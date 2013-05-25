package lolirofle.data

trait Horizontal extends Direction{override val y:Charge=Charge.Neutral}
trait Vertical   extends Direction{override val x:Charge=Charge.Neutral}

trait Direction{
	def x:Charge
	def y:Charge
	
	def and(dir:Direction)=Direction.Dir(x.value+dir.x.value,y.value+dir.y.value)
	
	override def toString()="Direction["+
		(x match{
			case Charge.- => "Left"
			case Charge.Neutral => "Center"
			case Charge.+ => "Right"
			case _ => "Unknown"
		})+','+
		(y match{
			case Charge.- => "Up"
			case Charge.Neutral => "Middle"
			case Charge.+ => "Down"
			case _ => "Unknown"
		})+']'
}

object Direction{
	implicit def toByte(h:Horizontal):Byte=h.x.value
	implicit def toByte(h:Vertical):Byte=h.y.value
	
	case object Left extends Horizontal{
		override val x=Charge.-
	}
	
	case object Right extends Horizontal{
		override val x=Charge.+
	}
	
	case object Up extends Vertical{
		override val y=Charge.-
	}
	
	case object Down extends Vertical{
		override val y=Charge.+
	}
	
	case class Dir(override val x:Charge,override val y:Charge) extends Horizontal with Vertical{
		def this(x:Double,y:Double)=this(if(x<0)Charge.- else if(x>0)Charge.+ else Charge.Neutral,if(y<0)Charge.- else if(y>0)Charge.+ else Charge.Neutral)
	}
	
	object Dir{
		def apply(x:Double,y:Double)=new Dir(x,y)
	}
	
	object Center extends Direction with Horizontal with Vertical{
		override val x=Charge.Neutral
		override val y=Charge.Neutral
	}
}
