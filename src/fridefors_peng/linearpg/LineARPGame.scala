package fridefors_peng.linearpg

import fridefors_peng.linearpg.terrain._
import fridefors_peng.linearpg.input._
import fridefors_peng.linearpg.objects.entities.Humanoid
import fridefors_peng.linearpg.objects._
import fridefors_peng.linearpg.objects.entities.actions.Action
import lolirofle.Game
import lolirofle.graphics2d.text.DefaultFont
import lolirofle.data.Vector
import lolirofle.data.Position
import org.lwjgl.input.Keyboard
import lolirofle.data.Horizontal
import lolirofle.data.Direction
import scala.collection.mutable.Buffer
import lolirofle.GameHandler
import fridefors_peng.linearpg.console.Console
import fridefors_peng.linearpg.objects.Updatable
import fridefors_peng.linearpg.timing.Alarm
import lolirofle.gl2d.GLDraw
import fridefors_peng.linearpg.camera._

class LineARPGame() extends Game with InputRelated{
	title="LineARPG"
	FPS=60

	private var shouldExit=false
	
	/**
	 * currentDelta is and should only be used for the debug display 
	 */
	private var currentDelta=0
	
	var obj:Humanoid = new Humanoid(Position(100, 250)){
		//override val mass=0.5f //TODO: Fix so gravitation will affect matter with different mass correctly
	}
	
	var control:Control=new GameControl(new EntityControl(obj,0))
	
	var camera:Camera=SimpleInteractableStalkerCamera(obj)
	
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
		control.handleInput(this)
		Updatable.list.clone foreach (_ update(delta))
	}

	override def onRender{
		camera.render(()=>Renderable.list.foreach(_.draw()),windowWidth,windowHeight)
		
		GUIRenderable.list.foreach(_.draw(windowWidth,windowHeight))
		
		Main.drawStrings(4,4,debugList)
	}
	override def onClose{}
	override def onWindowResize(width:Int,height:Int,fullscreen:Boolean){}
	
	val actionKeysDown=Buffer[ControlKey]()
	override def keyIsDown(key:ControlKey)=actionKeysDown.exists(_==key)
	override def keyIsDown(key:Int)=GameHandler.keyIsDown(key)
	
	override def onKeyEvent(key:Int,state:Boolean){
		val keyAction=Control.getMappedKey(key) match{
			case None=>null
			case Some(key)=>key
		}
		
		if(state){
			control.onKeyPressed(this,key)
			if(keyAction!=null){
				control.onKeyPressed(this,keyAction)
				actionKeysDown+=keyAction
			}
		}
		else{
			control.onKeyReleased(this,key)
			if(keyAction!=null){
				control.onKeyReleased(this,keyAction)
				actionKeysDown-=keyAction
			}
		}
	}
	
	override def onKeyCharEvent(chr:Char){
		control.onKeyChar(chr)
	}

	override def isCloseRequested=shouldExit

	override def close(){
		shouldExit=true
	}
	
	override def reset(){//TODO: All the global lists containing objects won't reset in a restart
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
			"yVelocity: " + obj.velocity.y  + " px/ms"/*,
			"Selected Action: "+ (
					obj.fAction(control.curAction)match {
						case Some(action) => action.name
						case _            => "No action"
					}
			)*/
		) 
	}
}