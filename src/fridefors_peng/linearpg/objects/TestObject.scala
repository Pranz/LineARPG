package fridefors_peng.linearpg.objects

import lolirofle.gl2dlib.data.Vector
import lolirofle.gl2dlib.geom.Shape
import lolirofle.gl2dlib.data.Position

class TestObject(pos:Position,body:Shape) extends Interactable(pos,body) with Renderable with Matter{
	override val gravity=0f
	force     = Vector(2, 1.8f)
	override val acceleration = Vector(0,0)
	override val friction     = 0.02f

	def draw(): Unit = {
		(body at position).draw
	}

	override def update(delta:Int): Unit = {
		super.update(delta)
	}
}