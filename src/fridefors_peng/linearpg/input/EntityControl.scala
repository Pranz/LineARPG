package fridefors_peng.linearpg.input

import fridefors_peng.linearpg.objects.entities.Entity
import fridefors_peng.linearpg.objects.Timer
import lolirofle.gl2dlib.GameHandler
import lolirofle.gl2dlib.data.Direction

/**
 * Controls any entity. General and thus final.
 */

final class EntityControl(ent:Entity, playerID:Int) extends Control(playerID){
	var curAction = 0
	val reverseKeyMap = keyMap.map(_.swap).withDefaultValue(-1)

	override def update(delta:Int){
		if(GameHandler.keyIsDown(keyMap(Control.Key.PERFORM_ACTION))) ent.fAction(curAction) match {
			case Some(action) => action.whileClicked
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
	
	override def keyPressed(key:Int){
		
		//get Control.Key enumeration value derived from Input.KEY int value
		reverseKeyMap(key) match {
			case Control.Key.JUMP   => ent.jump
			case Control.Key.MOVE_LEFT=>{ent.turn(Direction.Left);ent.run(Direction.Left)}
			case Control.Key.MOVE_RIGHT=>{ent.turn(Direction.Right);ent.run(Direction.Right)}
			case Control.Key.PERFORM_ACTION => {
				ent.fAction(curAction) match {
					case Some(action) => {if(action.ready) action.onClick; expect(curAction);}
					case None         => {}
				}  
			}
			case _ => {}
		}
		
		curAction = reverseKeyMap(key) match {
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
	override def keyReleased(key:Int) =
		reverseKeyMap(key) match {
		case Control.Key.MOVE_RIGHT=>{
				if(!GameHandler.keyIsDown(keyMap(Control.Key.MOVE_LEFT)))
					ent.stand()
				else
					keyPressed(keyMap(Control.Key.MOVE_LEFT))
			}
			case Control.Key.MOVE_LEFT=>{
				if(!GameHandler.keyIsDown(keyMap(Control.Key.MOVE_RIGHT)))
					ent.stand()
				else
					keyPressed(keyMap(Control.Key.MOVE_RIGHT))
			}
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
	
	override def keyChar(char:Char){
		
	}
}
