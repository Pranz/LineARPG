package fridefors_peng.linearpg.objects.entities

import fridefors_peng.linearpg.objects.{Physical, Interactive, Renderable, Alarm}
import fridefors_peng.linearpg.objects.entities.actions.Action
import fridefors_peng.linearpg.Vector
import org.newdawn.slick.geom.Shape
import org.newdawn.slick.Graphics
import collection.mutable.ArrayBuffer

abstract class Entity( pos:Vector, body:Shape) extends Interactive(pos, body) with Physical 
		with Renderable{
	
	(Entity list) += this
	
	private val speedMods = ArrayBuffer[Float]()//TODO: Check if we really need a List for this, multiplication and division would work fine
	
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
	var gravity      = 0.001f
	var moving       = false
	var jumpPower:Float
	var hDir         = 1
	private var spd  = 0.06f
	private var hfr  = 0.05f

	def hfriction = hfr * speedMod
	def hfriction_=(value:Float):Unit = {
		hfr = value
	}
	
	var vfriction = 0
	
	var speedMod = 1f //calculates for every update due to performance.
	
	var maxhspd   = 0.25f
	def maxhSpeed:Float = {
		if (onGround) speedMod * maxhspd
		else speedMod * maxhspd * airSpeedFactor * 5
	}
	
	def speed = speedMod * spd
	def speed_=(value:Float):Unit = {
		spd = value
	}
	
	def addSpeedMod(spd:Float, duration:Int = -1) {
		speedMods += spd
		if(duration != -1) new Alarm(duration, () => speedMods -= spd, false)
	}
	
	def removeSpeedMod(spd:Float){
		speedMods -= spd
	}
	
	def draw(g:Graphics) {
		g.draw(body)
	}
	
	override def update(delta:Int) {
		super.update(delta)
		applyHFriction
		speedMod = speedMods.foldLeft(1.0f){_ * _}
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
	
	def moveX(x:Float):Vector={
		if (x >= 1 || x <= -1){
			if (collidesAny(position.x + x, position.y, true)) {
				hspeed = 0
				return moveX(x - math.signum(x))
			}
			else return V(x,0);
		}
		else if(collidesAny(position.x + math.signum(x), position.y, true)){
			hspeed = 0
			return V(0,0);
		}
		else
			return V(x,0)
	}
	
	def moveY(y:Float):Vector={
		if(y >= 1 || y <= -1){
			if (collidesAny(position.x, position.y + y, true)){
				vspeed = 0
				return moveY(y - math.signum(y));
			}
			else
				return V(0,y)
		}
		else if(collidesAny(position.x, position.y + math.signum(y), true)){
			vspeed = 0
			return V(0,0)
		}
		else
			return V(0,y)
	}
	
	override def movementModifier(dpos:Vector)=moveX(dpos.x)+moveY(dpos.y)
}

object Entity{
	val LEFT  = -1
	val RIGHT =  1
	val list:ArrayBuffer[Entity] = ArrayBuffer()
}