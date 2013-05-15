package lolirofle.gl2dlib.geom

case class Square(side:Float) extends Tetragon with RegularPolygon{
	override def width=side
	override def height=side
	
	override def sides=4;
	override def area=super.area;
	override def perimeter=super.perimeter;
}