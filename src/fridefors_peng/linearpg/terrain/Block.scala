package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.objects.Interactable
import lolirofle.gl2dlib.data.Vector
import lolirofle.gl2dlib.geom.Rectangle
import fridefors_peng.linearpg.Main
import lolirofle.gl2dlib.geom.PositionedShape
import lolirofle.gl2dlib.data.Position
import fridefors_peng.linearpg.objects.Solid

/**
 * Any terrain that fits into the grid. The grid is defined by Main.TILE_SIZE.
 */
case class Block(pos:Position, len:Int, wid:Int)
		extends Terrain(pos,new Rectangle(len * Main.TILE_SIZE, wid * Main.TILE_SIZE)) with Solid{

}