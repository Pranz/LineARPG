package lolirofle.geom

import lolirofle.data.Position
import lolirofle.gl2d.GLDraw

case object Point extends RegularPolygon{
	override val sides=1
	override val side=1f
	override val width=1f
	override val height=1f
	override val vertices=List(Position(0,0));
	override val midpoint=Position(0.5f,0.5f)
	
	override def draw(x:Float,y:Float,filled:Boolean){
		GLDraw.drawPoint(x,y)
	}
}
