package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.objects.{Mass, Renderable}
import lolirofle.gl2dlib.data.Vector
import lolirofle.gl2dlib.geom.Shape
import lolirofle.gl2dlib.data.Position

/**
 * Any solid Interactive object.
 */
class Terrain(pos:Position,bd:Shape) extends Mass(pos,bd) with Renderable{	
	Terrain.list += this
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