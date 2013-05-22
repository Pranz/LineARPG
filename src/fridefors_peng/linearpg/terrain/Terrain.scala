package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.objects.{Interactable, Renderable}
import lolirofle.data.Vector
import lolirofle.geom.Shape
import lolirofle.data.Position

/**
 * Any solid Interactive object.
 */
class Terrain(pos:Position,bd:Shape) extends Interactable(pos,bd) with Renderable{	
	Terrain.list += this
	def draw(){
		(body at position).draw
	}
	
	override def onDestroy {
		super.onDestroy
		Terrain.list -= this
	}
}

object Terrain {
	val list: collection.mutable.ArrayBuffer[Terrain] = collection.mutable.ArrayBuffer()
}