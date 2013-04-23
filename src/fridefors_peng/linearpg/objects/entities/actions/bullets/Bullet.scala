package fridefors_peng.linearpg.objects.entities.actions.bullets

import fridefors_peng.linearpg.objects.{Interactive, Physical, Renderable}
import fridefors_peng.linearpg.objects.entities.Entity
import fridefors_peng.linearpg.{Vector, NullVector}
import org.newdawn.slick.geom.Shape

abstract class Bullet(pos:Vector, bd:Shape, ent:Entity, relativePos:Boolean = false) 
		extends Interactive(pos, bd) with Physical with Renderable {
	
	var gravity = 0 : Float
	var accerelation = NullVector : Vector
	val firstPos = ent.position
	
	override def update {
		super.update
		if(relativePos){
			position += Vector(ent.deltaPos.x, ent.deltaPos.y)
		}
	}
}