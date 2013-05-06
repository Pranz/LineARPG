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
trait Mass extends Matter{
	Mass.list += Mass.this
	
	def mass:Float=1
	
	/**
	 * Elasticity
	 * 
	 * Indicates how elastic the object is, that is what would happen when colliding
	 * http://en.wikipedia.org/wiki/Inelastic_collision#Formula
	 * http://en.wikipedia.org/wiki/Elastic_collision#Equations
	 * 
	 * Range: 0 -> 1
	 */
	def elasticity:Float=0
	
	//TODO: Directional gravity
	//TODO: Global gravity based on height of object, or create a GravitationalObject (ForceField?) that affects other objects forces based on distance 
	def gravity:Float
	
	def friction:Float=
		if(onGround)
			0.2f//TODO: Friction based on surface of different objects
		else
			0.001f
	
	def weight=mass*gravity
			
	protected var movement:Vector=NullVector
	protected var acceleration:Vector=NullVector
	
	def xMovement = movement.x
	protected def xMovement_= (hsp:Float){movement = Vector(hsp,movement.y)}
		
	def yMovement = movement.y
	protected def yMovement_= (vsp:Float){movement = Vector(movement.x, vsp)}
	
	override def exertForce(force:Vector,otherMass:Float):Vector={
		if(force==NullVector)
			return NullVector

		val tmp=movement
		movement=((force-movement)*elasticity*otherMass+force+movement)/(mass+otherMass)
		return tmp
	}
	
	override def update(delta:Int){
		super.update(delta)

		//Apply gravity
		if(gravity!=0)
			movement += Vector(0,gravity)
			
		//Apply acceleration
		if(acceleration!=NullVector)
			movement += acceleration

		//Apply friction
		if(friction!=0){
			val velWithFric=velocity-friction
			movement=if(velWithFric>=0)movement.withLength(velWithFric) else NullVector
		}
			
		position+=movementModifier(movement)
	}
	
	def velocity = movement.length//TODO: movement should not relate to the velocity of the object because of delta affecting movement
	protected def velocity_= (vel:Double){
		if(vel==0)
			movement=NullVector
		else
			movement=movement.withLength(vel)
	}

	override def destroy{
		super.destroy
		Mass.list -= Mass.this
	}

	private def moveContactXCheck(movement:Vector):Vector=
		if(movement.x >= 1 || movement.x <= -1){//If not -1<=speed<=1
			//Whether this is colliding with anything
			val collided=placeMeeting(position+Vector(movement.x,0))
			return collided match{
				case obj:Some[_]=>moveContactXCheck(movement-Vector(math.signum(movement.x),0))//Collided, check for collision repeatedly with less movement
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
	
	protected def xMovement(movement:Float):Vector={
		if(movement==0)
			NullVector
		
		return placeMeeting(position+Vector(movement,0)) match{
			case Some(other)=>{
				val otherMovement=other.exertForce(Vector(movement,0),mass)
				xMovement=(((otherMovement-this.movement)*elasticity+otherMovement+this.movement)/2).x
				moveContactXCheck(Vector(movement,0))
			}
			case _=>Vector(movement,0)
		}
	}
	
	private def moveContactYCheck(movement:Vector):Vector=
		//If not -1<=speed<=1
		if(movement.y >= 1 || movement.y <= -1){
			//Whether this is colliding with anything
			val collided=placeMeeting(position+Vector(0,movement.y))
			return collided match{
				//Collided, check for collision repeatedly with less movement
				case obj:Some[_]=>moveContactYCheck(movement-Vector(0,math.signum(movement.y)))
				
				//No collision anywhere, move the distance requested
				case _=>Vector(0,movement.y)
			}
		}
		else{
			//Whether this is colliding with anything
			val collided=placeMeeting(position+Vector(0,math.signum(movement.y)))
			return collided match{
				case obj:Some[_]=>NullVector
				case _=>Vector(0,movement.y)
			}
		}
	
	//Copy-paste of xMovement with modifications
	protected def yMovement(movement:Float):Vector={
		if(movement==0)
			NullVector
		
		return placeMeeting(position+Vector(0,movement)) match{
			case Some(other)=>{
				val otherMovement=other.exertForce(Vector(0,movement),mass)
				yMovement=(((otherMovement-this.movement)*elasticity+otherMovement+this.movement)/2).y
				moveContactYCheck(Vector(0,movement))
			}
			case _=>Vector(0,movement)
		}
	}
	
	def movementModifier(dpos:Vector)=xMovement(dpos.x)+yMovement(dpos.y)
}

object Mass{
	val list:ArrayBuffer[Mass] = ArrayBuffer()
}
