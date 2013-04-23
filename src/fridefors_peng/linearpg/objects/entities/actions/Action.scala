package fridefors_peng.linearpg.objects.entities.actions

import fridefors_peng.linearpg.objects.Alarm
import fridefors_peng.linearpg.objects.entities.Entity

abstract class Action(ent:Entity) {
  
  val name : String
  
  val delay:Int
  val duration:Int
  var ready = true
  
  protected def preAction:Unit
  protected def action:Unit
  protected def then:Unit
  
  protected final def execute {
    ready = false
    preAction
    new Alarm(delay,() => {
      action
      new Alarm(duration, () => {
        then
        ready = true
      })
    })
  }
  
  def onClick : Unit
  def onRelease() : Unit
  def whileClicked : Unit

}