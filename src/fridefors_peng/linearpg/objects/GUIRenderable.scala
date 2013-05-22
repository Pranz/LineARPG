package fridefors_peng.linearpg.objects

import collection.mutable.ArrayBuffer

/**
 * GUIRenderable
 * 
 * All objects that renders GUI should extend GUIRenderable
 * GUIRenderable renders above of all Renderables and its rendering position is always relative to the window  
 */
trait GUIRenderable extends GameObject{
	GUIRenderable.list += this

	def draw(windowWidth:Float,windowHeight:Float):Unit

	override def onDestroy{
		super.onDestroy
		GUIRenderable.list -= this
	}
}

object GUIRenderable{
	val list:ArrayBuffer[GUIRenderable]=ArrayBuffer()
}