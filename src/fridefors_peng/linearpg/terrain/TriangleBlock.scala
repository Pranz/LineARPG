package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.Main
import lolirofle.gl2dlib.data.Vector
import lolirofle.gl2dlib.geom.Polygon
import lolirofle.gl2dlib.data.Position

class TriangleBlock(pos:Position, len:Int, height:Int)
		extends Terrain(pos,Polygon(List(
			Position(pos.x, pos.y),
			Position(pos.x + len*Main.TILE_SIZE, pos.y),
			Position(pos.x + len*Main.TILE_SIZE, pos.y + height*Main.TILE_SIZE))
)){
	//bodyOffset = Vector(0,Main.TILE_SIZE * height)
}