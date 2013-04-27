package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.{Vector, Main}
import org.newdawn.slick.geom.Polygon

class TriangleBlock(pos:Vector, len:Int, height:Int)
		extends Terrain(pos,new Polygon(Array(
			pos.x, pos.y,
			pos.x + len*Main.TILE_SIZE, pos.y,
			pos.x + len*Main.TILE_SIZE, pos.y + height*Main.TILE_SIZE)
)){
	//bodyOffset = Vector(0,Main.TILE_SIZE * height)
}