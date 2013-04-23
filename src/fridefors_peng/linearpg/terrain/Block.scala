package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.objects.Interactive
import fridefors_peng.linearpg.{Vector, Main}
import org.newdawn.slick.geom.Rectangle

case class Block(pos:Vector, len:Int, wid:Int)
		extends Terrain(pos,new Rectangle(pos.x, pos.y, len * Main.TILE_SIZE, wid * Main.TILE_SIZE)){

}