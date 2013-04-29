package lolirofle.gl2dlib.geom

class Ellipse(var radiusX:Float,var radiusY:Float) extends Shape{
	override def width=radiusX*2;
	override def height=radiusY*2;
	override def perimeter=((radiusX+radiusY)*math.Pi).toFloat
	override def area=(radiusX*radiusY*math.Pi).toFloat
	
	override def draw(x:Float,y:Float,filled:Boolean){//TODO: Drawing a ellipse
		
	}
}