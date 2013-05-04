package fridefors_peng.linearpg

import org.newdawn.slick.{Input, AppGameContainer, Graphics, UnicodeFont}
import fridefors_peng.linearpg.objects.metagame.Console

object Main{
	
	val WORK_DIR = System.getProperty("user.dir")
	val FONT_DIR = WORK_DIR + "/resources/fonts/"
	val IMAGE_DIR = WORK_DIR + "/resources/image/"
	val FNT_ANDALE_MONO = new UnicodeFont(FONT_DIR + "AndaleMono.ttf", 16, false, false)
	val console = new Console()
	
	val TITLE = "LineARPG"
	val TILE_SIZE = 16
	val WIDTH = 1024
	val HEIGHT = 756
	var input:Input = null
	
	def main(args: Array[String]) {
		val container = createAppGameContainer()
		container start

	}
	
	def createAppGameContainer() : AppGameContainer = {
		val app = new AppGameContainer(new LineARPG(), WIDTH, HEIGHT, false);
		app.setUpdateOnlyWhenVisible(false);
		app.setTitle(TITLE);
		app.setShowFPS(false);
		app.setTargetFrameRate(60);
		return app;
	}
	
	var Camera = Vector(0,0)
	
	def drawList(list:List[String], g:Graphics) {
		for (i <- 0 to list.size - 1){
			g.drawString(list(i), 0, 16*i)
		}
	}
}
