package fridefors_peng.linearpg.objects
import scala.collection.mutable.ArrayBuffer

/**
 * Any object that is a function of time is a gameObject.
 * updates every frame.
 */
abstract class GameObject {
	private var destroyed = false
	(GameObject list) += this
	
	def destroy{
		if (!destroyed){
			(GameObject list) -= this
			destroyed = true
		}
	}
	
	def update(delta_t:Int):Unit	
}

object GameObject{
	val list: ArrayBuffer[GameObject] = ArrayBuffer()
}