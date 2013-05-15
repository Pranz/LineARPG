package lolirofle.gl2dlib.graphics.text

import scala.collection.mutable.HashMap
import org.lwjgl.opengl.GL11
import scala.collection.immutable.IntMap
import lolirofle.gl2dlib.graphics.text.chardata.FontChar

object DrawableObjectFont{
	val MAX_RENDER_CACHE_SIZE=200;
	val defaultRenderCacheSize=200;
}

/**
 * Makes a cachable font out of Drawable objects.
 * 
 * @author Lolirofle
 *
 * @param <T> Defines which Drawable class the LetterChars is, if multiple Drawable classes are needed for the LetterChars, then put T as ?.
 */
class DrawablesFont(override val name:String,override val size:Short,override val lineHeight:Int,val chars:IntMap[FontChar],val useDisplayListCache:Boolean=true) extends Font{
	protected var caches:HashMap[String,Int]=null;
	var cacheIdStart:Int=0;
	var cacheIdCounter=0;

	//letterPadding=new int[]{0,0,0,0};
	
	def initDisplayCache(cacheSize:Int=DrawableObjectFont.defaultRenderCacheSize):Unit={
		if(caches==null){
			if(useDisplayListCache){
				caches=new HashMap[String,Int];
				cacheIdStart=GL11.glGenLists(cacheSize);
			}
			else{
				caches=null;
			}
		}
	}
	
	def drawString(x:Float,y:Float,str:String){
		GL11.glTranslatef(x,y,0);
		
		if(useDisplayListCache){
			initDisplayCache();
			
			if(caches.contains(str)){
				GL11.glCallList(caches(str));//Draw cached render
			}
			else{
				caches.put(str,cacheIdStart+cacheIdCounter);
				GL11.glNewList(cacheIdStart+cacheIdCounter,GL11.GL_COMPILE_AND_EXECUTE);
					renderDrawableString(str);
				GL11.glEndList();
				cacheIdCounter+=1;
			}
		}
		else{
			renderDrawableString(str);
		}
		GL11.glTranslatef(-x,-y,0);
	}
	
	private def renderDrawableString(str:String){
		var xAdvanced=0;
		var yAdvanced=0;
		
		str.foreach(chr=>{
			val chrId=chr.toInt;
			
			if(chr=='\n'){
				xAdvanced=0;
				yAdvanced+=lineHeight;
			}
			else{
				val chrData:FontChar={
						if(chrId<0)
							chars(0);
						else
							chars.getOrElse(chrId,chars(0));
				}
				
				chrData.draw(xAdvanced,yAdvanced,size);
				xAdvanced+=chrData.xAdvance;
			}
		});
	}

	override def getCharsAllocated()=chars.size;
	override def getWidthOf(str:String):Int=0;
	override def getHeightOf(str:String):Int=0;
}
