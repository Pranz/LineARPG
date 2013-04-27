package fridefors_peng.linearpg.objects

import fridefors_peng.linearpg.{Vector, NullVector}
import collection.mutable.ArrayBuffer
import org.newdawn.slick.geom.Rectangle
import fridefors_peng.linearpg.objects.entities.Entity

trait Physical extends Interactive {
	val V = Vector
	
	implicit def extractPhysicalOption(option:Option[Physical]):Physical = option match {
		case Some(obj) => obj
		case None      => DefaultPhysical
	}
	
	(Physical list) += this
	
	var movement:Vector
	var accerelation:Vector
	var gravity:Float
	var friction:Double = 0
	
	var relativeObject:Option[Physical] = None
	var prvRelativeObject:Option[Physical] = None
	
	def hspeed = movement.x
	def hspeed_= (hsp:Float):Unit = 
		movement = V(hsp,movement.y)
		
	def vspeed = movement.y
	def vspeed_= (vsp:Float):Unit = 
		movement = V(movement.x, vsp)
	
	override def update(delta:Int) {
		super.update(delta)
		if(relativeObject != prvRelativeObject){
			movement += prvRelativeObject.movement + (-relativeObject.movement)
		}
		//TODO: This delta code makes it feel a bit weird, is it correctly calculated? Try 10 FPS and compare to 60 FPS
		movement += V(0, gravity)*delta
		movement += accerelation*delta
		
		position+=movementModifier(movement*delta);
		
		exertForce(movement)
		
		if (friction != 0) 
			velocity -= friction*delta
			
		prvRelativeObject = relativeObject
		relativeObject = None
	}
	
	def velocity = movement.length
	def velocity_= (vel:Double):Unit = movement.whoseLengthIs(vel);
	
	def exertForce(force:Vector) {
		if(solid && force != NullVector){
			val objs = allPlaceMeetingList(position.x + force.x, position.y + force.y , Entity list)
			objs foreach {(o) =>
				o.relativeObject = Some(this)
				o.position += force
			}
		}
	}

	override def destroy {
		super.destroy
		(Physical list) -= this
	}
}

object Physical{
	val list:ArrayBuffer[Physical] = ArrayBuffer()
}

object DefaultPhysical extends Interactive(NullVector, new Rectangle(0,0,0,0)) with Physical {
	var movement:Vector = Vector(0,0)
	var accerelation:Vector = Vector(0,0)
	var gravity:Float = 0
}