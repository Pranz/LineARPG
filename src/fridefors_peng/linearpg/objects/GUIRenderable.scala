package fridefors_peng.linearpg.objects

import collection.mutable.ArrayBuffer

/**
 * Simple trait. Any object that implements this must define a draw function
 */
trait GUIRenderable extends GameObject {
	GUIRenderable.list += this

	def draw():Unit

	override def onDestroy{
		super.onDestroy
		GUIRenderable.list -= this
	}
}

object GUIRenderable{
	val list:ArrayBuffer[GUIRenderable]=ArrayBuffer()
}