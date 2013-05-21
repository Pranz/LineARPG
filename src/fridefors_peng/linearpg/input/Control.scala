package fridefors_peng.linearpg.input

import fridefors_peng.linearpg.objects.GameObject
import collection.mutable.ArrayBuffer
import collection.immutable.HashMap
import org.lwjgl.input.Keyboard

/**
 * Abstract class and object with a interface for user input.
 * The object contains an enumeration for key values, along with a keyMap
 * Any object of Control also contains a keyMap, according to it's PlayerID
 */
trait Control{
	/**
	 * On defined action key press
	 * 
	 * @param key Defined action key
	 */
	def onKeyPressed(input:InputRelated,key:ControlKey){}
	
	/**
	 * On defined action key release
	 * 
	 * @param key Defined action key
	 */
	def onKeyReleased(input:InputRelated,key:ControlKey){}
	
	/**
	 * On raw key pressed
	 * 
	 * @param key LWJGL Keyboard code 
	 */
	def onKeyPressed(input:InputRelated,key:Int){}
	
	/**
	 * On raw key released
	 * 
	 * @param key LWJGL Keyboard code
	 */
	def onKeyReleased(input:InputRelated,key:Int){}
	
	/**
	 * When a character has been input
	 * 
	 * @param char Character 
	 */
	def onKeyChar(char:Char){}

	/**
	 * Input loop
	 */
	def handleInput(i:InputRelated){}
}

object Control {
	//TODO: Values of keyMap should be made external to a config file	
	/**
	 * Contains an array buffer with key mappings
	 */
	val keyMap =
		HashMap(
			Keyboard.KEY_LEFT   -> ControlKeys.MOVE_LEFT,
			Keyboard.KEY_RIGHT  -> ControlKeys.MOVE_RIGHT,
			Keyboard.KEY_DOWN   -> ControlKeys.MOVE_DOWN,
			Keyboard.KEY_UP     -> ControlKeys.MOVE_UP,
			Keyboard.KEY_Z      -> ControlKeys.JUMP,
			Keyboard.KEY_X      -> ControlKeys.PERFORM_ACTION,
			Keyboard.KEY_LSHIFT -> ControlKeys.CROUCH,
			Keyboard.KEY_1      -> ControlKeys.ACTION(1),
			Keyboard.KEY_2      -> ControlKeys.ACTION(2),
			Keyboard.KEY_3      -> ControlKeys.ACTION(3),
			Keyboard.KEY_4      -> ControlKeys.ACTION(4),
			Keyboard.KEY_5      -> ControlKeys.ACTION(5),
			Keyboard.KEY_6      -> ControlKeys.ACTION(6),
			Keyboard.KEY_7      -> ControlKeys.ACTION(7),
			Keyboard.KEY_8      -> ControlKeys.ACTION(8),
			Keyboard.KEY_9      -> ControlKeys.ACTION(9),
			Keyboard.KEY_0      -> ControlKeys.ACTION(0),
			Keyboard.KEY_T      -> ControlKeys.CONSOLE_KEY
		)

	def getMappedKey(keyCode:Int)=keyMap.get(keyCode)
}