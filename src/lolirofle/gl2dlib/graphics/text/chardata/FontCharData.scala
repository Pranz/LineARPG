package lolirofle.gl2dlib.graphics.text.chardata

import org.lwjgl.opengl.GL11
import lolirofle.gl2dlib.graphics.Drawable
import lolirofle.gl2dlib.data._

class FontCharData[+T<:Drawable](val drawable:T)(override val xAdvance:Short=(drawable.width+1).toShort,val xOffset:Short=0,val yOffset:Short=0,horizontalAlign:Horizontal=Direction.Left,verticalAlign:Vertical=Direction.Down) extends FontChar{
	override def toString():String="[FontCharData id=" + " drawable=" + drawable + "]";

	override def yAdvance:Short=0;
	
	override def draw(x:Float,y:Float,fontSize:Short){
		val dx=x+xOffset
		val dy=yOffset+(
				if(verticalAlign==Direction.Down)
					y+(fontSize-drawable.height)
				else if(verticalAlign==Direction.Center)
					y+((fontSize-drawable.height).toFloat/2)
				else
					y
		)
		GL11.glTranslatef(dx,dy,0);
		drawable.draw()
		GL11.glTranslatef(-dx,-dy,0);
	}
}
