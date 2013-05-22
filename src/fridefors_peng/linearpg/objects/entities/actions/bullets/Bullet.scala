package fridefors_peng.linearpg.objects.entities.actions.bullets

import fridefors_peng.linearpg.objects.{Interactable, Matter, Renderable}
import fridefors_peng.linearpg.timing.Alarm
import collection.mutable.ArrayBuffer
import fridefors_peng.linearpg.objects.entities.Entity
import lolirofle.data.{Vector,NullVector}
import fridefors_peng.linearpg.terrain.Terrain
import lolirofle.geom.Shape
import lolirofle.data.Position

/**
 * A bullet is a physical object which interacts with entities and possible terrain.
 * Main source for damage and debuffs. Set durability to -1 for infinite durability. 
 */

abstract class Bullet(pos:Position,bd:Shape,ent:Entity,relativePos:Boolean=false) 
		extends Interactable(pos,bd) with Matter with Renderable{
	override val gravity=0f
	override val friction=0f
	val firstPos = ent.position
	
	var invulnerable_ents = ArrayBuffer(ent)
	val invulnerable_time = -1
	var durability : Int
	
	def draw(){
		(body at position).draw
	}
	
	def makeInvulnerable(ent:Entity) : Unit = {
		invulnerable_ents += ent
		new Alarm(invulnerable_time, () => invulnerable_ents -= ent)
	}
	
	override def update(delta:Int) {
		//Loop through all collision of ents, filter out those who are invulnerable
		(placeMeetings(position,Entity.list).filter {!invulnerable_ents.contains(_)}).foreach(
			ent => {
				durability -= 1
				if (durability == 0) onDestroy
				interactWithEnt(ent)
				makeInvulnerable(ent)
			})
		interactWithTerrain(placeMeeting(position,Terrain.list))
		
		super.update(delta)
		if(relativePos){
			position += Vector(ent.deltaPosition.x, ent.deltaPosition.y)
		}
		
		
	}
	
	def interactWithEnt(ent:Entity) : Unit
	def interactWithTerrain(terrain:Option[Terrain]) : Unit
	
	
}