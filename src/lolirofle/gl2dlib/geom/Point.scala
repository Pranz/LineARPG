package lolirofle.gl2dlib.geom

import lolirofle.gl2dlib.data.Position

case object Point extends RegularPolygon{
	override val sides=1
	override val side=1f
	override val width=1f
	override val height=1f
	override val vertices=List(Position(0,0));
}