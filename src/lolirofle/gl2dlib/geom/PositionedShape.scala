package lolirofle.gl2dlib.geom

import lolirofle.gl2dlib.graphics.Drawable
import lolirofle.gl2dlib.gl.GLDraw
import lolirofle.gl2dlib.data.Position

case class PositionedShape[T<:Shape](val shape:T,var x:Float,var y:Float) extends Drawable{
	def insideOf[T<:Shape](other:PositionedShape[T]):Boolean=shape match{
		case s:Tetragon=>		
			x+shape.width  > other.x &&
			x              < other.x+other.shape.width &&
			y+shape.height > other.y &&
			y              < other.y+other.shape.height;
		case _=>false
	}
	
	override def draw(){
		draw(false);
	}
	
	def draw(filled:Boolean){
		shape.draw(x,y,filled);
	}
	
	def width=shape.width
	def height=shape.height
}
