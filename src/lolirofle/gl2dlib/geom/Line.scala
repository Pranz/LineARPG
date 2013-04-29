package lolirofle.gl2dlib.geom

import lolirofle.gl2dlib.data.Position

object Line{
	def apply(pointTo:Position):Line=Line(pointTo.x,pointTo.y);
}

case class Line(var x:Float,var y:Float) extends RegularPolygon{
	def length=math.hypot(x,y).toFloat
	def side=length
	def sides=2
	
	override def vertices=List(Position(0,0),Position(x,y));

	override def width=math.abs(x);
	override def height=math.abs(y);
	
	/*def intersects(line:Line):Boolean={
		val xDif=x2-x1;
		val yDif=y2-y1;
		val xDifOther=line.x2-line.x1;
		val yDifOther=line.y2-line.y1;
		val denom=(yDifOther*xDif)-(xDifOther*yDif);

		if(denom==0){
			return false;
		}

		val u=((xDif*(y1-line.y1))-(yDif*(x1-line.x1)))/denom;
		val uOther=((xDifOther*(y1-line.y1))-(yDifOther*(x1-line.x1)))/denom;

		return !(u<0||u>1||uOther<0||uOther>1);
	}*/
}