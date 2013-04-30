package fridefors_peng.linearpg.objects.entities

import fridefors_peng.linearpg.objects.{Physical, Interactive, Renderable, Alarm}
import fridefors_peng.linearpg.objects.entities.actions.Action
import fridefors_peng.linearpg.Vector
import org.newdawn.slick.geom.Shape
import org.newdawn.slick.Graphics
import collection.mutable.ArrayBuffer

/**
 * The Entity class. This is any unit in the game world, and can be player or AI controlled.
 * 
 */
abstract class Entity( pos:Vector, body:Shape) extends Interactive(pos, body) with Physical 
		with Renderable{
	
	(Entity list) += this
	
	private var airspdf = 0.1f
	var airFricFactor  = 0f
	
	def airSpeedFactor:Float = {
		if(vspeed < 0) 0
		else airspdf
	}
	
	var dt : Int = 0
	val hp:Double
	val fAction = new Array[Option[Action]](10) map {_ => None : Option[Action]}
	
	var movement     = V(0, 0)
	var acceleration = V(0, 0)
	var gravity      = 0.001f
	var moving       = false
	var jumpPower:Float
	var hDir         = 1
	private var speed_prv  = 0.0025f
	private var hfriction_prv  = 0.0012f
	
	def hfriction = hfriction_prv * speedMod
	def hfriction_=(value:Float):Unit = {
		hfriction_prv = value
	}
	
	private var speedMod = 1f //calculated every update due to performance. Init value
	
	var maxhspd   = 0.25f
	def maxhSpeed:Float = {
		if (onGround) speedMod * maxhspd
		else speedMod * maxhspd * airSpeedFactor * 5
	}
	
	def speed = speedMod * speed_prv
	def speed_=(value:Float):Unit = {
		speed_prv = value
	}
	
	def addSpeedMod(spd:Float, duration:Int = -1) {
		speedMod *= spd
		new Alarm(duration, () => removeSpeedMod(spd))
	}
	
	def removeSpeedMod(spd:Float){
		speedMod /= spd
	}
	
	def draw(g:Graphics) {
		g.draw(body)
	}
	
	override def update(delta:Int) {
		super.update(delta)
		applyHFriction
		dt = delta
	}
	
	def applyHFriction {
		val hfric = (dt) * {
			if (onGround) hfriction
			else hfriction * airFricFactor
		}
		if(hfric != 0){
			if (math.abs(hspeed) < hfric) hspeed = 0
			else hspeed -= math.signum(hspeed) * hfric
		}
		else moving = false
	}
	
	def run(dir:Int) {
		hDir = dir
		val spd = dt * {
			if (onGround) speed
			else speed * airSpeedFactor
		}
		moving = true
		if ((math.abs(hspeed + spd*dir)) >= maxhSpeed && dir == Math.signum(hspeed)){
			if (spd > (math.abs(hspeed + spd*dir)) - maxhSpeed) hspeed = maxhSpeed * math.signum(hspeed)
		}
		else hspeed += spd*dir
	}
	
	def jump() {
		if(onGround){
			vspeed -= jumpPower
		}
	}
	
	def moveX(dpos : Vector) : Float={
		if (dpos.x >= 1 || dpos.x <= -1){
			if (collidesAny(position.x + dpos.x, position.y + dpos.y, true)) {
				hspeed = 0
				return moveX(V(dpos.x - math.signum(dpos.x), dpos.y))
			}
			else return dpos.x;
		}
		else if(collidesAny(position.x + math.signum(dpos.x), position.y + dpos.y, true)){
			hspeed = 0
			return 0;
		}
		else
			return dpos.x
	}
	
	def moveY(dpos : Vector) : Float ={
		if(dpos.y >= 1 || dpos.y <= -1){
			if (collidesAny(position.x + dpos.x, position.y + dpos.y, true)){
				vspeed = 0
				return moveY(V(dpos.x, dpos.y - math.signum(dpos.y)));
			}
			else
				return dpos.y
		}
		else if(collidesAny(position.x + dpos.x, position.y + math.signum(dpos.y), true)){
			vspeed = 0
			return 0
		}
		else
			return dpos.y
	}
	
	override def movementModifier(dpos:Vector) = {
		val xMove = moveX(V(dpos.x, 0))
		V(xMove, moveY(V(xMove, dpos.y)))
	}
}

object Entity{
	val LEFT  = -1
	val RIGHT =  1
	val list:ArrayBuffer[Entity] = ArrayBuffer()
}