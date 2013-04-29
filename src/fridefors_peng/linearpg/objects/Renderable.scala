package fridefors_peng.linearpg.objects

/**
 * Simple trait. Any object that implements this must define a draw function, which takes a single slick.Graphics argument.
 */

trait Renderable extends GameObject {
	Renderable.list += this

	def draw():Unit

	override def destroy{
		Renderable.list -= this
		super.destroy
	}
}

object Renderable {
	val list: collection.mutable.ArrayBuffer[Renderable] = collection.mutable.ArrayBuffer()
}