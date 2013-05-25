package lolirofle.data

object Vector{
	def apply(x:Float,y:Float)=PositionalVector(x,y)
	implicit def VectorToPositionalVector(v:Vector)=PositionalVector(v.x,v.y)
	implicit def VectorToPolarVector(v:Vector)=PolarVector(v.direction,v.length)
}

trait Vector{
	def x:Float
	def y:Float
	
	def length:Double
	def direction:Double
	
	final def toTuple = (x,y)
	
	def withLength(len:Double):Vector
	def withDirection(angle:Double):Vector
	def withX(x:Float):Vector
	def withY(y:Float):Vector
	
	def +(v:Vector):Vector
	def -(v:Vector):Vector
	def *(n:Float):Vector
	def /(n:Float):Vector
	def unary_- :Vector
	
	override def toString = "Vector("+x.toString+','+y.toString+')'
}

/**
 * Mathematical object, a 2-dimensional vector. 
 */
case class PositionalVector(override val x:Float,override val y:Float) extends Vector{
	override lazy val length=if(x==0)Math.abs(y) else if(y==0)Math.abs(x) else math.hypot(x,y)
	override lazy val direction=math.atan2(x,y)
	
	override def +(v:Vector)= PositionalVector(x+v.x,y+v.y)
	override def -(v:Vector)= PositionalVector(x-v.x,y-v.y)
	override def *(n:Float) = PositionalVector(x*n,y*n)
	override def /(n:Float) = PositionalVector(x/n,y/n)
	override def unary_- :Vector = PositionalVector(-x,-y)
	
	override def withX(x:Float)=PositionalVector(x,y)
	override def withY(y:Float)=PositionalVector(x,y)
	override def withLength(len:Double):Vector=
		if(len==0)
			NullVector
		else
			PositionalVector((len*math.sin(direction)).toFloat,(len*math.cos(direction)).toFloat)

	override def withDirection(angle:Double):Vector=
		PositionalVector((x*math.cos(angle)-y*math.sin(angle)).toFloat,(x*math.sin(angle)+y*math.cos(angle)).toFloat)
	
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
}

case class PolarVector(override val direction:Double,override val length:Double) extends Vector{
	override lazy val x=(length*math.sin(direction)).toFloat
 	override lazy val y=(length*math.cos(direction)).toFloat
	
	override def +(v:Vector)= PolarVector(x+v.x,y+v.y)
	override def -(v:Vector)= PolarVector(x-v.x,y-v.y)
	override def *(n:Float) = PolarVector(direction,length*n)
	override def /(n:Float) = PolarVector(direction,length/n)
	override def unary_- :Vector = PolarVector(direction,-length)
	
	override def withLength(length:Double):Vector=PolarVector(direction,length)
	override def withDirection(direction:Double):Vector=PolarVector(direction,length)
	override def withX(x:Float)=PolarVector(math.atan2(x,y),math.hypot(x,y))
	override def withY(y:Float)=PolarVector(math.atan2(x,y),math.hypot(x,y))
}

object NullVector extends PositionalVector(0,0){
	override lazy val length=0.0
	override lazy val direction=0.0
}
