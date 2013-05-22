package fridefors_peng.linearpg.camera

import fridefors_peng.linearpg.objects.Interactable
import lolirofle.gl2d.GLDraw
import fridefors_peng.linearpg.Main

case class SimpleInteractableStalkerCamera(val target:Interactable) extends Camera{
	protected lazy val targetMidpoint=target.body.midpoint
	
	override def x(viewWidth:Float)=target.position.x+targetMidpoint.x-viewWidth*0.5f
	override def y(viewHeight:Float)=target.position.y+targetMidpoint.y-viewHeight*0.5f
	
	override def render(renderFunc:()=>Unit){
		GLDraw.offset_=(-x(Main.WIDTH),-y((Main.HEIGHT)))
			renderFunc()
		GLDraw.offset_=(x(Main.WIDTH),y(Main.HEIGHT))
	}
}