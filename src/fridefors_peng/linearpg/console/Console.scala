package fridefors_peng.linearpg.console

import fridefors_peng.linearpg.objects.GUIRenderable
import collection.mutable.ArrayBuffer
import scala.collection.JavaConversions._
import lolirofle.gl2d.GLDraw
import lolirofle.graphics2d.Color
import java.io.OutputStream
import scala.collection.mutable.Queue
import fridefors_peng.linearpg.input._
import org.lwjgl.input.Keyboard
import lolirofle.util.MiscUtil
import lolirofle.GameHandler
import lolirofle.util.NumUtil
import lolirofle.geom.Rectangle
import lolirofle.data.Position
import fridefors_peng.linearpg.objects.Updatable

case class ConsoleOutputLine(str:String,time:Long)

class Console extends GUIRenderable with Updatable with Control{
	val regexSplit = " (?=([^\"]*\"[^\"]*\")*[^\"]*$)"
	var inputString = new StringBuilder()
	var inputPosition=0//TODO: Input position with line drawn out when font.string_width is fixed 
	val commandPrefix = '/'

	def sendMsg() : Unit = if(!inputString.isEmpty){ 
		lines.enqueue(inputString.toString)
		val(command :: args) = inputString.toString.split(regexSplit).toList
		if(command.head == commandPrefix)
			commands.foreach{case (cmdName,_,f) => {
				if(cmdName == command.tail)
					f(args)
		}}
		inputString.clear()
		inputPosition=0
	}
	
	/**
	 * The lines of text in output
	 */
	var lines = new Queue[ConsoleOutputLine](){
		def enqueue(str:String){
			enqueue(ConsoleOutputLine(str,timeElapsed))
		}
	}
	
	/**
	 * The time each line takes to fade out in milliseconds (ms)
	 */
	var fadeTime  = 10000
	
	/**
	 * The time to wait before each lines fades out in milliseconds (ms)
	 */
	var fadeDelay = 5000
	
	/**
	 * The maximum amount of visible lines
	 */
	var maxVisibleLines=12
	
	/**
	 * Time elapsed since console start in milliseconds (ms)
	 */
	protected var timeElapsed=0L
	
	private val commands : ArrayBuffer[(String, String, (List[String] => Unit))] = 
		ArrayBuffer(
			("help", "This help screen", _ => commands.foreach{case (name, description, _) => 
				lines.enqueue(name + "    //    " + description)
			}),
			("echo", "echoes every argument on a seperate line", _.foreach{(str) => lines.enqueue(str)})
		)

	override def draw(windowWidth:Float,windowHeight:Float){
		val linesCount=lines.length

		//TODO: Reworking with the positioning. At the moment y is the bottom of output but above input
		val x=16
		val y=windowHeight-100
		val x2=x+256
		val y2=y-GLDraw.font.lineHeight*maxVisibleLines
		
		//Background
		GLDraw.color=Color(32,32,32,64)
		GLDraw.drawRectangle(x,y,x2,y2,true)
		
		//Background Border
		GLDraw.color=Color(64,64,64,64)
		GLDraw.drawRectangle(x,y,x2,y2)
		
		//Text
		lines.takeRight(maxVisibleLines).zipWithIndex.foreach{case(line,i) => {
			GLDraw.color=Color(255,255,255, 255 - (255*NumUtil.limit((timeElapsed-line.time-fadeDelay).toDouble/fadeTime,0,1)).toInt)
			GLDraw.font.drawString(x+4,y+4+GLDraw.font.lineHeight*(i-math.min(maxVisibleLines,linesCount)),line.str)
		}}
		
		GLDraw.color=Color.WHITE
		
		GLDraw.font.drawString(x+4,y+2,"> "+inputString)
	}

	override def update(delta:Int){
		timeElapsed+=delta
	}

	override def onKeyPressed(input:InputRelated,key:Int){
		if(key==Keyboard.KEY_C){
			if(input.keyIsDown(Keyboard.KEY_LCONTROL))
				MiscUtil.clipboard_=(inputString.toString)
		}
		else if(key==Keyboard.KEY_V){
			if(input.keyIsDown(Keyboard.KEY_LCONTROL))
				inputString.insert(inputPosition,MiscUtil.clipboardString)//.append
		}
		else if(key==Keyboard.KEY_LEFT){
			if(inputPosition>0)
				inputPosition-=1
		}
		else if(key==Keyboard.KEY_RIGHT){
			if(inputPosition<inputString.length-1)
				inputPosition+=1
		}
		else if(key==Keyboard.KEY_HOME){
			inputPosition=0
		}
		else if(key==Keyboard.KEY_END){
			inputPosition=inputString.length
		}
	}
	
	override def onKeyChar(char:Char){
		if(char==8){//Backspace
			if(inputPosition>0){
				inputString = inputString.deleteCharAt(inputPosition-1)//.init
				inputPosition-=1
			}
		}

		else if(char==127){//Delete
			if(inputPosition==0)
				inputString=inputString.tail
			else if(inputPosition<inputString.length)
				inputString.deleteCharAt(inputPosition)
		}

		else if(char==13)//Return/Enter
			sendMsg()
			
		else if(char=='	' || (char>=32 && char<256)){//Visible Symbol
			inputString.insert(inputPosition,char)//.append
			inputPosition+=1
		}
	}
}