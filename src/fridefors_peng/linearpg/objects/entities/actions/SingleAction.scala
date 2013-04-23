package fridefors_peng.linearpg.objects.entities.actions

import fridefors_peng.linearpg.objects.entities.Entity

abstract class SingleAction(ent:Entity) extends Action(ent) {
	def onClick(): Unit = {
		execute
	}
	def onRelease() : Unit = {}
	def whileClicked() : Unit = {}
}