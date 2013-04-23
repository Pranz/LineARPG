package fridefors_peng.linearpg.objects

abstract class GameObject {
	private var destroyed = false
	(GameObject list) += this
	
	def destroy{
		if (!destroyed){
			(GameObject list) -= this
			destroyed = true
		}
	}
	
	def update:Unit	
}

object GameObject{
	val list: scala.collection.mutable.ArrayBuffer[GameObject] = scala.collection.mutable.ArrayBuffer()
}