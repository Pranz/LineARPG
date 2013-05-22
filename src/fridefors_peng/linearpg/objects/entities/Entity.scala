package fridefors_peng.linearpg.objects.entities

import fridefors_peng.linearpg.objects.{Matter, Interactable, Renderable}
import fridefors_peng.linearpg.objects.entities.actions.Action
import lolirofle.gl2dlib.data.Vector
import lolirofle.gl2dlib.geom.Shape
import collection.mutable.ArrayBuffer
import lolirofle.gl2dlib.data.Horizontal
import lolirofle.gl2dlib.data.Direction
import lolirofle.gl2dlib.data.Position
import lolirofle.gl2dlib.data.NullVector

/**
 * The Entity class. This is any unit in the game world, and can be player or AI controlled.
 * 
 */
abstract class Entity(pos:Position,body:Shape) extends Interactable(pos,body) with Matter with Renderable{
	Entity.list += this
	
	private var isRunning=false
	
	def airSpeedFactor:Float
	def jumpForce:Vector
	
	val fAction = new Array[Option[Action]](10) map {_ => None : Option[Action]}
	
	var facingDir:Horizontal = Direction.Right
	var movingDir:Horizontal = Direction.Right
	
	private var runningForce=Vector(0.0015f,0)
	
	var maxhspd   = 0.2f
	def maxhSpeed:Float=
		if(onGround)
			maxhspd
		else
			maxhspd * airSpeedFactor*2
	
	override def draw(){
		(body at position).draw
	}
	
	def turn(dir:Horizontal){
		facingDir=dir
	}
	
	def run(dir:Horizontal=facingDir){
		isRunning=true
		movingDir=dir
	}
	
	def stand(){
		isRunning=false
		movingDir = Direction.Center
	}
	
	def jump(){
		if(onGround)//TODO: Movement modifier method with custom elasticity
			_velocity-=jumpForce//TODO: Should use forces and not change velocity directly
	}
	
	override def update(delta:Int){
		if(isRunning){
			val forceNew=force+(if(onGround)runningForce else runningForce*airSpeedFactor)*movingDir.toByte
			
			if(Math.abs(velocity.x)<maxhSpeed)
				force=forceNew
		}
		super.update(delta)
	}
}

object Entity{
	val list:ArrayBuffer[Entity] = ArrayBuffer()
}