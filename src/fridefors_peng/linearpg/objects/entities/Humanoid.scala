package fridefors_peng.linearpg.objects.entities

import lolirofle.gl2dlib.geom.Rectangle
import lolirofle.gl2dlib.data.Vector
import fridefors_peng.linearpg.objects.entities.actions.{
	TestAction, TestChargedAction, SwordAction
}
import lolirofle.gl2dlib.gl.GLDraw
import lolirofle.gl2dlib.data.Position

class Humanoid(pos:Position) extends Entity(pos,Rectangle(24,56)){	
	override val jumpForce = Vector(0,0.38f)
	override val airSpeedFactor = 0.1f
	
	fAction(0) = Some(new TestAction (this))
	fAction(1) = Some(new TestChargedAction(this))
	fAction(2) = Some(new SwordAction(this))
}