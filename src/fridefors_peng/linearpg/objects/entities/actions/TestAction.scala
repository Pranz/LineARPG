package fridefors_peng.linearpg.objects.entities.actions

import fridefors_peng.linearpg.objects.entities.Entity
import fridefors_peng.linearpg.objects.entities.actions.bullets.StraightBullet
import fridefors_peng.linearpg.timing.Alarm

class TestAction(ent:Entity) extends SingleAction(ent) {
	val name = "Best Action"
		
	val delay    = 0 : Int
	val duration = 0 : Int

	def preAction(): Unit = {

	}

	def action(): Unit = {
		new StraightBullet(ent.position, 0.1f, ent)
	}

	def thenAction(): Unit = {}
}