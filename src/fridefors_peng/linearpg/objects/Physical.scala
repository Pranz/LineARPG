package fridefors_peng.linearpg.objects

import fridefors_peng.linearpg.{Vector, NullVector}
import collection.mutable.ArrayBuffer
import lolirofle.gl2dlib.geom.Rectangle
import fridefors_peng.linearpg.objects.entities.Entity
import lolirofle.gl2dlib.geom.Shape

/**
 * Any object that is affected by gravity and has velocity and acceleration.
 * Physical solids also affect other physical objects movements.
 */

trait Physical extends Interactive{
	val V = Vector
	
	implicit def extractPhysicalOption(option:Option[Physical]):Physical = option match {
		case Some(obj) => obj
		case None      => DefaultPhysical
	}
	
	Physical.list += this
	
	def gravity:Float
	
	var movement:Vector
	var acceleration:Vector
	var friction:Double = 0
	
	var relativeObject:Option[Physical] = None
	var prvRelativeObject:Option[Physical] = None
	
	def hspeed = movement.x
	def hspeed_= (hsp:Float){movement = V(hsp,movement.y)}
		
	def vspeed = movement.y
	def vspeed_= (vsp:Float){movement = V(movement.x, vsp)}
	
	override def update(delta:Int) {
		super.update(delta)
		if(relativeObject != prvRelativeObject){
			movement += prvRelativeObject.movement + (-relativeObject.movement)
		}
		//TODO: This delta code makes it feel a bit weird, is it correctly calculated? Try 10 FPS and compare to 60 FPS
		movement += V(0, gravity) * delta
		movement += acceleration * delta
		
		position+=movementModifier(movement*delta);
		
		exertForce(movement * delta)
		
		if (friction != 0) 
			velocity -= friction*delta
			
		prvRelativeObject = relativeObject
		relativeObject = None
	}
	
	def velocity = movement.length
	def velocity_= (vel:Double):Unit = movement.withLength(vel);
	
	def exertForce(force:Vector) {
		if(solid && force != NullVector){
			val objs = allPlaceMeetingList(position.x + force.x, position.y + force.y, Entity.list)
			objs foreach {(o) =>
				o.relativeObject = Some(this)
				o.position += force
			}
		}
	}

	override def destroy{
		super.destroy
		Physical.list -= this
	}
}

object Physical{
	val list:ArrayBuffer[Physical] = ArrayBuffer()
}

object DefaultPhysical extends Interactive(NullVector,Rectangle(0,0)) with Physical {
	override var movement:Vector = NullVector
	override var acceleration:Vector = NullVector
	val gravity:Float = 0
}