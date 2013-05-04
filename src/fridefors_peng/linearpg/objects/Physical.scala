package fridefors_peng.linearpg.objects

import lolirofle.gl2dlib.data.{Vector,NullVector}
import collection.mutable.ArrayBuffer
import lolirofle.gl2dlib.geom.Rectangle
import fridefors_peng.linearpg.objects.entities.Entity
import lolirofle.gl2dlib.geom.Shape
import lolirofle.gl2dlib.data.Position

/**
 * An object that is affected by physical forces, e.g. gravity, has velocity and acceleration.
 * Physical objects also affect other physical objects movements.
 */
trait Physical extends Mass{
	Physical.list += Physical.this
	
	def gravity:Float
	def friction:Float=
		if(onGround)
			0.0012f//TODO: Friction based on surface of different objects
		else
			0.00001f
	
	var movement:Vector=NullVector
	var acceleration:Vector=NullVector
	
	def hspeed = movement.x
	def hspeed_= (hsp:Float){movement = Vector(hsp,movement.y)}
		
	def vspeed = movement.y
	def vspeed_= (vsp:Float){movement = Vector(movement.x, vsp)}
	
	override def exertForce(force:Vector,delta:Int):Vector={
		if(force!=NullVector)
			position+=movementModifier(force,delta)//TODO: Can cause stack overflow because another object's movementModifier is able to call this, and this will call our movementModifier which may call the other objects exertForce again. This happens when the returned force is negative and moves back towards the other object
		NullVector
	}
	
	override def update(delta:Int) {
		super.update(delta)

		//TODO: This delta code makes it feel a bit weird, is it correctly calculated? Try 10 FPS and compare to 60 FPS
		movement += Vector(0,gravity) * delta
		movement += acceleration * delta
		
		val velWithFric=velocity-friction*delta
		movement=if(velWithFric>=0)movement.withLength(velWithFric) else NullVector
		
		position+=movementModifier(movement,delta)
	}
	
	def velocity = movement.length//TODO: movement should not relate to the velocity of the object because of delta affecting movement
	def velocity_= (vel:Double){
		if(vel==0)
			movement=NullVector
		else
			movement=movement.withLength(vel)
	}

	override def destroy{
		super.destroy
		Physical.list -= Physical.this
	}

	protected def xMovement(movement:Float,delta:Int):Vector={
		if(movement==0)
			NullVector
		
		val deltaMovement=movement*delta
		return placeMeeting(position+Vector(deltaMovement,0)) match{
			case obj:Some[_]=>{
				//Define function because we "need" recursion
				def moveCheck(movement:Vector):Vector=
					if(movement.x >= 1 || movement.x <= -1){//If not -1<=speed<=1
						//Whether this is colliding with anything
						val collided=placeMeeting(position+Vector(movement.x,0))
						return collided match{
							case obj:Some[_]=>moveCheck(movement-Vector(math.signum(movement.x),0))//Collided, check for collision repeatedly with less movement
							case _=>Vector(movement.x,0)//No collision anywhere, move the distance requested
						}
					}
					else{
						//Whether this is colliding with anything
						val collided=placeMeeting(position+Vector(math.signum(movement.x),0))
						return collided match{
							case obj:Some[_]=>NullVector
							case _=>Vector(movement.x,0)
						}
					}
				
				//Use function
				val postMove=moveCheck((Vector(movement,0)+obj.get.exertForce(Vector(movement,0),delta))*delta)
				this.movement=Vector(postMove.x/delta,this.movement.y+postMove.y/delta)//TODO: Will this mess up the movement when the delta timing changes rapidly?
				postMove
			}
			case _=>Vector(deltaMovement,0)
		}
	}
	
	//Copy-paste of xMovement with modifications
	protected def yMovement(movement:Float,delta:Int):Vector={
		if(movement==0)
			NullVector
		
		val deltaMovement=movement*delta
		return placeMeeting(position+Vector(0,deltaMovement)) match{
			case obj:Some[_]=>{
				//Define function because we "need" recursion
				def moveCheck(movement:Vector):Vector=
					//If not -1<=speed<=1
					if(movement.y >= 1 || movement.y <= -1){
						//Whether this is colliding with anything
						val collided=placeMeeting(position+Vector(0,movement.y))
						return collided match{
							//Collided, check for collision repeatedly with less movement
							case obj:Some[_]=>moveCheck(movement-Vector(0,math.signum(movement.y)))
							
							//No collision anywhere, move the distance requested
							case _=>movement
						}
					}
					else{
						//Whether this is colliding with anything
						val collided=placeMeeting(position+Vector(0,math.signum(movement.y)))
						return collided match{
							case obj:Some[_]=>NullVector
							case _=>movement
						}
					}

				val postMove=moveCheck((Vector(0,movement)+obj.get.exertForce(Vector(0,movement),delta))*delta)
				this.movement=Vector(this.movement.x+postMove.x/delta,postMove.y/delta)//TODO: Will this mess up the movement when the delta timing changes rapidly?
				postMove
			}
			case _=>Vector(0,deltaMovement)
		}
	}
	
	def movementModifier(dpos:Vector,delta:Int)=xMovement(dpos.x,delta)+yMovement(dpos.y,delta)
}

object Physical{
	val list:ArrayBuffer[Physical] = ArrayBuffer()
}
