package fridefors_peng.linearpg.input

import org.newdawn.slick.Input
import fridefors_peng.linearpg.objects.entities.Entity
import fridefors_peng.linearpg.Main
import fridefors_peng.linearpg.objects.Timer

class EntityControl(ent:Entity, playerID:Int) extends Control(playerID) {
  
  var curAction = 0
  val keyMap_ = keyMap.map(_ swap)
  val actionCharge = new Timer(paused = true)

  def handleKeys(input: Input): Unit = {
    if (input.isKeyDown(keyMap(Control.Key.MOVE_LEFT)))  ent.run(Entity.LEFT)
    if (input.isKeyDown(keyMap(Control.Key.MOVE_RIGHT))) ent.run(Entity.RIGHT)
    if (input.isKeyDown(keyMap(Control.Key.PERFORM_ACTION))) ent.fAction(curAction) match {
      case Some(action) => action whileClicked
      case None         => {}
    }
  }
  
  private var exp = 0
  def expect(a:Int){
    exp = a
  }
  def expects(a:Int) =
    a == exp
  
  def keyPressed(key:Int, keyChar:Char){
    
    val key_ = if(keyMap_.contains(key))
      keyMap_(key)
      else -1

    key_ match {
      case Control.Key.JUMP   => ent.jump
      case Control.Key.CROUCH => ent.addSpeedMod(0.5f)
      case Control.Key.PERFORM_ACTION => {
        ent.fAction(curAction) match {
        		case Some(action) => {if(action.ready) action.onClick; expect(curAction); actionCharge reset}
        		case None         => {}
        }  
      }
      
      case _ => {}
    }
    
    curAction = key_ match {
      case Control.Key.ACTION_1 => 0
      case Control.Key.ACTION_2 => 1
      case Control.Key.ACTION_3 => 2
      case Control.Key.ACTION_4 => 3
      case Control.Key.ACTION_5 => 4
      case Control.Key.ACTION_6 => 5
      case Control.Key.ACTION_7 => 6
      case Control.Key.ACTION_8 => 7
      case Control.Key.ACTION_9 => 8
      case Control.Key.ACTION_0 => 9
      
      case _ => curAction
    }
    
  }
  def keyReleased(key:Int, keyChar:Char){
    val key_ = if(keyMap_.contains(key))
      keyMap_(key)
      else -1
    key_ match {
      case Control.Key.CROUCH => ent.removeSpeedMod(0.5f)
      case Control.Key.PERFORM_ACTION => {
        if (expects(curAction)){
        	ent.fAction(curAction) match {
        		case Some(action) => action.onRelease()
        		case None         => {}
        	}
        }
        actionCharge stop
      }
      case _ => {}
    }
  }

}