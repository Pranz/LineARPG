package fridefors_peng.linearpg.objects

import fridefors_peng.linearpg.Vector
import lolirofle.gl2dlib.geom.Shape

class TestObject(pos:Vector,body:Shape) extends Interactive(pos,body) with Renderable with Physical{
	var gravity      = .0f
	var movement     = V(2, 1.8f)
	var acceleration = V(0,0)
	friction         = 0.02

	def draw(): Unit = {
		body.draw()
	}

	override def update(delta:Int): Unit = {
		super.update(delta)
	}
}