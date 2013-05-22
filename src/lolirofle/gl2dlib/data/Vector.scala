package lolirofle.gl2dlib.data

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
	
	def withLength(len:Double):this.type
	def withDirection(angle:Double):this.type
	def withX(x:Float):this.type
	def withY(y:Float):this.type
	
	def +(v:Vector):this.type
	def -(v:Vector):this.type
	def *(n:Float):this.type
	def /(n:Float):this.type
	def unary_- :this.type
	
	override def toString = "Vector("+x.toString+','+y.toString+')'
}

/**
 * Mathematical object, a 2-dimensional vector. 
 */
case class PositionalVector(override val x:Float,override val y:Float) extends Vector{
	override lazy val length=if(x==0)Math.abs(y) else if(y==0)Math.abs(x) else math.hypot(x,y)
	override lazy val direction=math.atan2(x,y)
	
	override def +(v:Vector)= PositionalVector(x+v.x,y+v.y).asInstanceOf[this.type]
	override def -(v:Vector)= PositionalVector(x-v.x,y-v.y).asInstanceOf[this.type]
	override def *(n:Float) = PositionalVector(x*n,y*n).asInstanceOf[this.type]
	override def /(n:Float) = PositionalVector(x/n,y/n).asInstanceOf[this.type]
	override def unary_- :this.type = PositionalVector(-x,-y).asInstanceOf[this.type]
	
	override def withX(x:Float)=PositionalVector(x,y).asInstanceOf[this.type]
	override def withY(y:Float)=PositionalVector(x,y).asInstanceOf[this.type]
	override def withLength(len:Double):this.type=
		if(len==0)
			NullVector.asInstanceOf[this.type]
		else
			PositionalVector((len*math.sin(direction)).toFloat,(len*math.cos(direction)).toFloat).asInstanceOf[this.type]

	override def withDirection(angle:Double):this.type=
		PositionalVector((x*math.cos(angle)-y*math.sin(angle)).toFloat,(x*math.sin(angle)+y*math.cos(angle)).toFloat).asInstanceOf[this.type]
	
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
	
	override def +(v:Vector)= PolarVector(x+v.x,y+v.y).asInstanceOf[this.type]
	override def -(v:Vector)= PolarVector(x-v.x,y-v.y).asInstanceOf[this.type]
	override def *(n:Float) = PolarVector(direction,length*n).asInstanceOf[this.type]
	override def /(n:Float) = PolarVector(direction,length/n).asInstanceOf[this.type]
	override def unary_- :this.type = PolarVector(direction,-length).asInstanceOf[this.type]
	
	override def withLength(length:Double):this.type=PolarVector(direction,length).asInstanceOf[this.type]
	override def withDirection(direction:Double):this.type=PolarVector(direction,length).asInstanceOf[this.type]
	override def withX(x:Float)=PolarVector(math.atan2(x,y),math.hypot(x,y)).asInstanceOf[this.type]
	override def withY(y:Float)=PolarVector(math.atan2(x,y),math.hypot(x,y)).asInstanceOf[this.type]
}

object NullVector extends PositionalVector(0,0){
	override lazy val length=0.0
	override lazy val direction=0.0
}
