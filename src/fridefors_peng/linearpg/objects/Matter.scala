package fridefors_peng.linearpg.objects

import lolirofle.gl2dlib.data.{Vector,NullVector}
import collection.mutable.ArrayBuffer
import lolirofle.gl2dlib.geom.Rectangle
import fridefors_peng.linearpg.objects.entities.Entity
import lolirofle.gl2dlib.geom.Shape
import lolirofle.gl2dlib.data.Position

/**
 * An object that is affected by physical forces, e.g. gravity, velocity, acceleration and affects other matters (TODO: Grammar check)
 */
trait Matter extends Interactable{
	Matter.list += Matter.this
	
	/**
	 * Mass
	 * 
	 * Should not be zero
	 * 
	 * Range: >0
	 */
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
	
	/**
	 * Density
	 */
	def density=mass/volume
	
	//TODO: Directional gravity
	//TODO: Global gravity based on height of object, or create a GravitationalObject (ForceField?) that affects other objects forces based on distance with formula "(mass*otherMass)/distanceToMidpointOfBodies"
	//TODO: Air resistance based on shape of body
	def gravity:Float=0.00075f
	
	//TODO: Friction based on surface of different objects. Currently this is the amount of friction that affects this object
	def friction:Float=
		if(onGround)
			0.01f
		else
			0.001f
	
	/**
	 * Weight
	 */
	def weight=mass*gravity

	/**
	 * Velocity (px/ms)
	 * 
	 * Modifies position and it describes speed with a direction
	 */
	def velocity=_velocity
	protected var _velocity:Vector=NullVector
	
	/**
	 * Acceleration (px/ms^2)
	 * 
	 * Modifies velocity
	 */
	def acceleration:Vector=force/mass
	protected def acceleration_= (a:Vector){force=a*mass}
	
	/**
	 * Force
	 * 
	 * The amount of force per update 
	 */
	var force:Vector=NullVector//TODO: Need a forcePerUpdate and a forcePerMs
	
	def processTime(time:Int){
		//Apply acceleration to velocity
		_velocity+=accelerationModifier(acceleration,time)
		
		force=NullVector
		
		//Apply friction to velocity
		if(friction!=0){
			val velWithFric=velocity.length-friction
			if(velWithFric>=0)
				_velocity=velocity.withLength(velWithFric)
		}
		
		//Updates position according to velocity
		position+=velocityModifier(velocity,time)
	}
	
	override def exertForce(force:Vector,otherMass:Float):Vector={
		if(force==NullVector)
			return NullVector

		val tmp=velocity
		_velocity=((force-velocity)*elasticity*otherMass+force+velocity)/(mass+otherMass)
		return tmp
	}
	
	override def update(delta:Int){
		super.update(delta)
		
		force+=Vector(0,gravity)
		
		processTime(delta)
	}
	
	/**
	 * Speed
	 * 
	 * The speed of the velocity
	 */
	def speed=velocity.length
	protected def speed_= (spd:Double){_velocity=_velocity.withLength(spd)}
	
	/**
	 * Direction
	 * 
	 * The direction of the velocity
	 */
	def direction=velocity.direction
	protected def direction_= (dir:Double){_velocity=_velocity.withDirection(dir)}
	
	override def onDestroy{
		super.onDestroy
		Matter.list -= Matter.this
	}

	//TODO: Real two-dimensional collision checking and force system. It should also fix slopes and collisions with non-rectangular objects
	
	private def collisionCheckX(movement:Float,otherMovement:Vector):Vector=
		if(movement >= 1 || movement <= -1){//If not -1<=speed<=1
			//Whether this is colliding with anything
			val collided=placeMeeting(position+Vector(movement,0))
			return collided match{
				case obj:Some[_]=>collisionCheckX(movement-math.signum(movement),otherMovement)//Collided, check for collision repeatedly with less movement
				case _=>{//No collision anywhere, move the distance requested
					//xMovement=(((otherMovement-this.movement)*elasticity+otherMovement+this.movement)/2).x
					Vector(movement,0)
				}
			}
		}
		else{
			//Whether this is colliding with anything
			val collided=placeMeeting(position+Vector(math.signum(movement),0))
			return collided match{
				case obj:Some[_]=>NullVector
				case _=>{
					//xMovement=(((otherMovement-this.movement)*elasticity+otherMovement+this.movement)/2).x
					Vector(movement,0)
				}
			}
		}
	
	protected def xVelocity(movement:Float, time:Int):Vector={
		if(movement==0)
			NullVector
					
		val deltaMovement=movement*time
		
		return placeMeeting(position+Vector(deltaMovement,0)) match{
			case Some(other)=>{
				val otherMovement=other.exertForce(Vector(movement,0),mass)
				_velocity=Vector((((otherMovement-Matter.this.velocity)*elasticity+otherMovement+Matter.this.velocity)/2).x, Matter.this.velocity.y)
				
				return collisionCheckX(deltaMovement,otherMovement)
			}
			case _=>Vector(deltaMovement,0)
		}
	}
	
	private def collisionCheckY(movement:Float):Vector=
		//If not -1<=speed<=1
		if(movement >= 1 || movement <= -1){
			//Whether this is colliding with anything
			val collided=placeMeeting(position+Vector(0,movement))
			return collided match{
				//Collided, check for collision repeatedly with less movement
				case obj:Some[_]=>collisionCheckY(movement-math.signum(movement))
				
				//No collision anywhere, move the distance requested
				case _=>Vector(0,movement)
			}
		}
		else{
			//Whether this is colliding with anything
			val collided=placeMeeting(position+Vector(0,math.signum(movement)))
			return collided match{
				case obj:Some[_]=>NullVector
				case _=>Vector(0,movement)
			}
		}
	
	//Copy-paste of xMovement with modifications
	protected def yVelocity(movement:Float, time:Int):Vector={
		if(movement==0)
			NullVector
		
		val deltaMovement=movement*time
		
		return placeMeeting(position+Vector(0,deltaMovement)) match{
			case Some(other)=>{//TODO: This should affect horizontal movement too for e.g. moving platforms
				val otherMovement=other.exertForce(Vector(0,movement),mass)
				_velocity=Vector(Matter.this.velocity.x, ((((otherMovement-Matter.this.velocity)*elasticity+otherMovement+Matter.this.velocity)/2).y))
				return collisionCheckY(deltaMovement)
			}
			case _=>Vector(0,deltaMovement)
		}
	}
	
	def velocityModifier(dpos:Vector,time:Int)=xVelocity(dpos.x,time)+yVelocity(dpos.y,time)
	def accelerationModifier(dmove:Vector,time:Int)=dmove*time
}

object Matter{
	val list:ArrayBuffer[Matter] = ArrayBuffer()
}
