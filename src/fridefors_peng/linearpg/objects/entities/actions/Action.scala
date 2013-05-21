package fridefors_peng.linearpg.objects.entities.actions

import fridefors_peng.linearpg.timing.Alarm
import fridefors_peng.linearpg.objects.entities.Entity

/**
 * abstract for any action.
 */

abstract class Action(ent:Entity) {
	val name : String
	
	val delay:Int
	val duration:Int
	var ready = true
	
	protected def preAction:Unit
	protected def action:Unit
	protected def thenAction:Unit
	
	protected final def execute {
		ready = false
		preAction
		new Alarm(delay,() => {
			action
			new Alarm(duration, () => {
				thenAction
				ready = true
			})
		})
	}
	
	def onClick : Unit
	def onRelease() : Unit
	def whileClicked : Unit
}