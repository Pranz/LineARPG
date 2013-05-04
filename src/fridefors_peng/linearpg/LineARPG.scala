package fridefors_peng.linearpg

import org.newdawn.slick.{Game, Graphics, GameContainer, UnicodeFont}
import org.newdawn.slick.font.effects.ColorEffect;
import fridefors_peng.linearpg.terrain._
import fridefors_peng.linearpg.input._
import fridefors_peng.linearpg.objects.entities.Humanoid
import fridefors_peng.linearpg.objects._
import fridefors_peng.linearpg.objects.entities.actions.Action

class LineARPG extends Game {
	var obj : Humanoid = null
	var control : EntityControl = null
	var dt : Int = 0

	def init(container: GameContainer): Unit = {
		obj = new Humanoid(Vector(100, 250))
		Main.input = container.getInput
		new Block(Vector(100, 500), 50, 2)
		new Block(Vector(600, 200), 2, 50)
		new PhysicalBlock(Vector(360, 450), 6, 2)
		control = new EntityControl(obj, 0)
		new MetaGameController(0)
		initFonts
	}

	def update(container: GameContainer, delta_t: Int): Unit = {
		dt = {
			if (delta_t > 100) 100
			else delta_t
		}
		(GameObject list).clone foreach (_ update(dt))
	}

	def render(container: GameContainer, g: Graphics): Unit = {
		g.setFont(Main.FNT_ANDALE_MONO)
		g.translate(-Main.Camera.x,-Main.Camera.y)
			(Renderable list) foreach (_.draw(g))
		g.translate( Main.Camera.x, Main.Camera.y)
		Main.drawList(("fps: " + container.getFPS)::debugList, g)
	}

	def closeRequested(): Boolean = true

	def getTitle(): String = "LINEARPG"
		
	def initFonts {
		initFont(Main.FNT_ANDALE_MONO)
	}
	
	def initFont(fnt: UnicodeFont){
		fnt.addAsciiGlyphs()
		fnt.addGlyphs(400,600)
		fnt.getEffects().asInstanceOf[java.util.List[ColorEffect]].add(new ColorEffect())
		fnt.loadGlyphs()
		
	}
		
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
			"selected action: "+ optionActionName(obj.fAction(control.curAction)),
			"dt: " + dt
		) 
	}
}