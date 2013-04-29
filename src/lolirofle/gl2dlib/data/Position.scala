package lolirofle.gl2dlib.data

object Position{
	def apply(x:Float,y:Float)=Pos(x,y)
}

trait Position{
	def x:Float;
	def y:Float;
}

case class Pos(var x:Float,var y:Float) extends Position{}