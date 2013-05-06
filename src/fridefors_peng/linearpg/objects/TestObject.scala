package fridefors_peng.linearpg.objects

import lolirofle.gl2dlib.data.Vector
import lolirofle.gl2dlib.geom.Shape
import lolirofle.gl2dlib.data.Position

class TestObject(pos:Position,body:Shape) extends Matter(pos,body) with Renderable with Mass{
	var gravity      = .0f
	movement     = Vector(2, 1.8f)
	acceleration = Vector(0,0)
	override val friction     = 0.02f

	def draw(): Unit = {
		body.draw()
	}

	override def update(delta:Int): Unit = {
		super.update(delta)
	}
}