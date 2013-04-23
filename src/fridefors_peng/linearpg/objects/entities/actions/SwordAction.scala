package fridefors_peng.linearpg.objects.entities.actions

import fridefors_peng.linearpg.objects.entities.Entity
import fridefors_peng.linearpg.objects.entities.actions.bullets.SwordBullet

class SwordAction(ent:Entity) extends SingleAction(ent) {
	val name     = "Sword"
	val delay    = 0 : Int
	val duration = 40 : Int

	def preAction(): Unit = {}

	def action(): Unit = {
		new SwordBullet(ent.position, ent)
	}

	def then(): Unit = {}
}