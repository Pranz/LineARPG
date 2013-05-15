package fridefors_peng.linearpg.objects.entities.actions.bullets

import fridefors_peng.linearpg.objects.{Interactable, Matter, Renderable, Alarm}
import fridefors_peng.linearpg.objects.entities.Entity
import lolirofle.gl2dlib.data.{Vector, NullVector}
import fridefors_peng.linearpg.terrain.Terrain
import lolirofle.gl2dlib.geom.Rectangle
import lolirofle.gl2dlib.data.Position

class SwordBullet(pos:Position, ent:Entity) extends 
	Bullet(pos + Vector(12-24*ent.facingDir, 12),Rectangle(46*ent.facingDir, 8), ent, true) {
	
	var durability = -1
	
	def interactWithEnt(ent:Entity) = {}
	def interactWithTerrain(terrain:Option[Terrain]) = {}

	force = Vector(0.001f*ent.facingDir,0)
	new Alarm(200,()=>force = -force)
	new Alarm(400, () => this.destroy)
}