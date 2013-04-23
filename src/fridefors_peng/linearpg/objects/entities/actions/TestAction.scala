package fridefors_peng.linearpg.objects.entities.actions

import fridefors_peng.linearpg.objects.entities.Entity
import fridefors_peng.linearpg.objects.entities.actions.bullets.StraightBullet

class TestAction(ent:Entity) extends SingleAction(ent) {
	val name = "Best Action"

	val delay    = 0 : Int
	val duration = 0 : Int

	def preAction(): Unit = {

	}

	def action(): Unit = {
		new StraightBullet(ent.position, 5, ent)
	}

	def then(): Unit = {}
}