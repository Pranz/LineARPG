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
	
	val hp:Double
	val fAction = new Array[Option[Action]](10) map {_ => None : Option[Action]}
	
	var movement     = V(0, 0)
	var accerelation = V(0, 0)
	var gravity      = 0.25f
	var moving       = false
	var jumpPower:Float
	var hDir         = 1
	private var speed_prv  = 0.6f
	private var hfriction_prv  = 0.3f
	
	def hfriction = hfriction_prv * speedMod
	def hfriction_=(value:Float):Unit = {
		hfriction_prv = value
	}
	
	private var speedMod = 1f //calculated every update due to performance. Init value
	
	var maxhspd   = 3f
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
	
	override def update {
		super.update
		applyHFriction
	}
	
	def applyHFriction {
		val hfric = {
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
		val spd = {
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
	
	def moveXtowardsOther(dir:Int, other:Interactive) {
		if(!collidesOther(position.x + dir, position.y, other)){
			position += V(dir,0)
			moveXtowardsOther(dir, other)
		}
	}
	
	def moveYtowardsOther(dir:Int, other:Interactive) {
		if(!collidesOther(position.x, position.y + dir, other)){
			position += V(0,dir)
			moveXtowardsOther(dir, other)
		}
	}
	
	def moveX(x:Float) {
		if (math.abs(x) >= 1){
			if (collidesAny(position.x + x, position.y, true)) {
				if(!collidesAny(position.x + Math.signum(x), position.y - 1, true)){
					position += V(0,-2)
				}
				else hspeed = 0
				
				moveX(x - math.signum(x))
			}
			else position += V(x,0)
		}
		else if (!collidesAny(position.x + math.signum(x), position.y, true)) position += V(x,0)
		else if(!collidesAny(position.x + Math.signum(x), position.y - 1, true)){
					position -= V(0,2)
				}
		else hspeed = 0
	}
	
	def moveY(y:Float) {
		if (math.abs(y) >= 1){
			if (collidesAny(position.x, position.y + y, true)){
				moveY(y - math.signum(y))
				vspeed = 0
			}
			else position += V(0,y)
		}
		else if (!collidesAny(position.x, position.y + math.signum(y), true)) position += V(0,y)
		else vspeed = 0
	}
	
	override def move(dpos:Vector):Unit = {
		moveX(dpos.x); moveY(dpos.y)
	}
}

object Entity{
	val LEFT  = -1
	val RIGHT =  1
	val list:ArrayBuffer[Entity] = ArrayBuffer()
}