package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.objects.{Interactive, Renderable}
import fridefors_peng.linearpg.Vector
import org.newdawn.slick.geom.Shape
import org.newdawn.slick.Graphics

abstract class Terrain(pos:Vector, bd:Shape) extends Interactive(pos, bd) with Renderable{
	solid = true
	def draw(g:Graphics) {
		g.draw(body)
	}
}