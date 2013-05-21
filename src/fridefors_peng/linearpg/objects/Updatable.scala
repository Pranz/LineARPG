package fridefors_peng.linearpg.objects

import scala.collection.mutable.ArrayBuffer

/**
 * Any object that is a function of time is a updatable.
 * Updates every frame.
 */
trait Updatable extends GameObject{
	Updatable.list += this
	
	override def onDestroy{
		super.onDestroy
		Updatable.list -= this
	}
	
	/**
	 * @param delta Time since last update in milliseconds (ms)
	 */
	def update(delta:Int):Unit
}

object Updatable{
	//TODO: State/World specific lists
	val list: ArrayBuffer[Updatable] = ArrayBuffer()
}