package fridefors_peng.linearpg.input

import org.newdawn.slick.Input
import fridefors_peng.linearpg.objects.entities.Entity
import fridefors_peng.linearpg.Main
import fridefors_peng.linearpg.objects.Timer

/**
 * Controls any entity. General and thus final.
 */

final class EntityControl(ent:Entity, playerID:Int) extends Control(playerID) {
	
	var curAction = 0
	val reverseKeyMap = keyMap.map(_ swap)

	def handleKeys(input: Input): Unit = {
		if (input.isKeyDown(keyMap(Control.Key.MOVE_LEFT)))  ent.run(Entity.LEFT)
		if (input.isKeyDown(keyMap(Control.Key.MOVE_RIGHT))) ent.run(Entity.RIGHT)
		if (input.isKeyDown(keyMap(Control.Key.PERFORM_ACTION))) ent.fAction(curAction) match {
			case Some(action) => action whileClicked
			case None         => {}
		}
	}
	
	/*
	 * 
	 */
	private var exp = 0
	def expect(actionID:Int){
		exp = actionID
	}
	def expects(actionID:Int) =
		actionID == exp
	
	def keyPressed(key:Int, keyChar:Char){
		
		//get Control.Key enumeration value derived from Input.KEY int value
		val key_ = if(reverseKeyMap.contains(key))
			reverseKeyMap(key)
			else -1

		key_ match {
			case Control.Key.JUMP   => ent.jump
			case Control.Key.CROUCH => ent.addSpeedMod(0.5f)
			case Control.Key.PERFORM_ACTION => {
				ent.fAction(curAction) match {
					case Some(action) => {if(action.ready) action.onClick; expect(curAction);}
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
		val key_ = if(reverseKeyMap.contains(key))
			reverseKeyMap(key)
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
			}
			case _ => {}
		}
	}

}