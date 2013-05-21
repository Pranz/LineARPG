package fridefors_peng.linearpg.objects

import lolirofle.gl2dlib.data.Vector
import collection.mutable.ArrayBuffer
import lolirofle.gl2dlib.geom.Shape
import lolirofle.gl2dlib.data.Position
import lolirofle.gl2dlib.data.NullVector

/**
 * Any object that has a position and a body
 */
abstract class Interactable(var position:Position, var body:Shape) extends GameObject with Updatable{
	Interactable.list += Interactable.this
	
	/**
	 * Position in previous update
	 */
	def previousPosition=_previousPosition
	private var _previousPosition = position
	
	/**
	 * Movement since last update
	 */
	def deltaPosition=position-previousPosition
	
	override def update(delta:Int){
		_previousPosition = position
	}

	override def onDestroy{
		super.onDestroy
		Interactable.list -= Interactable.this
	}
	
	/**
	 * Volume (px^2)
	 */
	def volume=body.area
	
	//TODO: Layer/depth for rendering and collisions. Also thickness with depth 
	//Long ass-collision code. 

	def collidesInMotion(spd:Vector):Option[Interactable]=None//TODO:
	
	def collidesWith(other:Interactable, pos:Position=position):Boolean=
		body.at(pos).colliding(other.body.at(other.position))
	
	def placeMeetings[A <: Interactable](pos:Position=position, objs:ArrayBuffer[A]=Interactable.list):ArrayBuffer[A] = {
		val nbody = body.at(pos)
		objs.filter(other=>other!=Interactable.this&&other.body.at(other.position).colliding(nbody))
	}
	
	def placeMeeting[A <: Interactable](pos:Position=position, objs:ArrayBuffer[A]=Interactable.list):Option[A] = {
		val nbody = body.at(pos)
		objs.toStream.filter(other=>other!=Interactable.this&&other.body.at(other.position).colliding(nbody)).headOption
	}
	
	def collidesAny[A <: Interactable](pos:Position=position, objs:ArrayBuffer[A]=Interactable.list):Boolean = {
		val nbody = body.at(pos)
		objs.toStream.exists(other=>other!=Interactable.this&&other.body.at(other.position).colliding(nbody))
	}
	
	def onGround:Boolean = collidesAny(position+Vector(0,1))
	
	def exertForce(force:Vector,mass:Float):Vector = -force
}

object Interactable{
	val list:ArrayBuffer[Interactable]=ArrayBuffer()
}