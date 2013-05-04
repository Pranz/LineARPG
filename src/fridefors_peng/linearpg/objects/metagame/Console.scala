package fridefors_peng.linearpg.objects.metagame

import fridefors_peng.linearpg.objects.Renderable
import fridefors_peng.linearpg.Main
import org.newdawn.slick.{Graphics, Font, TrueTypeFont}

class Console extends Renderable {
	
	val regexSplit = " (?=([^\"]*\"[^\"]*\")*[^\"]*$)"
	var msg = ""
	var isOn = false
	var history = List[String]()
	def draw(g : Graphics) : Unit = {
		(if(isOn)
			(">" + msg :: history.take(5)) 
			else history.take(5)
			).zipWithIndex.foreach{case (str, i) => {
			g.drawString(str, 16, Main.HEIGHT - 32 - 18*i)
		}}
	}
	def sendMsg() : Unit = {
		if(msg != "")history = msg :: history
		msg = ""
	}
	def update(dt : Int) : Unit = {}

}