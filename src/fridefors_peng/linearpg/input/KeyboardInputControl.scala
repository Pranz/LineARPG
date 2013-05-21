package fridefors_peng.linearpg.input

import java.io.InputStream

class KeyboardInputControl extends Control{
	protected val buffer=new StringBuilder()
	
	override def onKeyChar(char:Char){
		buffer+=char
	}
	
	def get=buffer.toString
	
	def clear{
		buffer.clear()
	}
}