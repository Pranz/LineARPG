package fridefors_peng.linearpg
import lolirofle.graphics2d.text.DefaultFont
import lolirofle.Game
import lolirofle.GameHandler

object Main{
	val TILE_SIZE = 16
	var game:Game=null

	var restart=false;
	
	def main(args:Array[String]){		
		do{
			restart=false;
			
			game=new LineARPGame()
			
			GameHandler.init(game,"Test",displayWidth=1024,displayHeight=756);
			GameHandler.start();
		}while(restart);
	}
	
	def drawStrings(x:Float,y:Float,list:Seq[String]){
		list.view.zipWithIndex.foreach{case(str,i)=>DefaultFont.drawString(x,y+16*i,str)}
	}
}
