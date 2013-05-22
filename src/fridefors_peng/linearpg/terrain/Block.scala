package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.objects.Interactable
import lolirofle.data.Vector
import lolirofle.geom.Rectangle
import fridefors_peng.linearpg.Main
import lolirofle.geom.PositionedShape
import lolirofle.data.Position
import fridefors_peng.linearpg.objects.Solid

/**
 * Any terrain that fits into the grid. The grid is defined by Main.TILE_SIZE.
 */
case class Block(pos:Position, len:Int, wid:Int)
		extends Terrain(pos,new Rectangle(len * Main.TILE_SIZE, wid * Main.TILE_SIZE)) with Solid{

}