package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.Vector

/*
 * A block who is controlled by a BlockController object.
 */

abstract class ControllableBlock(pos:Vector, len:Int, wid:Int, val blockID:Int) extends PhysicalBlock(pos, len, wid) {
	ControllableBlock.list += this
	
	val property:Array[ControllableBlock.Property]
	val switch:Array[Boolean]
	def apply(prop:Int) = property(prop)
	
	def toggle(sw:Int):Unit = switch(sw) = !switch(sw)
}

object ControllableBlock {
	val list = collection.mutable.ArrayBuffer[ControllableBlock]()
	def getBlocksWithID(id: Int) = list.filter(_.blockID == id)
	
	case class Property(max:Int){
		var curVal:Float = 0
		
		def +(newVal:Float):Unit = {
			if(curVal + newVal > max) curVal = max
			else if(curVal + newVal < 0) curVal = 0
			else curVal += newVal
		}
		def -(newVal:Float):Unit = {
			if(curVal - newVal < 0) curVal = 0
			else if(curVal - newVal > max) curVal = max
			else curVal -= newVal
		}
		def get:Float = curVal
	}
}