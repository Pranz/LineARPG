package lolirofle.graphics2d.textured;

import lolirofle.graphics2d._
import lolirofle.gl2d.GLGeom
/*
/**
 * A drawable image
 * @author Lolirofle
 */
trait Image extends Drawable{
	def textureDrawX1:Float;
	def textureDrawY1:Float;
	def textureDrawX2:Float;
	def textureDrawY2:Float;
	
	protected def calcDrawW(xOffset:Float):Float=xOffset/texture.textureWidth;
	protected def calcDrawH(yOffset:Float):Float=yOffset/texture.textureHeight;
	def xOffset:Int=textureDrawX1.toInt*texture.textureWidth;
	def yOffset:Int=textureDrawY1.toInt*texture.textureHeight;
	
	def draw(x1:Float,y1:Float,x2:Float,y2:Float){
		//drawTexture(x1,y1,width,height,textureDrawX1,textureDrawY1,textureDrawX2,textureDrawY2);
	}
	
	def drawScaled(xScale:Float,yScale:Float){
		//drawTexture(0,0,width*xScale,height*yScale,textureDrawX1,textureDrawY1,textureDrawX2,textureDrawY2);
	}
	
	override val geomType=GLGeom.QUADS;
}
*/
