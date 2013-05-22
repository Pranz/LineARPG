package lolirofle.gl2d

import lolirofle.graphics2d.Drawable
import org.lwjgl.opengl.GL11

/*class DisplayCaches(size:Int){//TODO DisplayCacheRanges
	val id=GL11.glGenLists(size)
	
	def apply(index:Int)=
		if(index<0||index>=size)
			throw new ArrayIndexOutOfBoundsException(index)
		else
			DisplayCache(id+index)
}
*/

case class DisplayCache(val drawable:Drawable) extends Drawable{
	/**
	 * Cache id
	 * 
	 * -1 = Not initialized
	 *  0 = Error
	 * >0 = Id for display list
	 */
	protected var _id = -1
	
	def id=_id

	override def draw(){
		if(id<=0){//If not initialized or error has occurred
			_id=GL11.glGenLists(1)//Generate list with one
			
			if(_id==0)//If generated is error
				drawable.draw()//Draw normally
			else{
				GL11.glNewList(id,GL11.GL_COMPILE_AND_EXECUTE);
					drawable.draw()
				GL11.glEndList();
			}
		}
		else
			GL11.glCallList(id)
	};

	override def width=drawable.width
	override def height=drawable.height
}

class DisplayCacheSolo(val drawFunc:()=>Unit,override val width:Float,override val height:Float) extends Drawable{
	def this(drawable:Drawable)=this(drawable.draw,drawable.width,drawable.height)
	
	/**
	 * Cache id
	 * 
	 * -1 = Not initialized
	 *  0 = Error
	 * >0 = Id for display list
	 */
	protected var _id = -1
	
	def id=_id

	override def draw(){
		if(id<=0){//If not initialized or error has occurred
			_id=GL11.glGenLists(1)//Generate list with one
			
			if(_id==0)//If generated is error
				drawFunc()//Draw normally
			else{
				GL11.glNewList(id,GL11.GL_COMPILE_AND_EXECUTE);
					drawFunc()
				GL11.glEndList();
			}
		}
		else
			GL11.glCallList(id)
	};
}