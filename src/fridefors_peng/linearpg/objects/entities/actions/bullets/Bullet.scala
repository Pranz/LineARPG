package fridefors_peng.linearpg.objects.entities.actions.bullets

import fridefors_peng.linearpg.objects.{Interactive, Physical, Renderable, Alarm}
import collection.mutable.ArrayBuffer
import fridefors_peng.linearpg.objects.entities.Entity
import fridefors_peng.linearpg.{Vector, NullVector}
import fridefors_peng.linearpg.terrain.Terrain
import lolirofle.gl2dlib.geom.Shape

/**
 * A bullet is a physical object which interacts with entities and possible terrain.
 * Main source for damage and debuffs. Set durability to -1 for infinite durability. 
 */

abstract class Bullet(pos:Vector,bd:Shape,ent:Entity,relativePos:Boolean=false) 
		extends Interactive(pos,bd) with Physical with Renderable{
	var gravity = 0 : Float
	var acceleration = NullVector : Vector
	val firstPos = ent.position
	
	var invulnerable_ents = ArrayBuffer(ent)
	val invulnerable_time = -1
	var durability : Int
	
	def draw() {
		body.at(position).draw
	}
	
	def makeInvulnerable(ent:Entity) : Unit = {
		invulnerable_ents += ent
		new Alarm(invulnerable_time, () => invulnerable_ents -= ent)
	}
	
	override def update(delta:Int) {
		//Loop through all collision of ents, filter out those who are invulnerable
		(allPlaceMeetingList(position.x,position.y, Entity.list).filter {!invulnerable_ents.contains(_)}).foreach(
			ent => {
				durability -= 1
				if (durability == 0) destroy
				interactWithEnt(ent)
				makeInvulnerable(ent)
			})
		interactWithTerrain(placeMeetingList(position.x, position.y, Terrain.list))
		
		super.update(delta)
		if(relativePos){
			position += Vector(ent.deltaPos.x, ent.deltaPos.y)
		}
		
		
	}
	
	def interactWithEnt(ent:Entity) : Unit
	def interactWithTerrain(terrain:Option[Terrain]) : Unit
	
	
}