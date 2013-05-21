package fridefors_peng.linearpg.input

import fridefors_peng.linearpg.console.{Console,ApplicationConsole}
import org.lwjgl.input.Keyboard

class GameControl(var mainControl:Control) extends Control{
	var consoleControl=new ApplicationConsole()
	
	var currentControl:Control=mainControl
	
	override def onKeyPressed(input:InputRelated,key:ControlKey){
		if(key==ControlKeys.CONSOLE_KEY&&currentControl==mainControl)
			currentControl=consoleControl
		else
			currentControl.onKeyPressed(input,key)
	}

	override def onKeyReleased(input:InputRelated,key:ControlKey){
		currentControl.onKeyReleased(input,key)
	}

	override def onKeyPressed(input:InputRelated,key:Int){
		if(currentControl==mainControl){
			if(key==Keyboard.KEY_ESCAPE)
				input.close()
			else if(key==Keyboard.KEY_R)
				input.reset();
			else
				currentControl.onKeyPressed(input,key)
		}
		else if(currentControl==consoleControl){
			if(key==Keyboard.KEY_ESCAPE || key==Keyboard.KEY_RETURN)
				currentControl=mainControl
			else
				currentControl.onKeyPressed(input,key)
		}
		else
			currentControl.onKeyPressed(input,key)
	}

	override def onKeyReleased(input:InputRelated,key:Int){
		currentControl.onKeyReleased(input,key)
	}
	
	override def onKeyChar(char:Char){
		currentControl.onKeyChar(char)
	}

	override def handleInput(i:InputRelated){
		currentControl.handleInput(i)
	}
}