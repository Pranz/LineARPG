package fridefors_peng.linearpg.objects.entities.actions

import fridefors_peng.linearpg.objects.entities.Entity
import fridefors_peng.linearpg.objects.entities.actions.bullets.StraightBullet

class TestChargedAction(ent:Entity)  extends ChargedAction(ent)  {
	override val name = "other action"

	val duration = 0 : Int
	val delay    = 0 : Int

	def preAction(): Unit = {
		new StraightBullet(ent.position, (Math.sqrt(time) * 1.2).toFloat, ent)
	}

	def action(): Unit = {}
	def thenAction(): Unit = {}
	def onClick(): Unit = {}
}