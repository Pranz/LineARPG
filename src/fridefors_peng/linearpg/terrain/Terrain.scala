package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.objects.{Interactive, Renderable}
import fridefors_peng.linearpg.Vector
import org.newdawn.slick.geom.Shape
import org.newdawn.slick.Graphics
/**
 * Any solid Interactive object.
 */
abstract class Terrain(pos:Vector, bd:Shape) extends Interactive(pos, bd) with Renderable{
	
	(Terrain list) += this
	solid = true
	def draw(g:Graphics) {
		g.draw(body)
	}
	
	override def destroy {
		super.destroy
		(Terrain list) -= this
	}
}

object Terrain {
	val list: collection.mutable.ArrayBuffer[Terrain] = collection.mutable.ArrayBuffer()
}