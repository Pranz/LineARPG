package fridefors_peng.linearpg.console

import fridefors_peng.linearpg.objects.GUIRenderable
import collection.mutable.ArrayBuffer
import lolirofle.gl2d.GLDraw
import lolirofle.graphics2d.Color
import java.io.OutputStream
import scala.collection.mutable.Queue
import fridefors_peng.linearpg.input._
import fridefors_peng.linearpg.console.applications.{DefaultShellIOHandlerProcess}
import org.lwjgl.input.Keyboard
import lolirofle.util.MiscUtil
import lolirofle.GameHandler
import lolirofle.util.NumUtil
import lolirofle.geom.Rectangle
import lolirofle.data.Position

/**
 * ApplicationConsole
 * 
 * A Console/Terminal that runs Applications
 */
class ApplicationConsole extends GUIRenderable with Control{
	var process:Process=new DefaultShellIOHandlerProcess()
	
	/**
	 * The maximum amount of visible lines
	 */
	var maxVisibleLines=12

	override def draw(windowWidth:Float,windowHeight:Float){
		val lines=process.output(maxVisibleLines)
		val linesCount=lines.length
		
		val margin=8
		val padding=4
		
		val x=margin
		val h=GLDraw.font.lineHeight*maxVisibleLines
		val y=windowHeight-h-margin
		val w=256
		
		//Background
		GLDraw.color=Color(32,32,32,64)
		GLDraw.drawRectangle(x,y-padding*2,x+w+padding,y+h,true)
		
		//Background Border
		GLDraw.color=Color(64,64,64,64)
		GLDraw.drawRectangle(x,y-padding*2,x+w+padding,y+h)
		
		GLDraw.color=Color.WHITE

		//Text
		lines.takeRight(maxVisibleLines).foldRight(0f){(line,yAdvance) => {
			val yAdv=yAdvance-GLDraw.font.heightOf(line)
			GLDraw.font.drawString(x+padding,y+h-padding+yAdv,line)
			yAdv
		}}
	}
	
	override def onKeyChar(char:Char){
		process.onChar(char)
	}
	
	override def onKeyPressed(input:InputRelated,key:Int){
		if(key==Keyboard.KEY_C){//TODO: Shift+Insert
			if(input.keyIsDown(Keyboard.KEY_LCONTROL))
				process.onFunc(ConsoleFunction.CLIPBOARD_COPY)
		}
		else if(key==Keyboard.KEY_V){
			if(input.keyIsDown(Keyboard.KEY_LCONTROL))
				process.onFunc(ConsoleFunction.CLIPBOARD_PASTE)
		}
		else if(key==Keyboard.KEY_LEFT)
			process.onFunc(ConsoleFunction.GOTO_LEFT)
		else if(key==Keyboard.KEY_RIGHT)
			process.onFunc(ConsoleFunction.GOTO_RIGHT)
		else if(key==Keyboard.KEY_UP)
			process.onFunc(ConsoleFunction.GOTO_PREVIOUS)
		else if(key==Keyboard.KEY_DOWN)
			process.onFunc(ConsoleFunction.GOTO_NEXT)
		else if(key==Keyboard.KEY_HOME)
			process.onFunc(ConsoleFunction.GOTO_START)
		else if(key==Keyboard.KEY_END)
			process.onFunc(ConsoleFunction.GOTO_END)
		else if(key==Keyboard.KEY_DELETE)
			process.onFunc(ConsoleFunction.DELETE_FRONT)
		else if(key==Keyboard.KEY_BACK)
			process.onFunc(ConsoleFunction.DELETE_REAR)
	}
}

object ConsoleFunction extends Enumeration{
	val
		DELETE_REAR,
		DELETE_FRONT,
		CLIPBOARD_COPY,
		CLIPBOARD_PASTE,
		GOTO_START,
		GOTO_END,
		GOTO_LEFT,
		GOTO_RIGHT,
		GOTO_PREVIOUS,
		GOTO_NEXT
	=Value
}
