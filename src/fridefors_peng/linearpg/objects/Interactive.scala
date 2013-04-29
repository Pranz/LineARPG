package fridefors_peng.linearpg.objects

import fridefors_peng.linearpg.{Vector, PolarVector}
import collection.mutable.ArrayBuffer
import lolirofle.gl2dlib.geom.Shape

/**
 * Any object that has a position and a body
 */
abstract class Interactive(var position:Vector,var body:Shape) extends GameObject {
	
	Interactive.list += this
	var previousPos = position
	def deltaPos = position - previousPos
	
	private var solid_prv = false
	def solid:Boolean = solid_prv
	def solid_=(newIsSolid:Boolean):Unit = 
		if (newIsSolid != solid)
		{
			if (newIsSolid){
			Interactive.solids += this
			}
			else Interactive.solids -= this
			solid_prv = newIsSolid
		}
	
	
	override def update(delta:Int) {
		previousPos = position
	}
	
	def movementModifier(dpos:Vector)=dpos;
	
	override def destroy {
		super.destroy
		Interactive.list -= this
		if(solid) Interactive.solids -= this
	}
	
	//Long ass-collision code. 
	
	def collidesOther(x:Float, y:Float, other:Interactive):Boolean = {
		val nbody = body.at(x,y)
		nbody.insideOf(other.body.at(other.position))
	}
	
	def allPlaceMeetingList[A <: Interactive](x:Float, y:Float, objs:ArrayBuffer[A]):ArrayBuffer[A] = {
		val nbody = body.at(x,y)
		objs.filter(x=>x.body.at(x.position).insideOf(nbody))
	}
	
	def placeMeetingList[A <: Interactive](x:Float, y:Float, objs:ArrayBuffer[A]):Option[A] = {
		val nbody = body.at(x,y)
		objs.toStream.filter(x=>x.body.at(x.position).insideOf(nbody)).headOption
	}
	
	def collidesList(x:Float, y:Float, objs:ArrayBuffer[Interactive]):Boolean = {
		val nbody = body.at(x,y)
		objs.toStream.exists(x=>x.body.at(x.position).insideOf(nbody))
	}
	
	def allPlaceMeeting(x:Float, y:Float, onlySolids:Boolean):ArrayBuffer[Interactive] = {
		val objs = {
			if(onlySolids) (Interactive.solids)
			else           (Interactive.list)
		}
		allPlaceMeetingList(x, y, objs)
	}
	
	def placeMeeting(x:Float, y:Float, onlySolids:Boolean):Option[Interactive] = {
		val objs = {
			if(onlySolids) (Interactive.solids)
			else           (Interactive.list)
		}
		placeMeetingList(x, y, objs)
	}
	
	def collidesAny(x:Float, y:Float, onlySolids:Boolean):Boolean = {
		val objs = {
			if(onlySolids) (Interactive.solids)
			else (Interactive.list)
		}
		collidesList(x, y, objs)
	}
	
	def onGround:Boolean = collidesAny(position.x, position.y+1, true) 
	
}

object Interactive{
	val list:   collection.mutable.ArrayBuffer[Interactive] = collection.mutable.ArrayBuffer()
	val solids: collection.mutable.ArrayBuffer[Interactive] = collection.mutable.ArrayBuffer()
}