package fridefors_peng.linearpg.objects

import collection.mutable.ArrayBuffer

/**
 * Simple trait. Any object that implements this must define a draw function
 */
trait Renderable extends GameObject {
	Renderable.list += this

	def draw():Unit

	override def onDestroy{
		super.onDestroy
		Renderable.list -= this
	}
}

object Renderable {
	val list:ArrayBuffer[Renderable]=ArrayBuffer()
}