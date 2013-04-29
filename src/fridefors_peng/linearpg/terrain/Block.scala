package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.objects.Interactive
import fridefors_peng.linearpg.{Vector}
import lolirofle.gl2dlib.geom.Rectangle
import fridefors_peng.linearpg.Main
import lolirofle.gl2dlib.geom.PositionedShape

/**
 * Any terrain that fits into the grid. The grid is defined by Main.TILE_SIZE.
 */
case class Block(pos:Vector, len:Int, wid:Int)
		extends Terrain(pos,new Rectangle(len * Main.TILE_SIZE, wid * Main.TILE_SIZE)){

}