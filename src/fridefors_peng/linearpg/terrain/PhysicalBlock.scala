package fridefors_peng.linearpg.terrain

import fridefors_peng.linearpg.Vector
import fridefors_peng.linearpg.objects.Physical

/**
 * Any block who has physical properties, like velocity.
 */
class PhysicalBlock(pos:Vector, len:Int, wid:Int) 
		extends Block(pos:Vector, len:Int, wid:Int) with Physical {
	var gravity = 0.0f

	var accerelation = V(0,0)
	var movement     = V(0,0)
}