package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.Main
import lolirofle.data.Vector
import lolirofle.geom.Polygon
import lolirofle.data.Position

class TriangleBlock(pos:Position, len:Int, height:Int)
		extends Terrain(pos,Polygon(List(
			Position(pos.x, pos.y),
			Position(pos.x + len*Main.TILE_SIZE, pos.y),
			Position(pos.x + len*Main.TILE_SIZE, pos.y + height*Main.TILE_SIZE))
)){
	//bodyOffset = Vector(0,Main.TILE_SIZE * height)
}