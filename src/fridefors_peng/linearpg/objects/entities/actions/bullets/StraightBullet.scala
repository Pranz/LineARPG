package fridefors_peng.linearpg.objects.entities.actions.bullets

import fridefors_peng.linearpg.objects.{Interactive, Physical, Renderable}
import fridefors_peng.linearpg.objects.entities.Entity
import fridefors_peng.linearpg.{Vector, NullVector}
import org.newdawn.slick.geom.Circle
import org.newdawn.slick.Graphics
import fridefors_peng.linearpg.objects.Alarm

class StraightBullet(pos:Vector, velocity:Float, ent:Entity) extends Bullet(pos, new Circle(0,0, 10), ent) {
	var movement = Vector(velocity * ent.hDir, 0)
	gravity  = 0f

	new Alarm(1000, () => this.destroy)
	
	def draw(g:Graphics) {
		g.draw(body)
	}
}