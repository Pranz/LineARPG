package lolirofle.gl2dlib.geom

import lolirofle.gl2dlib.gl.GLGeom
import lolirofle.gl2dlib.gl.GLDraw
import scala.collection.mutable.ArrayBuffer
import lolirofle.gl2dlib.data.Position

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
}