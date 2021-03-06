package fridefors_peng.linearpg.objects

import scala.collection.mutable.ArrayBuffer

trait Solid extends Interactable{
	Solid.list+=Solid.this
	
	override def onDestroy{
		super.onDestroy
		Solid.list -= Solid.this
	}
}

object Solid{
	val list:ArrayBuffer[Solid]=ArrayBuffer()
}