package fridefors_peng.linearpg.objects.entities

import fridefors_peng.linearpg.objects.{Physical, Mass, Renderable, Alarm}
import fridefors_peng.linearpg.objects.entities.actions.Action
import lolirofle.gl2dlib.data.Vector
import lolirofle.gl2dlib.geom.Shape
import collection.mutable.ArrayBuffer
import lolirofle.gl2dlib.data.Horizontal
import lolirofle.gl2dlib.data.Direction
import lolirofle.gl2dlib.data.Position
import lolirofle.gl2dlib.data.NullVector

/**
 * The Entity class. This is any unit in the game world, and can be player or AI controlled.
 * 
 */
abstract class Entity(pos:Position,body:Shape) extends Mass(pos,body) with Physical with Renderable{
	Entity.list += this
	
	def airSpeedFactor:Float
	def jumpPower:Float
	
	val fAction = new Array[Option[Action]](10) map {_ => None : Option[Action]}
	
	override val gravity = 0.001f //TODO: "Gravity" value based on shape of body (Air resistance)
	
	var hDir:Horizontal = Direction.Right
	private var speed_prv  = 0.04f
	
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
}

object Entity{
	val list:ArrayBuffer[Entity] = ArrayBuffer()
}