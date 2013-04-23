package fridefors_peng.linearpg

import org.newdawn.slick.{Game, Graphics, GameContainer}
import fridefors_peng.linearpg.terrain._
import fridefors_peng.linearpg.input._
import fridefors_peng.linearpg.objects.entities.Humanoid
import fridefors_peng.linearpg.objects._
import fridefors_peng.linearpg.objects.entities.actions.Action

class LineARPG extends Game {
	var obj : Humanoid = null
	var control : EntityControl = null

	def init(container: GameContainer): Unit = {
		obj = new Humanoid(Vector(100, 250))
		Main.input = container.getInput
		new Block(Vector(100, 500), 50, 2)
		new TriangleBlock(Vector(300,500), 20, -20)
		new Block(Vector(600, 200), 2, 50)
		new PhysicalBlock(Vector(360, 450), 6, 2)
		control = new EntityControl(obj, 0)
		new Alarm(60, () => println("Hejhej"))
	}

	def update(container: GameContainer, delta_t: Int): Unit = {
		(GameObject list).clone foreach (_ update)
	}

	def render(container: GameContainer, g: Graphics): Unit = {
		g.translate(-Main.Camera.x,-Main.Camera.y)
			(Renderable list) foreach (_.draw(g))
		g.translate( Main.Camera.x, Main.Camera.y)
		Main.drawList(("fps: " + container.getFPS)::debugList, g)
	}

	def closeRequested(): Boolean = true

	def getTitle(): String = "LINEARPG"
		
	def debugList:List[String] = {
		def optionActionName(maybeA : Option[Action]) : String = maybeA match {
			case Some(action) => action.name
			case _            => "No action"
		}
		
		implicit def extractPhysicalOption(option:Option[Physical]):Physical = option match {
			case Some(obj) => obj
			case None      => DefaultPhysical
		}
		
		List(
			"x: "         + obj.position.x,
			"y: "         + obj.position.y,
			"dx: "        + obj.deltaPos.x,
			"dy: "        + obj.deltaPos.y,
			"hsp: "	      + obj.hspeed,
			"vsp: "       + obj.vspeed,
			"velocity: "  + obj.velocity,
			"relative x: "+ obj.prvRelativeObject.movement.x,
			"relative y: "+ obj.prvRelativeObject.movement.y,
			"selected action: "+ optionActionName(obj.fAction(control.curAction))
		) 
	}
}