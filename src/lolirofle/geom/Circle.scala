package lolirofle.geom

import lolirofle.gl2d.GLDraw
import lolirofle.data.Position

case class Circle(var radius:Float) extends Shape{
	def diameter=radius*2;
	override def width=diameter;
	override def height=diameter;
	override def perimeter=(radius*2*math.Pi).toFloat
	override def area=(radius*radius*math.Pi).toFloat
	override def midpoint=Position(radius,radius)
	
	override def draw(x:Float,y:Float,filled:Boolean){
		GLDraw.drawCircle(x,y,radius,filled);
	}
}