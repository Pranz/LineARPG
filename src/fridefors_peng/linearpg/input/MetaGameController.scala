package fridefors_peng.linearpg.input

import org.newdawn.slick.Input
import fridefors_peng.linearpg.objects.metagame.Console
import fridefors_peng.linearpg.Main
import java.awt.Toolkit;
import java.awt.datatransfer.{DataFlavor, StringSelection, Transferable, UnsupportedFlavorException}
import java.io.IOException

class MetaGameController(id:Int) extends Control(id) {

	val reverseKeyMap = keyMap.map(_ swap).withDefaultValue(-1)
	def handleKeys(input: Input): Unit = {}
	def keyPressed(key: Int, char: Char): Unit = {
		reverseKeyMap(key) match {
			case Control.Key.CONSOLE_KEY => {
				if(Main.console.isOn) {
					unSolo()
					Main.input.disableKeyRepeat()
					Main.console.sendMsg
					Main.console.isOn = false
				
				}
				else {
					solo()
					Main.input.enableKeyRepeat()
					Main.console.isOn = true
				}
			}
			case _ => {}
		}
		if(Main.console.isOn) key match {
			case 14  => /*backspace*/ if(Main.console.msg != "") Main.console.msg = Main.console.msg.reverse.tail.reverse
			case _   => if(char >= 32 && char < 256) Main.console.msg += char
		}
		if (key ==Input.KEY_C && Main.input.isKeyDown(Input.KEY_LCONTROL)) setClipBoard(Main.console.msg)
		if (key ==Input.KEY_V && Main.input.isKeyDown(Input.KEY_LCONTROL)) Main.console.msg += getClipBoard
	}
	
	def keyReleased(arg0: Int, arg1: Char): Unit = {
		
	}
	
	//Copied from Misc
	def setClipBoard(str:String) {
		val ss : StringSelection = new StringSelection(str);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}
	
	def getClipBoard : String = {
		val t : Transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

	    try{
	    	if(t!=null&&t.isDataFlavorSupported(DataFlavor.stringFlavor)){
	        	val text = t.getTransferData(DataFlavor.stringFlavor).asInstanceOf[String]
	            return text;
	        }
	    }
	    catch {
	    	case e : UnsupportedFlavorException => {}
	    	case e : IOException => {}
	    }
	    return null;
	}

}