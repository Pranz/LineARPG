package fridefors_peng.linearpg.objects.entities.actions.bullets

import fridefors_peng.linearpg.objects.{Interactive, Physical, Renderable}
import fridefors_peng.linearpg.objects.entities.Entity
import fridefors_peng.linearpg.{Vector, NullVector}
import org.newdawn.slick.geom.Circle
import org.newdawn.slick.Graphics

class StraightBullet(pos:Vector, velocity:Float, ent:Entity) extends Bullet(pos, new Circle(0,0, 10), ent) {
	var movement = Vector(velocity * ent.hDir, 0)
	gravity  = 0.15f

	def draw(g:Graphics) {
		g.draw(body)
	}
}