package lolirofle.geom

import lolirofle.gl2d.GLGeom
import lolirofle.gl2d.GLDraw
import scala.collection.mutable.ArrayBuffer
import lolirofle.data.Position

trait Tetragon extends Polygon{
	def width:Float;
	def height:Float;
	
	def hypotenuse=math.hypot(width,height)
	override def area=width*height
	override def perimeter=width*2+height*2
	override def sides=4
	
	override def vertices=List(Position(0,0),Position(0,width),Position(width,height),Position(0,height));
	
	override def draw(x:Float,y:Float,filled:Boolean){
		GLDraw.drawRectangle(x,y,x+width,y+height,filled)
	}
	
	override def midpoint=Position(width*0.5f,height*0.5f)
}