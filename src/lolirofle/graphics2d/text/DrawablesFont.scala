package lolirofle.graphics2d.text

import scala.collection.mutable.HashMap
import org.lwjgl.opengl.GL11
import scala.collection.immutable.IntMap
import lolirofle.graphics2d.text.chardata.FontChar
import lolirofle.data.Position

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
	protected var caches:HashMap[String,(Int,Position)]=null;//TODO: Cache is limited to normal drawString because there's no indication of any difference between different renders in storage, only which string it is. Maybe it should stay this way and implement a e.g. "withColor" or "withFormatting" trait that creates new fonts for fonts to use 
	var cacheIdStart:Int=0;
	var cacheIdCounter=0;

	//letterPadding=new int[]{0,0,0,0};
	
	def initDisplayCache(cacheSize:Int=DrawableObjectFont.defaultRenderCacheSize):Unit={
		if(caches==null){
			if(useDisplayListCache){
				caches=new HashMap[String,(Int,Position)];
				cacheIdStart=GL11.glGenLists(cacheSize);
			}
			else{
				caches=null;
			}
		}
	}
	
	override def drawString(x:Float,y:Float,str:String){
		GL11.glTranslatef(x,y,0);
			if(useDisplayListCache){
				initDisplayCache();
				
				caches.get(str) match{
					case Some((cache,advanced))=>
						GL11.glCallList(cache);//Draw cached render
					
					case None=>
						cacheRender(str,true)
				}
			}
			else
				renderDrawableString(str,{_.draw(_,_,size)});
		GL11.glTranslatef(-x,-y,0);
	}
	
	/**
	 * Renders the string str
	 * 
	 * @param str String to render
	 * @returns Width and height of the rendered string
	 */
	private def renderDrawableString(str:String,func:(FontChar,Float,Float)=>Unit):Position={
		var xAdvanced=0;
		var yAdvanced=0;
		
		str.foreach(chr=>{			
			if(chr=='\n'){
				xAdvanced=0;
				yAdvanced+=lineHeight;
			}
			else{
				val chrData:FontChar={
						if(chr<0)
							chars(0);
						else
							chars.getOrElse(chr.toInt,chars(0));
				}
				
				func(chrData,xAdvanced,yAdvanced)
				xAdvanced+=chrData.xAdvance;
			}
		});
		return Position(xAdvanced,yAdvanced)
	}

	protected def cacheRender(str:String,execute:Boolean,renderFunc:(FontChar,Float,Float)=>Unit={_.draw(_,_,size)}):Position={
		GL11.glNewList(cacheIdStart+cacheIdCounter,if(execute)GL11.GL_COMPILE_AND_EXECUTE else GL11.GL_COMPILE);
			val advanced=renderDrawableString(str,renderFunc)
		GL11.glEndList();
			
		caches.put(str,(cacheIdStart+cacheIdCounter,advanced))
		cacheIdCounter+=1
		
		return advanced
	}
	
	override def getCharsAllocated()=chars.size;
	
	override def widthOf(str:String):Float=caches.get(str) match{
		case Some((cache,advanced))=>advanced.x
		case None=>cacheRender(str,false).x
	}

	override def heightOf(str:String):Float=caches.get(str) match{
		case Some((cache,advanced))=>advanced.y
		case None=>cacheRender(str,false).y
	}
}
