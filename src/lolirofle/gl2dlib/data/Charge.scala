package lolirofle.gl2dlib.data

trait Charge{
	def value:Byte
	def state:Boolean

	def +(n:Float)=n+value;
	def -(n:Float)=n-value;
	def *(n:Float)=n*value;
	def /(n:Float)=n/value;
	
	def unary_-():Charge
}

object Charge{
	object Positive extends Charge{override val value:Byte =  1;override val state=true; override val unary_- = Negative;}
	object Neutral  extends Charge{override val value:Byte =  0;override val state=false;override val unary_- = Neutral;}
	object Negative extends Charge{override val value:Byte = -1;override val state=false;override val unary_- = Positive;}
	
	val + = Positive;
	val - = Negative;
}