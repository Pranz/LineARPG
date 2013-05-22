package fridefors_peng.linearpg.objects.entities.actions.bullets

import fridefors_peng.linearpg.objects.{Interactable, Matter, Renderable}
import fridefors_peng.linearpg.objects.entities.Entity
import lolirofle.data.{Vector, NullVector}
import fridefors_peng.linearpg.terrain.Terrain
import lolirofle.geom.Circle
import fridefors_peng.linearpg.timing.Alarm
import lolirofle.data.Position

class StraightBullet(pos:Position, speed:Float, ent:Entity) extends Bullet(pos,Circle(2),ent) {
	force = Vector(speed * ent.facingDir, 0)
	override val gravity=0f

	new Alarm(1000, () => this.onDestroy)
	
	var durability = 1
	
	def interactWithEnt(ent:Entity) = {}
	def interactWithTerrain(terrain:Option[Terrain]) = {}
}