package fridefors_peng.linearpg.objects

import lolirofle.gl2dlib.data.Vector
import collection.mutable.ArrayBuffer
import lolirofle.gl2dlib.geom.Shape
import lolirofle.gl2dlib.data.Position
import lolirofle.gl2dlib.data.NullVector

/**
 * Any object that has a position and a body
 */
abstract class Matter(var position:Position, var body:Shape) extends GameObject {
	Matter.list += Matter.this
	
	var previousPos = position
	def deltaPos = position - previousPos
	
	override def update(delta:Int){
		previousPos = position
	}

	override def destroy{
		super.destroy
		Matter.list -= Matter.this
	}
		
	//Long ass-collision code. 

	def collidesInMotion(pos:Position, spd:Vector):Option[Matter]=None//TODO:
	
	def collidesOther(pos:Position, other:Matter):Boolean = {
		val nbody = body.at(pos)
		nbody.colliding(other.body.at(other.position))
	}
	
	def allPlaceMeeting[A <: Matter](pos:Position, objs:ArrayBuffer[A]=Matter.list):ArrayBuffer[A] = {
		val nbody = body.at(pos)
		objs.filter(x=>x!=Matter.this&&x.body.at(x.position).colliding(nbody))
	}
	
	def placeMeeting[A <: Matter](pos:Position, objs:ArrayBuffer[A]=Matter.list):Option[A] = {
		val nbody = body.at(pos)
		objs.toStream.filter(x=>x!=Matter.this&&x.body.at(x.position).colliding(nbody)).headOption
	}
	
	def collidesAny[A <: Matter](pos:Position, objs:ArrayBuffer[A]=Matter.list):Boolean = {
		val nbody = body.at(pos)
		objs.toStream.exists(obj=>obj!=Matter.this&&obj.body.at(obj.position).colliding(nbody))
	}
	
	def onGround:Boolean = collidesAny(position+Vector(0,1))
	
	def exertForce(force:Vector,mass:Float):Vector = -force
}

object Matter{
	val list:ArrayBuffer[Matter]=ArrayBuffer()
}