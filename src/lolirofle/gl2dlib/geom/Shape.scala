package lolirofle.gl2dlib.geom

import lolirofle.gl2dlib.graphics.Drawable
import lolirofle.gl2dlib.data.Position

trait Shape extends Drawable{
	def perimeter:Float;
	def area:Float;
	def draw(){draw(0,0,false);}
	def draw(x:Float,y:Float,filled:Boolean);
	
	def at(x:Float,y:Float)=PositionedShape(this,x,y);
	def at(pos:Position)=PositionedShape(this,pos.x,pos.y);
}