package lolirofle.gl2dlib.graphics

object Color{
	val BLACK       = Color fromFloat(0f  ,0f  ,0f  ,1f  );
	val WHITE       = Color fromFloat(1f  ,1f  ,1f  ,1f  );
	val GRAY        = Color fromFloat(0.5f,0.5f,0.5f,1f  );
	val RED         = Color fromFloat(1f  ,0   ,0   ,1f  );
	val GREEN       = Color fromFloat(0   ,1f  ,0   ,1f  );
	val BLUE        = Color fromFloat(0   ,0   ,1f  ,1f  );
	val YELLOW      = Color fromFloat(1f  ,1f  ,0   ,1f  );
	val CYAN        = Color fromFloat(0   ,1f  ,1f  ,1f  );
	val MAGENTA     = Color fromFloat(1f  ,0   ,1f  ,1f  );
	val LIGHTGRAY   = Color fromFloat(.75f,.75f,.75f,1f  );
	val DARMGRAY    = Color fromFloat(.25f,.25f,.25f,1f  );
	val DARKRED     = Color fromFloat(.5f ,0   ,0   ,1f  );
	val DARKGREEN   = Color fromFloat(0   ,.5f ,0   ,1f  );
	val DARMBLUE    = Color fromFloat(0   ,0   ,.5f ,1f  );
	val PURPLE      = Color fromFloat(.5f ,0   ,.5f ,1f  );
	val ORANGE      = Color fromFloat(1f  ,.65f,0   ,1f  );
	val TRANSPARENT = Color(0   ,0   ,0   ,0   );
	
	def apply(red:Int,green:Int,blue:Int,alpha:Int=255):Color=Colorl(red.toLong|(green.toLong<<8)|(blue.toLong<<16)|(alpha.toLong<<24));
	def fromFloat(red:Float,green:Float,blue:Float,alpha:Float=1):Colorf=Colorf(red,green,blue,alpha);
	def fromFloatToColour(red:Float,green:Float,blue:Float,alpha:Float=1):Color=Color((red*255).toInt,(green*255).toInt,(blue*255).toInt,(alpha*255).toInt);
	
	implicit def color2colorf(c:Color)=Colorf(c.red.toFloat/255f,c.green.toFloat/255f,c.blue.toFloat/255f,c.alpha.toFloat/255f);
}

trait Color{
	def red:Byte
	def green:Byte
	def blue:Byte
	def alpha:Byte
	
	def inverted:Color
}

case class Colorl(color:Long) extends Color{
	override def inverted:Color=Color(255-red&0xFF,255-green&0xFF,255-blue&0xFF,alpha&0xFF);
	
	override def red  = (color&0xFF).toByte;
	override def green= ((color&0xFF00)>>8).toByte;
	override def blue = ((color&0xFF0000)>>16).toByte;
	override def alpha= ((color&0xFF000000)>>24).toByte;
}

case class Colorf(val redf:Float,val greenf:Float,val bluef:Float,val alphaf:Float=1) extends Color{
	override def inverted:Color=Colorf(1-redf,1-greenf,1-bluef,alpha);
	
	override def red  = math.round(redf*255).toByte;
	override def green= math.round(greenf*255).toByte;
	override def blue = math.round(bluef*255).toByte;
	override def alpha= math.round(alphaf*255).toByte;

	/**
	 * Scales the color
	 * @param scale The amount to scale
	 * @return The scaled color
	 */
	def scale(scale:Float):Colorf=Colorf(redf*scale,greenf*scale,bluef*scale,alphaf);
	def scale(scaleR:Float,scaleG:Float,scaleB:Float,scaleA:Float=1):Colorf=Colorf(redf*scaleR,greenf*scaleG,bluef*scaleB,alphaf*scaleA);
}

trait ColorBlend{
	def blendChannel(originalChannel:Float,modifierChannel:Float):Float
	
	def blendColor(originalColor:Color,modifierColor:Color):Colorf=
		Colorf(
				blendChannel(originalColor.redf,modifierColor.redf),
				blendChannel(originalColor.greenf,modifierColor.greenf),
				blendChannel(originalColor.bluef,modifierColor.bluef),
				blendChannel(originalColor.alphaf,modifierColor.alphaf)
		);
	
	def blendChannel(originalChannel:Float,modifierChannel:Float,blend:Float):Float=
		if(blend==0f)
			originalChannel
		else if(blend==1f)
			modifierChannel
		else
			(blendChannel(originalChannel,modifierChannel)-originalChannel)*blend+originalChannel;
	
	def blendColor(originalColor:Color,modifierColor:Color,rBlend:Float,gBlend:Float,bBlend:Float,aBlend:Float):Colorf=
		Colorf(
				blendChannel(originalColor.redf,modifierColor.redf,rBlend),
				blendChannel(originalColor.greenf,modifierColor.greenf,gBlend),
				blendChannel(originalColor.bluef,modifierColor.bluef,bBlend),
				blendChannel(originalColor.alphaf,modifierColor.alphaf,aBlend)
		);
}

/**
 * <P>Blend methods
 * <P>For reference: <a href="http://blog.deepskycolors.com/archivo/2010/04/21/formulas-for-Photoshop-blending-modes.html">Link</a>
 * @author Lolirofle
 */
object ColorBlend{
	/**
	 * <P>Adds the values of the modifierColor on the values of originalColor using arithmetic addition.
	 */
	case object ADDITIVE extends ColorBlend{
		override def blendChannel(originalChannel:Float,modifierChannel:Float):Float=
			originalChannel+modifierChannel;
	}
	/**
	 * <P>Subtracts the values of originalColor with the values of the modifierColor using arithmetic subtraction.
	 */
	case object SUBTRACTIVE extends ColorBlend{
		override def blendChannel(originalChannel:Float,modifierChannel:Float):Float=
			originalChannel-modifierChannel;
	}
	/**
	 * <P>Multiplies the values of originalColor with the values of the modifierColor using arithmetic multiplication.
	 * <P>Results a darker color
	 */
	case object MULTIPLY extends ColorBlend{
		override def blendChannel(originalChannel:Float,modifierChannel:Float):Float=
			originalChannel*modifierChannel;
	}
	/**
	 * <P>Selects the darkest value of the values of originalColor and modifierColor and puts it into the new color.
	 */
	case object DARKEST extends ColorBlend{
		override def blendChannel(originalChannel:Float,modifierChannel:Float):Float=
			if(originalChannel>modifierChannel)originalChannel else modifierChannel;
	}
	/**
	 * <P>Selects the lightest value of the values of originalColor and modifierColor and puts it into the new color.
	 */
	case object LIGHTEST extends ColorBlend{
		override def blendChannel(originalChannel:Float,modifierChannel:Float):Float=
			if(originalChannel<modifierChannel)originalChannel else modifierChannel;
	}
	/**
	 * <P>Multiplies the values of a inverted originalColor with the values of a inverted modifierColor using arithmetic multiplication and then returning the inverted result.
	 * <P>Results a brighter color
	 */
	case object SCREEN extends ColorBlend{
		override def blendChannel(originalChannel:Float,modifierChannel:Float):Float=
			1f-((1f-originalChannel)*(1f-modifierChannel));
	}
	/**
	 * 
	 */
	case object OVERLAY extends ColorBlend{
		override def blendChannel(orig:Float,mod:Float):Float=
			if(orig>.5f)1f-(1f-2f*(orig-.5f))*1f-mod else (2f*orig)*mod;
	}
}