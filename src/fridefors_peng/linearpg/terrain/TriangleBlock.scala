package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.{Vector,Main}
import lolirofle.gl2dlib.geom.Polygon

class TriangleBlock(pos:Vector, len:Int, height:Int)
		extends Terrain(pos,Polygon(List(
			Vector(pos.x, pos.y),
			Vector(pos.x + len*Main.TILE_SIZE, pos.y),
			Vector(pos.x + len*Main.TILE_SIZE, pos.y + height*Main.TILE_SIZE))
)){
	//bodyOffset = Vector(0,Main.TILE_SIZE * height)
}