package fridefors_peng.linearpg.objects.metagame

import fridefors_peng.linearpg.objects.Renderable
import fridefors_peng.linearpg.Main
import org.newdawn.slick.{Graphics, Font, TrueTypeFont, Color}
import collection.mutable.ArrayBuffer
import scala.collection.JavaConversions._

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
	def draw(g : Graphics) : Unit = {
		(if(isOn)
			(">" + msg :: history.take(maxHistory)).zip(0 :: alphaHistory)
			else history.take(maxHistory).zip(alphaHistory)
			).zipWithIndex.foreach{case ((str, alphaTime), i) => {
				g.setColor(new Color(255,255,255, 255 - 255 * alphaTime/fadeTime))
				g.drawString(str, 16, Main.HEIGHT - 32 - 18*i)
				g.setColor(Color.white)
		}}
	}
	def sendMsg() : Unit = if(msg != "") { 
		addHistory(msg)
		val(command :: flags) = msg.split(regexSplit).toList
		if(command.first == commandPrefix) commands.foreach{case (cmdName, _, f) => {
			if(cmdName == command.tail) f(flags)
		}}
		msg = ""
	}
	def update(dt : Int) : Unit = {
		alphaHistory = alphaHistory.map(_ + dt)
	}
	def addCommand(cmd : (String, String, (List[String] => Unit))) : Unit = commands += cmd
	def addHistory(str : String) : Unit = {
		history = str :: history
		alphaHistory = -fadeDelay :: alphaHistory
	}

}