package fridefors_peng.linearpg.objects.entities.actions

import fridefors_peng.linearpg.objects.entities.Entity

abstract class ChargedAction(ent:Entity) extends Action(ent:Entity) {
	protected var time = 0 : Int

	def onRelease(): Unit = {
		execute
		time = 0
	}

	def whileClicked {
		time += 1
	}
}