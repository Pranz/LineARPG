package fridefors_peng.linearpg.input

import fridefors_peng.linearpg.objects.metagame.Console
import fridefors_peng.linearpg.Main
import lolirofle.gl2dlib.util.MiscUtil
import org.lwjgl.input.Keyboard
import lolirofle.gl2dlib.GameHandler

class MetaGameController(id:Int,console:Console) extends Control(id){
	val reverseKeyMap = keyMap.map(_.swap).withDefaultValue(-1)
	override def keyPressed(key: Int){
		reverseKeyMap(key) match {
			case Control.Key.CONSOLE_KEY => {
				if(console.isOn) {
					unSolo()
					console.sendMsg
					console.isOn = false
				
				}
				else {
					solo()
					console.isOn = true
				}
			}
			case _ => {}
		}
		if (key ==Keyboard.KEY_C && GameHandler.keyIsDown(Keyboard.KEY_LCONTROL)) MiscUtil.clipboard_=(console.msg)
		if (key ==Keyboard.KEY_V && GameHandler.keyIsDown(Keyboard.KEY_LCONTROL)) console.msg += MiscUtil.clipboardString
	}
	
	override def keyReleased(key:Int){
		
	}
	
	override def keyChar(char:Char){
		if(console.isOn) char match {
			case 14  => /*backspace*/ if(console.msg != "") console.msg = console.msg.reverse.tail.reverse
			case _   => if(char >= 32 && char < 256) console.msg += char
		}
	}
	
	override def update(delta:Int){}
}