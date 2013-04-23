package fridefors_peng.linearpg.objects.entities

import org.newdawn.slick.geom.Rectangle
import fridefors_peng.linearpg.Vector
import fridefors_peng.linearpg.objects.entities.actions.{
	TestAction, TestChargedAction, SwordAction
}

class Humanoid(pos:Vector) extends Entity(pos, new Rectangle(0,0,24,56)) {
	val hp = 100 : Double
	var jumpPower = 7f
	fAction(0) = Some(new TestAction (this))
	fAction(1) = Some(new TestChargedAction(this))
	fAction(2) = Some(new SwordAction(this))
}