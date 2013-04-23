package fridefors_peng.linearpg

case class Vector(x:Float, y:Float) {
	def length = math sqrt(x * x + y * y)
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
	def direction = math atan2(x, y)
	
	def +(v:Vector):Vector = Vector(this.x + v.x, this.y + v.y)
	def -(v:Vector):Vector = Vector(this.x - v.x, this.y - v.y)
	def *(n:Float):Vector = Vector(this.x * n, this.y * n)
	def unary_- = Vector(-x, -y)
	def toTuple = (x, y)
	
	override def toString = x.toString + ", " + y.toString
}

case class PolarVector(direction:Double, length:Double)

object NullVector extends Vector(0, 0)