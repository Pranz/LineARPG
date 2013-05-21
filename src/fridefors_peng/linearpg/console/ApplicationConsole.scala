package fridefors_peng.linearpg.console

import fridefors_peng.linearpg.objects.GUIRenderable
import collection.mutable.ArrayBuffer
import scala.collection.JavaConversions._
import lolirofle.gl2dlib.gl.GLDraw
import lolirofle.gl2dlib.graphics.Color
import java.io.OutputStream
import scala.collection.mutable.Queue
import fridefors_peng.linearpg.input._
import fridefors_peng.linearpg.console.applications.{Application,IOHandlerApplication}
import org.lwjgl.input.Keyboard
import lolirofle.gl2dlib.util.MiscUtil
import lolirofle.gl2dlib.GameHandler
import lolirofle.gl2dlib.util.NumUtil
import lolirofle.gl2dlib.geom.Rectangle
import lolirofle.gl2dlib.data.Position
import fridefors_peng.linearpg.console.applications.Applications

class ApplicationConsole extends GUIRenderable with Control{
	var app:Application=new IOHandlerApplication(){
		var shouldClose=false
		
		override def onInit(args:Seq[String]){}
		
		override def input(in:String){ 
			val(command :: args)=in.toString.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)").toList
			Applications.list.get(command) match{
				case Some(app)=>
					app.start().main(this,args)
				case None=>{}
			}
		}
	}
	
	/**
	 * The maximum amount of visible lines
	 */
	var maxVisibleLines=12

	override def draw(){
		val lines=app.output(maxVisibleLines)
		val linesCount=lines.length

		//TODO: Reworking with the positioning. At the moment y is the bottom of output but above input
		val x=16
		val y=640//TODO: General way to get height of window instead of constant (640), maybe through parameters in draw. A display
		val x2=x+256
		val y2=y-GLDraw.font.lineHeight*maxVisibleLines
		
		//Background
		GLDraw.color=Color(32,32,32,64)
		GLDraw.drawRectangle(x,y,x2,y2,true)
		
		//Background Border
		GLDraw.color=Color(64,64,64,64)
		GLDraw.drawRectangle(x,y,x2,y2)
		
		GLDraw.color=Color.WHITE
		
		//Text
		lines.takeRight(maxVisibleLines).zipWithIndex.foreach{case(line,i) => {
			GLDraw.font.drawString(x+4,y+4+GLDraw.font.lineHeight*(i-math.min(maxVisibleLines,linesCount)),line)
		}}
	}
	
	override def onKeyChar(char:Char){
		app.onChar(char)
	}
	
	override def onKeyPressed(input:InputRelated,key:Int){
		if(key==Keyboard.KEY_C){//TODO: Shift+Insert
			if(input.keyIsDown(Keyboard.KEY_LCONTROL))
				app.onFunc(ConsoleFunction.CLIPBOARD_COPY)
		}
		else if(key==Keyboard.KEY_V){
			if(input.keyIsDown(Keyboard.KEY_LCONTROL))
				app.onFunc(ConsoleFunction.CLIPBOARD_PASTE)
		}
		else if(key==Keyboard.KEY_LEFT)
			app.onFunc(ConsoleFunction.GOTO_LEFT)
		else if(key==Keyboard.KEY_RIGHT)
			app.onFunc(ConsoleFunction.GOTO_RIGHT)
		else if(key==Keyboard.KEY_UP)
			app.onFunc(ConsoleFunction.GOTO_PREVIOUS)
		else if(key==Keyboard.KEY_DOWN)
			app.onFunc(ConsoleFunction.GOTO_NEXT)
		else if(key==Keyboard.KEY_HOME)
			app.onFunc(ConsoleFunction.GOTO_START)
		else if(key==Keyboard.KEY_END)
			app.onFunc(ConsoleFunction.GOTO_END)
	}
}

object ConsoleFunction extends Enumeration{
	val
		DELETE_BACK,
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