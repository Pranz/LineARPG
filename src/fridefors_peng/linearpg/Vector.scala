package fridefors_peng.linearpg

import lolirofle.gl2dlib.data.Position

/**
 * Mathematical object, a 2-dimensional vector. 
 */
case class Vector(x:Float, y:Float)extends Position{
	def whoseLengthIs (newl:Double):Vector = {
		if (newl == 0) return this
		if (length <= math.abs(newl) ) return {
			NullVector
		}
		else return {
			val dir = direction
				Vector((newl * math.sin(dir)).toFloat,
				y = (newl * math.cos(dir)).toFloat)
		}

	}
		
	def length=math.hypot(x,y)
	def direction=math.atan2(x,y)
	
	def +(v:Vector):Vector = Vector(this.x + v.x, this.y + v.y)
	def -(v:Vector):Vector = Vector(this.x - v.x, this.y - v.y)
	def *(n:Float):Vector = Vector(this.x * n, this.y * n)
	def /(n:Float):Vector = Vector(this.x / n, this.y / n)
	def unary_- = Vector(-x, -y)
	def toTuple = (x, y)
	
	override def toString = x.toString + ", " + y.toString
}

case class PolarVector(direction:Double, length:Double)//TODO: Extend Point and override x,y with functions

object NullVector extends Vector(0, 0)