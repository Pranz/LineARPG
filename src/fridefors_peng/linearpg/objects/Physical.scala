package fridefors_peng.linearpg.objects

import fridefors_peng.linearpg.{Vector, NullVector}
import collection.mutable.ArrayBuffer
import org.newdawn.slick.geom.Rectangle
import fridefors_peng.linearpg.objects.entities.Entity

trait Physical extends Interactive {
  
  val V = Vector
  
  implicit def extractPhysicalOption(option:Option[Physical]):Physical = option match {
    case Some(obj) => obj
    case None      => DefaultPhysical
  }
  
  (Physical list) += this
  
  var movement:Vector
  var accerelation:Vector
  var gravity:Float
  var friction:Double = 0
  
  var relativeObject:Option[Physical] = None
  var prvRelativeObject:Option[Physical] = None
  
  def hspeed = movement.x
  def hspeed_= (hsp:Float):Unit = 
    movement = V(hsp,movement.y)
    
  def vspeed = movement.y
  def vspeed_= (vsp:Float):Unit = 
    movement = V(movement.x, vsp)
  
  override def update {
    super.update
    if(relativeObject != prvRelativeObject){
      movement += prvRelativeObject.movement + (-relativeObject.movement)
    }
    applyForces
    prvRelativeObject = relativeObject
    relativeObject = None
    
  }
  
  def velocity = movement.length
  def velocity_= (vel:Double):Unit = movement.whoseLengthIs(vel);
  
  
  def applyForces {
    movement += V(0, gravity)
    movement += accerelation
    exertForce(movement)
    move(movement)
    if (friction != 0) 
      velocity -= friction
  }
  
  def exertForce(force:Vector) {
    if(solid && force != NullVector){
      val objs = allPlaceMeetingList(position.x + force.x, position.y + force.y , Entity list)
      objs foreach {(o) =>
        o.relativeObject = Some(this)
        o.position += force
        }
    }
    
  }
  

  override def destroy {
    super.destroy
    (Physical list) -= this
  }

}

object Physical{
  val list:   ArrayBuffer[Physical] = ArrayBuffer()

}

object DefaultPhysical extends Interactive(NullVector, new Rectangle(0,0,0,0)) with Physical {
  
  var movement:Vector = Vector(0,0)
  var accerelation:Vector = Vector(0,0)
  var gravity:Float = 0
}