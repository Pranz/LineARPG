package fridefors_peng.linearpg.input

import fridefors_peng.linearpg.objects.GameObject
import collection.mutable.ArrayBuffer
import collection.immutable.HashMap
import org.lwjgl.input.Keyboard

/**
 * Abstract class and object with a interface for user input.
 * Adds itself as a keyListener to the main input.
 * The object contains an enumeration for key values, along with a keyMap
 * Any object of Control also contains a keyMap, according to it's PlayerID
 */

abstract class Control(val playerID:Int) extends GameObject{
	Control.list += this
	
	def keyMap = Control.keyMap(playerID)
	def keyPressed(key:Int);
	def keyReleased(key:Int);
	def keyChar(char:Char);
	
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
			Key.MOVE_LEFT  -> Keyboard.KEY_LEFT,
			Key.MOVE_RIGHT -> Keyboard.KEY_RIGHT,
			Key.MOVE_DOWN  -> Keyboard.KEY_DOWN,
			Key.MOVE_UP    -> Keyboard.KEY_UP,
			Key.JUMP       -> Keyboard.KEY_Z,
			Key.PERFORM_ACTION -> Keyboard.KEY_X,
			Key.CROUCH     -> Keyboard.KEY_LSHIFT,
			Key.ACTION_1   -> Keyboard.KEY_1,
			Key.ACTION_2   -> Keyboard.KEY_2,
			Key.ACTION_3   -> Keyboard.KEY_3,
			Key.ACTION_4   -> Keyboard.KEY_4,
			Key.ACTION_5   -> Keyboard.KEY_5,
			Key.ACTION_6   -> Keyboard.KEY_6,
			Key.ACTION_7   -> Keyboard.KEY_7,
			Key.ACTION_8   -> Keyboard.KEY_8,
			Key.ACTION_9   -> Keyboard.KEY_9,
			Key.ACTION_0   -> Keyboard.KEY_0,
			Key.CONSOLE_KEY-> Keyboard.KEY_T
		)
	)
}