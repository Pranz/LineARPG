package fridefors_peng.linearpg.objects

import org.newdawn.slick.Graphics
import org.newdawn.slick.geom.Shape
import fridefors_peng.linearpg.Vector

class TestObject( position:Vector, body:Shape) extends Interactive(position, body) 
	with Renderable with Physical {
  
  var gravity      = .0f
  var movement     = V(2, 1.8f)
  var accerelation = V(0,0)
  friction         = 0.02

  def draw(g: Graphics): Unit = {
    g.draw(body)
  }

  override def update(): Unit = {
    super.update
  }
  
}