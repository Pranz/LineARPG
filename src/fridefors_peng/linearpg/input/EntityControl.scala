package fridefors_peng.linearpg.input

import fridefors_peng.linearpg.objects.entities.Entity
import fridefors_peng.linearpg.timing.Timer
import lolirofle.GameHandler
import lolirofle.data.Direction

/**
 * Controls any entity. General and thus final.
 */

final class EntityControl(ent:Entity, playerId:Int) extends Control{
	var currentAction = 0

	override def handleInput(input:InputRelated){
		if(input.keyIsDown(ControlKeys.PERFORM_ACTION)) ent.fAction(currentAction) match {
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
	
	override def onKeyPressed(input:InputRelated,key:ControlKey){
		//Get Control.Key enumeration value derived from Input.KEY int value
		key match{
			case ControlKeys.JUMP=>
				ent.jump
			
			case ControlKeys.MOVE_LEFT=>{
				ent.turn(Direction.Left)
				ent.run(Direction.Left)
			}
			
			case ControlKeys.MOVE_RIGHT=>{
				ent.turn(Direction.Right);
				ent.run(Direction.Right)
			}
			
			case ControlKeys.PERFORM_ACTION=>{
				ent.fAction(currentAction) match {
					case Some(action) => {if(action.ready) action.onClick; expect(currentAction);}
					case None         => {}
				}  
			}
			
			case ControlKeys.ACTION(actionId)=>
				currentAction=actionId
			
			case _=>{}
		}
	}
	override def onKeyReleased(input:InputRelated,key:ControlKey) =
		key match {
		case ControlKeys.MOVE_RIGHT=>{
				if(!input.keyIsDown(ControlKeys.MOVE_LEFT))
					ent.stand()
				else
					onKeyPressed(input,ControlKeys.MOVE_LEFT)
			}
			case ControlKeys.MOVE_LEFT=>{
				if(!input.keyIsDown(ControlKeys.MOVE_RIGHT))
					ent.stand()
				else
					onKeyPressed(input,ControlKeys.MOVE_RIGHT)
			}
			case ControlKeys.PERFORM_ACTION => {
				if (expects(currentAction)){
					ent.fAction(currentAction) match {
						case Some(action) => action.onRelease()
						case None         => {}
					}
				}
			}
			case _ => {}
		}
	
	override def onKeyChar(char:Char){}
}
