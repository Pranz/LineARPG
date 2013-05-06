package fridefors_peng.linearpg

import fridefors_peng.linearpg.terrain._
import fridefors_peng.linearpg.input._
import fridefors_peng.linearpg.objects.entities.Humanoid
import fridefors_peng.linearpg.objects._
import fridefors_peng.linearpg.objects.entities.actions.Action
import lolirofle.gl2dlib.Game
import lolirofle.gl2dlib.graphics.text.DefaultFont
import lolirofle.gl2dlib.data.Vector
import lolirofle.gl2dlib.data.Position
import org.lwjgl.input.Keyboard

class LineARPGame() extends Game{
	var shouldExit=false
	
	var obj:Humanoid = new Humanoid(Position(100, 250))
	var control:EntityControl = new EntityControl(obj,0)
	
	new Block(Position(100, 500), 50, 2)
	new Block(Position(600, 400), 1, 6)
	new Block(Position(200, 450), 1, 2)
	new Block(Position(0, 600), 5, 1)
	new PhysicalBlock(Position(360, 450), 6, 2){
		xMovement=1f;		
		new Alarm(60,()=>{xMovement = -xMovement},true)
	}
	
	new Humanoid(Position(260, 40))
	
	override def onUpdate(delta:Int){
		GameObject.list.clone foreach (_ update(1))
	}

	override def onRender{
		Renderable.list.foreach(_.draw())
		Main.drawList(4,4,("FPS: "+FPS)::debugList)
	}
	override def onClose{}
	override def onWindowResize(width:Int,height:Int,fullscreen:Boolean){}
	override def onKeyEvent(key:Int,state:Boolean){
		if(state){
			if(key==Keyboard.KEY_ESCAPE)
				close()
			else if(key==Keyboard.KEY_R)
				reset();
			else
				control.keyPressed(key)
		}
		else
			control.keyReleased(key)
	}
	override def onKeyCharEvent(chr:Char){}

	override def isCloseRequested=shouldExit

	def close(){
		shouldExit=true
	}
	
	def reset(){//TODO: All the global lists containing objects won't reset 
		Main.restart=true;
		close();
	}
	
	def debugList:List[String] = {
		List(
			"x: "         + obj.position.x,
			"y: "         + obj.position.y,
			"deltaX: "        + obj.deltaPos.x,
			"deltaY: "        + obj.deltaPos.y,
			"xMovement: "	      + obj.xMovement,
			"yMovement: "       + obj.yMovement,
			"Velocity: "  + obj.velocity,
			"Selected Action: "+ (
					obj.fAction(control.curAction)match {
						case Some(action) => action.name
						case _            => "No action"
					}
			)
		) 
	}
}