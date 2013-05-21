package fridefors_peng.linearpg.input

trait ControlKey

object ControlKeys{
	case object MOVE_LEFT extends ControlKey
	case object MOVE_RIGHT extends ControlKey
	case object MOVE_UP extends ControlKey
	case object MOVE_DOWN extends ControlKey

	case object PERFORM_ACTION extends ControlKey
	case object JUMP extends ControlKey
	case object CROUCH extends ControlKey
	case class ACTION(i:Int) extends ControlKey
	
	case object CONSOLE_KEY extends ControlKey
}
