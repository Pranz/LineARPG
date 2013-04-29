package fridefors_peng.linearpg.objects.entities

import lolirofle.gl2dlib.geom.Rectangle
import fridefors_peng.linearpg.Vector
import fridefors_peng.linearpg.objects.entities.actions.{
	TestAction, TestChargedAction, SwordAction
}
import lolirofle.gl2dlib.gl.GLDraw

class Humanoid(pos:Vector) extends Entity(pos,Rectangle(24,56)){	
	override val jumpPower = 0.4f
	override val airSpeedFactor = 0.125f
	override val airFricFactor = 0f
	
	fAction(0) = Some(new TestAction (this))
	fAction(1) = Some(new TestChargedAction(this))
	fAction(2) = Some(new SwordAction(this))
}