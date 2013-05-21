package fridefors_peng.linearpg
import lolirofle.gl2dlib.graphics.text.DefaultFont
import lolirofle.gl2dlib.Game
import lolirofle.gl2dlib.GameHandler

object Main{
	val TILE_SIZE = 16
	var game:Game=null
	
	val WIDTH=1024//TODO: Better way of doing this
	val HEIGHT=756
	
	var restart=false;
	
	def main(args:Array[String]){
		do{
			restart=false;
			
			game=new LineARPGame()
			
			GameHandler.init(game,"Test",displayWidth=WIDTH,displayHeight=HEIGHT);
			GameHandler.start();
		}while(restart);
	}
	
	def drawStrings(x:Float,y:Float,list:Seq[String]){
		list.view.zipWithIndex.foreach{case(str,i)=>DefaultFont.drawString(x,y+16*i,str)}
	}
}
