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
import lolirofle.gl2dlib.data.Horizontal
import lolirofle.gl2dlib.data.Direction

class LineARPGame() extends Game{
	title="LineARPG"
	FPS=60
	
	private var shouldExit=false
	
	/**
	 * It will and should only be used for the debug display 
	 */
	private var currentDelta=0
	
	var obj:Humanoid = new Humanoid(Position(100, 250)){
		override val mass=0.5f
	}
	var control:EntityControl = new EntityControl(obj,0)
	
	new Block(Position(100, 500), 50, 2)
	new Block(Position(600, 400), 1, 6)
	new Block(Position(200, 450), 1, 2)
	new Block(Position(0, 600), 5, 1)
	new PhysicalBlock(Position(360, 450), 6, 2){//TODO: ImmovableMass that extends Mass
		var dir:Horizontal=Direction.Left
		new Alarm(1000,()=>{
			if(dir==Direction.Left)dir=Direction.Right else	dir=Direction.Left
		},true)
		
		override def acceleration=Vector(0.001f*dir.toByte,0)
		
		override def accelerationModifier(acceleration:Vector,delta:Int)={
			val newVel=velocity.x+acceleration.x*delta
			if(newVel<0.1f && newVel> -0.1f)
				acceleration*delta
			else
				Vector(0.1f*dir.toByte-newVel,acceleration.y*delta)
		}
	}
	
	new Humanoid(Position(260, 40))
	new Humanoid(Position(267, -100))
	
	override def onUpdate(delta:Int){
		currentDelta=delta
		GameObject.list.clone foreach (_ update(delta))
	}

	override def onRender{
		Renderable.list.foreach(_.draw())
		Main.drawStrings(4,4,debugList)
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
			"FPS: "       + FPS,
			"Delta: "     + currentDelta   + " ms/update",
			"x: "         + obj.position.x,
			"y: "         + obj.position.y,
			"deltaX: "    + obj.deltaPosition.x + " px/update",
			"deltaY: "    + obj.deltaPosition.y + " px/update",
			"xVelocity: " + obj.velocity.x  + " px/ms",
			"yVelocity: " + obj.velocity.y  + " px/ms",
			"Selected Action: "+ (
					obj.fAction(control.curAction)match {
						case Some(action) => action.name
						case _            => "No action"
					}
			)
		) 
	}
}