package fridefors_peng.linearpg

import lolirofle.gl2dlib.data.Position

/**
 * Mathematical object, a 2-dimensional vector. 
 */
case class Vector(x:Float,y:Float)extends Position{//TODO: withDirection
	def withLength(len:Double):Vector=
		if(len==0)
			NullVector
		else
			Vector((len*math.sin(direction)).toFloat,(len*math.cos(direction)).toFloat)

	lazy val length=math.hypot(x,y)
	lazy val direction=math.atan2(x,y)
	
	def +(v:Vector)= Vector(this.x + v.x, this.y + v.y)
	def -(v:Vector)= Vector(this.x - v.x, this.y - v.y)
	def *(n:Float) = Vector(this.x * n, this.y * n)
	def /(n:Float) = Vector(this.x / n, this.y / n)
	def unary_- = Vector(-x,-y)
	def toTuple = (x,y)
	
	override def toString = x.toString + ", " + y.toString
}

case class PolarVector(direction:Double,length:Double)//TODO: Extend Position and override x,y with functions

object NullVector extends Vector(0,0)
