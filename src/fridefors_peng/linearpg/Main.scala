package fridefors_peng.linearpg
import lolirofle.gl2dlib.graphics.text.DefaultFont
import lolirofle.gl2dlib.Game
import lolirofle.gl2dlib.GameHandler

object Main{	
	val TILE_SIZE = 16
	var game:Game=null
	
	def main(args: Array[String]){
		game=new LineARPGame()
		
		GameHandler.init(game,"Test",displayWidth=1024,displayHeight=756);
		game.title="LineARPG"
		game.FPS=60
		GameHandler.start();
	}
	
	def drawList(x:Float,y:Float,list:List[String]) {
		for (i <- 0 to list.size-1){
			DefaultFont.font.drawString(x,y+16*i,list(i))
		}
	}
}
