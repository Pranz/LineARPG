package fridefors_peng.linearpg.input

import fridefors_peng.linearpg.Main
import fridefors_peng.linearpg.objects.GameObject
import org.newdawn.slick.{KeyListener, Input}
import collection.mutable.ArrayBuffer
import collection.immutable.HashMap

/**
 * Abstract class and object with a interface for user input.
 * Adds itself as a keyListener to the main input.
 * The object contains an enumeration for key values, along with a keyMap
 * Any object of Control also contains a keyMap, according to it's PlayerID
 */

abstract class Control(val playerID:Int) extends GameObject with KeyListener {
	def handleKeys(input:Input):Unit
	Main.input.addKeyListener(this)
	(Control list) += this

	override final def update(delta:Int) {
		if(isAcceptingInput) handleKeys(Main.input)
	}

	val keyMap = Control.keyMap(playerID)

	def isAcceptingInput = isActive
	def setInput(input:Input):Unit = {} 
	def inputEnded:Unit = {}
	def inputStarted:Unit = {}
	
	//Any input that solos is the only one who accepts input for that particular playerID
	var isActive = true
	def solo() : Unit = Control.list.filter((ctrl) => ctrl != this && ctrl.playerID == this.playerID).foreach {_.isActive = false}
	def unSolo() : Unit = Control.list.filter(_.playerID == this.playerID).foreach {_.isActive = true}
}

object Control {
	
	val list : ArrayBuffer[Control] = ArrayBuffer()
	object Key extends Enumeration {
		type Key = Value
		val MOVE_LEFT, MOVE_RIGHT, MOVE_UP, MOVE_DOWN, PERFORM_ACTION, JUMP, CROUCH, ACTION_1, ACTION_2, ACTION_3, ACTION_4, 
		ACTION_5, ACTION_6, ACTION_7, ACTION_8, ACTION_9, ACTION_0, CONSOLE_KEY = Value
	}
	
	//TODO: value of keyMap should be made external to a config file
	val keyMap = HashMap[Int, HashMap[Key.Key, Int]](
		0 -> HashMap(
			Key.MOVE_LEFT  -> Input.KEY_LEFT,
			Key.MOVE_RIGHT -> Input.KEY_RIGHT,
			Key.MOVE_DOWN  -> Input.KEY_DOWN,
			Key.MOVE_UP    -> Input.KEY_UP,
			Key.JUMP       -> Input.KEY_Z,
			Key.PERFORM_ACTION -> Input.KEY_X,
			Key.CROUCH     -> Input.KEY_LSHIFT,
			Key.ACTION_1   -> Input.KEY_1,
			Key.ACTION_2   -> Input.KEY_2,
			Key.ACTION_3   -> Input.KEY_3,
			Key.ACTION_4   -> Input.KEY_4,
			Key.ACTION_5   -> Input.KEY_5,
			Key.ACTION_6   -> Input.KEY_6,
			Key.ACTION_7   -> Input.KEY_7,
			Key.ACTION_8   -> Input.KEY_8,
			Key.ACTION_9   -> Input.KEY_9,
			Key.ACTION_0   -> Input.KEY_0,
			Key.CONSOLE_KEY-> Input.KEY_ENTER
		)
	)
}