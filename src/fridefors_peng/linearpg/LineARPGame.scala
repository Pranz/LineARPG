package fridefors_peng.linearpg

import fridefors_peng.linearpg.terrain._
import fridefors_peng.linearpg.input._
import fridefors_peng.linearpg.objects.entities.Humanoid
import fridefors_peng.linearpg.objects._
import fridefors_peng.linearpg.objects.entities.actions.Action
import lolirofle.gl2dlib.Game
import lolirofle.gl2dlib.graphics.text.DefaultFont

class LineARPGame() extends Game{
	var obj:Humanoid = new Humanoid(Vector(100, 250))
	var control:EntityControl = new EntityControl(obj,0)
	
	new Block(Vector(100, 500), 50, 2)
	new Block(Vector(600, 400), 1, 10)
	new Block(Vector(0, 600), 5, 1)
	new PhysicalBlock(Vector(360, 450), 6, 2){
		hspeed=0.1f;
		new Alarm(1000,()=>{hspeed = -hspeed},true)
	}
	
	override def onUpdate(delta:Int){
		GameObject.list.clone foreach (_ update(delta))
	}

	override def onRender{
		Renderable.list.foreach(_.draw())
		Main.drawList(4,4,("FPS: "+FPS)::debugList)
		
	}
	override def onClose{}
	override def onWindowResize(width:Int,height:Int,fullscreen:Boolean){}
	override def onKeyEvent(key:Int,state:Boolean){
		if(state)
			control.keyPressed(key)
		else
			control.keyReleased(key)
	}
	override def onKeyCharEvent(chr:Char){}

	override def isCloseRequested=false
		def debugList:List[String] = {
		def optionActionName(maybeA : Option[Action]) : String = maybeA match {
			case Some(action) => action.name
			case _            => "No action"
		}
		
		implicit def extractPhysicalOption(option:Option[Physical]):Physical = option match {
			case Some(obj) => obj
			case None      => DefaultPhysical
		}
		
		List(
			"x: "         + obj.position.x,
			"y: "         + obj.position.y,
			"deltaX: "        + obj.deltaPos.x,
			"deltaY: "        + obj.deltaPos.y,
			"hSpd: "	      + obj.hspeed,
			"vSpd: "       + obj.vspeed,
			"Velocity: "  + obj.velocity,
			"Relative x: "+ obj.prvRelativeObject.movement.x,
			"Relative y: "+ obj.prvRelativeObject.movement.y,
			"Selected Action: "+ optionActionName(obj.fAction(control.curAction))
		) 
	}
}