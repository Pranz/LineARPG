package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.objects.{Interactive, Renderable}
import fridefors_peng.linearpg.Vector
import lolirofle.gl2dlib.geom.Shape

/**
 * Any solid Interactive object.
 */
class Terrain(pos:Vector,bd:Shape) extends Interactive(pos,bd) with Renderable{	
	Terrain.list += this
	solid = true
	def draw(){
		body.at(position).draw
	}
	
	override def destroy {
		super.destroy
		Terrain.list -= this
	}
}

object Terrain {
	val list: collection.mutable.ArrayBuffer[Terrain] = collection.mutable.ArrayBuffer()
}