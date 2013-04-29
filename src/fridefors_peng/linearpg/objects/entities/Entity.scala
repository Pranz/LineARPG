package fridefors_peng.linearpg.objects.entities

import fridefors_peng.linearpg.objects.{Physical, Interactive, Renderable, Alarm}
import fridefors_peng.linearpg.objects.entities.actions.Action
import fridefors_peng.linearpg.Vector
import lolirofle.gl2dlib.geom.Shape
import collection.mutable.ArrayBuffer
import lolirofle.gl2dlib.data.Horizontal
import lolirofle.gl2dlib.data.Direction

/**
 * The Entity class. This is any unit in the game world, and can be player or AI controlled.
 * 
 */
abstract class Entity(pos:Vector,body:Shape) extends Interactive(pos,body) with Physical with Renderable{
	Entity.list += this
	
	private var airspdf = 0.1f
	var airFricFactor  = 0f
	
	def airSpeedFactor:Float = {
		if(vspeed < 0) 0
		else airspdf
	}
	
	val hp:Double
	val fAction = new Array[Option[Action]](10) map {_ => None : Option[Action]}
	
	var movement        = V(0, 0)
	var acceleration    = V(0, 0)
	var gravity         = 0.001f
	var moving          = false
	var jumpPower:Float
	var hDir:Horizontal = Direction.Right
	private var speed_prv  = 0.06f
	private var hfriction_prv  = 0.03f
	
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
	
	def draw(){
		body.at(position).draw
	}
	
	override def update(delta:Int) {
		super.update(delta)
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
	
	def run(dir:Horizontal) {
		hDir = dir
		val spd = {
			if (onGround) speed
			else speed * airSpeedFactor
		}
		moving = true
		if ((math.abs(hspeed + spd*dir)) >= maxhSpeed && dir.toByte == Math.signum(hspeed)){
			if (spd > (math.abs(hspeed + spd*dir)) - maxhSpeed) hspeed = maxhSpeed * math.signum(hspeed)
		}
		else hspeed += spd*dir
	}
	
	def jump() {
		if(onGround){
			vspeed -= jumpPower
		}
	}
	
	def moveX(x:Float) : Float=
		if (x >= 1 || x <= -1){
			if(collidesAny(position.x+x,position.y,true)){
				hspeed=0;
				return moveX(x-math.signum(x))
			}
			else return x;
		}
		else if(collidesAny(position.x+math.signum(x),position.y,true)){
			hspeed=0;
			return 0;
		}
		else
			return x
	
	def moveY(y:Float) : Float=
		if(y >= 1 || y <= -1){
			if (collidesAny(position.x, position.y + y, true)){
				vspeed = 0
				return moveY(y - math.signum(y));
			}
			else
				return y
		}
		else if(collidesAny(position.x, position.y + math.signum(y), true)){
			vspeed = 0
			return 0
		}
		else
			return y
	
	override def movementModifier(dpos:Vector) = V(moveX(dpos.x), moveY(dpos.y))
}

object Entity{
	val list:ArrayBuffer[Entity] = ArrayBuffer()
}