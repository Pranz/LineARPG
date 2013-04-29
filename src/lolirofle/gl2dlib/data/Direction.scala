package lolirofle.gl2dlib.data

trait Horizontal extends Direction{val y:Charge=Charge.Neutral}
trait Vertical   extends Direction{val x:Charge=Charge.Neutral}

trait Direction{
	def x:Charge
	def y:Charge
}

object Direction{
	implicit def toByte(h:Horizontal):Byte=h.x.value
	implicit def toByte(h:Vertical):Byte=h.y.value
	
	object Left extends Horizontal{
		override val x=Charge.-
	}
	
	object Right extends Horizontal{
		override val x=Charge.+
	}
	
	object Up extends Vertical{
		override val y=Charge.-
	}
	
	object Down extends Vertical{
		override val y=Charge.+
	}
	
	object Center extends Direction with Horizontal with Vertical{
		override val x=Charge.Neutral
		override val y=Charge.Neutral
	}
}
