package lolirofle.gl2dlib.graphics

import lolirofle.gl2dlib.gl.GLDraw
import org.lwjgl.opengl.GL11

trait Drawable{
	def draw();
	
	def drawColored(c:Color){
		GL11.glColor4ub(c.red,c.blue,c.green,c.alpha)
		draw()
		GL11.glColor4b(127,127,127,127)
	}
	
	def drawRotated(angle:Float){
		GL11.glRotatef(angle,5,5,1f);
		draw()
		GL11.glRotatef(0,0f,0f,1f);
	}
	
	def width:Float;
	def height:Float;
}