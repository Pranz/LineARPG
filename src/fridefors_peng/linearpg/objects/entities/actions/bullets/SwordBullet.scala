package fridefors_peng.linearpg.objects.entities.actions.bullets

import fridefors_peng.linearpg.objects.{Mass, Physical, Renderable, Alarm}
import fridefors_peng.linearpg.objects.entities.Entity
import lolirofle.gl2dlib.data.{Vector, NullVector}
import fridefors_peng.linearpg.terrain.Terrain
import lolirofle.gl2dlib.geom.Rectangle
import lolirofle.gl2dlib.data.Position

class SwordBullet(pos:Position, ent:Entity) extends 
	Bullet(pos + Vector(12-24*ent.hDir, 12),Rectangle(46*ent.hDir, 8), ent, true) {
	
	var durability = -1
	
	def interactWithEnt(ent:Entity) = {}
	def interactWithTerrain(terrain:Option[Terrain]) = {}

	movement = Vector(0.25f*ent.hDir,0)
	new Alarm(200,()=>movement= -movement)
	new Alarm(400, () => this.destroy)
}