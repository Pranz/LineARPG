package fridefors_peng.linearpg

import org.newdawn.slick.{Input, AppGameContainer, Graphics}

object Main{
  
  val TITLE = "LineARPG"
  val TILE_SIZE = 16
  var input:Input = null
	
	def main(args: Array[String]) {
    val container = createAppGameContainer()
    container start

  }
  
  def createAppGameContainer() : AppGameContainer = {
		val
		app = new AppGameContainer(new LineARPG(), 1024, 756, false);
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
