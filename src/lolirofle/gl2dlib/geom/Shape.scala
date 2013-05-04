package lolirofle.gl2dlib.geom

import lolirofle.gl2dlib.graphics.Drawable
import lolirofle.gl2dlib.data.Position

trait Shape extends Drawable{
	def perimeter:Float;
	def area:Float;
	def draw(){draw(0,0,false);}
	def draw(x:Float,y:Float,filled:Boolean);
	
	//When rotation and flipping are accepted
	def isCongruent(p:Polygon){}//TODO
	
	//When rotation, flipping and resizing are accepted
	def isSimiliar(p:Polygon){}//TODO
	
	def isResized(p:Polygon){}
	def isRotated(p:Polygon){}
	def isFlipped(p:Polygon){}
	
	def at(x:Float,y:Float)=PositionedShape(this,x,y);
	def at(pos:Position)=PositionedShape(this,pos.x,pos.y);
}