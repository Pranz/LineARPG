package fridefors_peng.linearpg.terrain

import lolirofle.gl2dlib.data.Vector
import fridefors_peng.linearpg.objects.Physical
import lolirofle.gl2dlib.data.Position

/**
 * Any block who has physical properties, like velocity.
 */
class PhysicalBlock(pos:Position, len:Int, wid:Int) 
		extends Block(pos:Position, len:Int, wid:Int) with Physical{
	var gravity =0f
	override val friction=0f
	override def exertForce(force:Vector,delta:Int):Vector=movement+force
}