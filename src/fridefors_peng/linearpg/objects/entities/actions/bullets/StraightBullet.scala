package fridefors_peng.linearpg.objects.entities.actions.bullets

import fridefors_peng.linearpg.objects.{Interactive, Physical, Renderable}
import fridefors_peng.linearpg.objects.entities.Entity
import fridefors_peng.linearpg.{Vector, NullVector}
import fridefors_peng.linearpg.terrain.Terrain
import org.newdawn.slick.geom.Circle

class StraightBullet(pos:Vector, speed:Float, ent:Entity) extends Bullet(pos, new Circle(0,0, 10), ent) {
	var movement = Vector(speed * ent.hDir, 0)
	gravity  = 0.15f
	
	var durability = 1
	
	def interactWithEnt(ent:Entity) = {}
	def interactWithTerrain(terrain:Option[Terrain]) = {}
}