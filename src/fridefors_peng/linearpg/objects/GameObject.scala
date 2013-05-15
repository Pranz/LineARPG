package fridefors_peng.linearpg.objects
import scala.collection.mutable.ArrayBuffer

/**
 * Any object that is a function of time is a gameObject.
 * Updates every frame.
 */
trait GameObject {
	private var destroyed = false
	GameObject.list += this
	
	def destroy{
		if (!destroyed){
			GameObject.list -= this
			destroyed = true
		}
	}
	
	/**
	 * @param delta Time since last update in milliseconds (ms)
	 */
	def update(delta:Int):Unit	
}

object GameObject{
	//TODO: State/World specific lists
	val list: ArrayBuffer[GameObject] = ArrayBuffer()
}