package fridefors_peng.linearpg.objects.entities.actions.bullets

import fridefors_peng.linearpg.objects.{Mass, Physical, Renderable}
import fridefors_peng.linearpg.objects.entities.Entity
import lolirofle.gl2dlib.data.{Vector, NullVector}
import fridefors_peng.linearpg.terrain.Terrain
import lolirofle.gl2dlib.geom.Circle
import fridefors_peng.linearpg.objects.Alarm
import lolirofle.gl2dlib.data.Position

class StraightBullet(pos:Position, speed:Float, ent:Entity) extends Bullet(pos,Circle(2),ent) {
	movement = Vector(speed * ent.hDir, 0)
	gravity  = 0f

	new Alarm(1000, () => this.destroy)
	
	var durability = 1
	
	def interactWithEnt(ent:Entity) = {}
	def interactWithTerrain(terrain:Option[Terrain]) = {}
}