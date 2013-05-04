package lolirofle.gl2dlib.data

/**
 * Mathematical object, a 2-dimensional vector. 
 */
case class Vector(x:Float,y:Float){
	def withLength(len:Double):Vector=
		if(len==0)
			NullVector
		else
			Vector((len*math.sin(direction)).toFloat,(len*math.cos(direction)).toFloat)

	def withDirection(angle:Double)=//TODO: Test this code
		Vector((x*math.cos(angle)-y*math.sin(angle)).toFloat,(x*math.sin(angle)+y*math.cos(angle)).toFloat);

	def asBiggerRatio=
		if(x==0||y==0)
			NullVector
		else{
			if(x-y>0)//If x is bigger than y
				Vector(x/y,1)
			else
				Vector(1,y/x)
		}
	
	def asSmallerRatio=
		if(x==0||y==0)
			NullVector
		else{
			if(x-y>0)//If x is bigger than y
				Vector(1,y/x)
			else
				Vector(x/y,1)
		}
	
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

object NullVector extends Vector(0,0)
