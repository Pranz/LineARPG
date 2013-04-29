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
	
	def airFricFactor:Float
	def airSpeedFactor:Float
	def jumpPower:Float
	
	val fAction = new Array[Option[Action]](10) map {_ => None : Option[Action]}
	
	final override var movement        = V(0,0)
	final override var acceleration    = V(0,0)
	override val gravity = 0.001f //TODO: "Gravity" value based on shape of body (Air resistance)
	
	var hDir:Horizontal = Direction.Right
	private var speed_prv  = 0.06f
	private var hfriction_prv  = 0.03f
	
	def hfriction = hfriction_prv * speedMod//TODO: Friction based on surface of different objects
	def hfriction_=(value:Float):Unit = {
		hfriction_prv = value
	}
	
	private var speedMod = 1f //calculated every update due to performance. Init value
	
	var maxhspd   = 0.25f
	def maxhSpeed:Float=
		if(onGround)
			speedMod * maxhspd
		else
			speedMod * maxhspd * airSpeedFactor*2
	
	def speed = speedMod * speed_prv
	def speed_=(value:Float):Unit = {
		speed_prv = value
	}
	
	def addSpeedMod(spd:Float, duration:Int = -1) {//TODO: Make this safer, and not with lists. Now we could remove the speedMod anytime we want and all the values would be messed up
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
	}
	
	def run(dir:Horizontal) {
		hDir = dir
		val spd =if(onGround)speed else	speed*airSpeedFactor
		
		if((math.abs(hspeed + spd*dir)) >= maxhSpeed && dir.toByte == Math.signum(hspeed)){
			if (spd > (math.abs(hspeed + spd*dir)) - maxhSpeed)
				hspeed = maxhSpeed * math.signum(hspeed)
		}
		else hspeed += spd*dir
	}
	
	def jump(){
		if(onGround)
			vspeed -= jumpPower
	}
	
	def xMovement(x:Float):Float=
		if(x >= 1 || x <= -1){//If speed>=1
			if(collidesAny(position.x+x,position.y,true)){//If colliding with anything at this speed
				hspeed=0;//We collided, stop movement
				return xMovement(x-math.signum(x))//See how far we can move without colliding with 1 length unit at a time
			}
			else return x;
		}
		else//Else if speed<1 (Another type of collision checking than the 1 length unit code)
			if(collidesAny(position.x+math.signum(x),position.y,true)){
				hspeed=0;
				return 0;
			}
			else//Else: No collision anywhere, move the distance requested
				return x
	
	def yMovement(y:Float):Float=
		if(y >= 1 || y <= -1){
			if (collidesAny(position.x, position.y + y, true)){
				vspeed = 0
				return yMovement(y - math.signum(y));
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
	
	override def movementModifier(dpos:Vector) = V(xMovement(dpos.x), yMovement(dpos.y))
}

object Entity{
	val list:ArrayBuffer[Entity] = ArrayBuffer()
}