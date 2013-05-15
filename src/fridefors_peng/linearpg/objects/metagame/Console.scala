package fridefors_peng.linearpg.objects.metagame

import fridefors_peng.linearpg.objects.Renderable
import fridefors_peng.linearpg.Main
import collection.mutable.ArrayBuffer
import scala.collection.JavaConversions._
import lolirofle.gl2dlib.gl.GLDraw
import lolirofle.gl2dlib.graphics.Color

class Console extends Renderable {
	
	val regexSplit = " (?=([^\"]*\"[^\"]*\")*[^\"]*$)"
	var msg = ""
	var isOn = false
	var history = List[String]()
	var alphaHistory = List[Int]()
	val fadeTime  = 3000 //ms
	val fadeDelay = 2000
	val maxHistory = 10
	val commandPrefix = '-'
	
	private val commands : ArrayBuffer[(String, String, (List[String] => Unit))] = 
	ArrayBuffer(
			("help", "This help screen", _ => commands.foreach{case (name, description, _) => 
				addHistory(name + "    //    " + description)
			}),
			("echo", "echoes every argument on a seperate line", _.foreach{(str) => addHistory(str)})
	)
	override def draw(){
		(if(isOn)
			(">" + msg :: history.take(maxHistory)).zip(0 :: alphaHistory)
			else history.take(maxHistory).zip(alphaHistory)
			).zipWithIndex.foreach{case ((str, alphaTime), i) => {
				GLDraw.color=Color(255,255,255, 255 - 255 * alphaTime/fadeTime)
				GLDraw.font.drawString(16, 640 - 32 - 18*i,str)//TODO: General way to get height of window instead of constant (640), maybe through parameters in draw. A display
				GLDraw.color=Color.WHITE
		}}
	}
	def sendMsg() : Unit = if(msg != "") { 
		addHistory(msg)
		val(command :: flags) = msg.split(regexSplit).toList
		if(command.head == commandPrefix) commands.foreach{case (cmdName, _, f) => {
			if(cmdName == command.tail) f(flags)
		}}
		msg = ""
	}
	override def update(delta:Int):Unit = {
		alphaHistory = alphaHistory.map(_+delta)
	}
	def addCommand(cmd : (String, String, (List[String] => Unit))) : Unit = commands += cmd
	def addHistory(str : String) : Unit = {
		history = str :: history
		alphaHistory = -fadeDelay :: alphaHistory
	}

}