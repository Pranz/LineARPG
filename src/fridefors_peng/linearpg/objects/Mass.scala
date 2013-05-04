package fridefors_peng.linearpg.objects

import lolirofle.gl2dlib.data.Vector
import collection.mutable.ArrayBuffer
import lolirofle.gl2dlib.geom.Shape
import lolirofle.gl2dlib.data.Position
import lolirofle.gl2dlib.data.NullVector

/**
 * Any object that has a position and a body
 */
abstract class Mass(var position:Position, var body:Shape) extends GameObject {
	Mass.list += Mass.this
	
	var previousPos = position
	def deltaPos = position - previousPos
	
	override def update(delta:Int){
		previousPos = position
	}
		
	override def destroy{
		super.destroy
		Mass.list -= Mass.this
	}
		
	//Long ass-collision code. 

	def collidesInMotion(pos:Position, spd:Vector):Option[Mass]=None//TODO:
	
	def collidesOther(pos:Position,other:Mass):Boolean = {
		val nbody = body.at(pos)
		nbody.colliding(other.body.at(other.position))
	}
	
	def allPlaceMeeting[A <: Mass](pos:Position, objs:ArrayBuffer[A]=Mass.list):ArrayBuffer[A] = {
		val nbody = body.at(pos)
		objs.filter(x=>x!=Mass.this&&x.body.at(x.position).colliding(nbody))
	}
	
	def placeMeeting[A <: Mass](pos:Position,objs:ArrayBuffer[A]=Mass.list):Option[A] = {
		val nbody = body.at(pos)
		objs.toStream.filter(x=>x!=Mass.this&&x.body.at(x.position).colliding(nbody)).headOption
	}
	
	def collidesAny(pos:Position,objs:ArrayBuffer[Mass]=Mass.list):Boolean = {
		val nbody = body.at(pos)
		objs.toStream.exists(obj=>obj!=Mass.this&&obj.body.at(obj.position).colliding(nbody))
	}
	
	def onGround:Boolean = collidesAny(position+Vector(0,1))
	
	def exertForce(force:Vector,delta:Int):Vector = -force
}

object Mass{
	val list:ArrayBuffer[Mass]=ArrayBuffer()
}