package fridefors_peng.linearpg.objects.entities.actions.bullets

import fridefors_peng.linearpg.objects.{Interactive, Physical, Renderable, Alarm}
import fridefors_peng.linearpg.objects.entities.Entity
import fridefors_peng.linearpg.{Vector, NullVector}
import org.newdawn.slick.geom.{Rectangle, Transform}
import org.newdawn.slick.Graphics

class SwordBullet(pos:Vector, ent:Entity) extends 
	Bullet(pos + Vector(12 + 6*ent.hDir, 12), new Rectangle(0,0, 46*ent.hDir, 8), ent, true) {

	override def draw(g:Graphics) {
		g.draw(body)
	}

	override def update(delta:Int){
		super.update(delta)
	}

	var movement = Vector(0,0)
	new Alarm(40, () => this.destroy)
}