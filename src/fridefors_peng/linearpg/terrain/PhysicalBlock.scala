package fridefors_peng.linearpg.terrain

import lolirofle.gl2dlib.data.Vector
import fridefors_peng.linearpg.objects.Matter
import lolirofle.gl2dlib.data.Position

/**
 * Any block who has physical properties, like velocity.
 */
class PhysicalBlock(pos:Position, len:Int, wid:Int) 
		extends Block(pos:Position, len:Int, wid:Int) with Matter{
	override val gravity=0f
	override def elasticity:Float=0
	override val friction=0f
	override def exertForce(force:Vector,mass:Float):Vector = velocity-force
}