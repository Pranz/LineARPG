package lolirofle.graphics2d.text

import scala.collection.mutable.HashMap
import org.lwjgl.opengl.GL11
import scala.collection.immutable.IntMap
import lolirofle.graphics2d.text.chardata.FontChar
import lolirofle.data.{Position,Vector}

object DrawableObjectFont{
	val defaultRenderCacheSize=200;
}

/**
 * A cachable font made of Drawable objects.
 * 
 * @author Lolirofle
 */
class DrawablesFont(override val name:String,override val size:Short,override val lineHeight:Int,val chars:IntMap[FontChar],val useDisplayListCache:Boolean=true) extends Font with FormattableFont{
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
						cacheString(str,true)
				}
			}
			else
				drawStringRaw(str);
		GL11.glTranslatef(-x,-y,0);
	}
	
	/**
	 * Renders a character
	 * 
	 * @param chr Character to render
	 * @return Width and height of the rendered character
	 */
	def drawCharacter(pos:Position,chr:Char):Vector={
		val chrData:FontChar=
			if(chr<0)
				chars(0);
			else
				chars.getOrElse(chr.toInt,chars(0));
		
		chrData.draw(pos.x,pos.y,size)
		
		return Vector(chrData.xAdvance,chrData.yAdvance)
	}
	
	/**
	 * Renders a string without caching
	 * 
	 * @param str String to render
	 * @return Width and height of the rendered string
	 */
	protected def drawStringRaw(str:String,drawCharFunc:(Char,Position)=>Position=(chr,advanced)=>
			if(chr=='\n')
				Position(0,advanced.y+lineHeight);
			else
				advanced+drawCharacter(advanced,chr)
	):Position={
		var advanced=Position(0,0);
		var maxAdvanced=advanced
		
		str.foreach(chr=>{
			advanced=drawCharFunc(chr,advanced)
			
			if(advanced.x>maxAdvanced.x)maxAdvanced=maxAdvanced.withX(advanced.x)
			if(advanced.y>maxAdvanced.y)maxAdvanced=maxAdvanced.withY(advanced.y)
		});
		return maxAdvanced.withY(maxAdvanced.y+lineHeight)
	}

	override def drawStringFormatted(x:Float,y:Float,str:String,formattingFunc:Char=>Boolean){
		GL11.glTranslatef(x,y,0);
			drawStringRaw(str,(chr,advanced)=>{
				if(formattingFunc(chr)){
					if(chr=='\n')
						Position(0,advanced.y+lineHeight);
					else
						advanced+drawCharacter(advanced,chr)
				}
				else
					advanced
			});
		GL11.glTranslatef(-x,-y,0);
	}
	
	/**
	 * Caches the string str
	 * 
	 * @param str String to render and cache
	 * @param execute If the string should be rendered as it is cached
	 * @return Width and height of the rendered string
	 */
	protected def cacheString(str:String,execute:Boolean):Position={
		GL11.glNewList(cacheIdStart+cacheIdCounter,if(execute)GL11.GL_COMPILE_AND_EXECUTE else GL11.GL_COMPILE);
			val advanced=drawStringRaw(str)
		GL11.glEndList();
		caches.put(str,(cacheIdStart+cacheIdCounter,advanced))
		cacheIdCounter+=1
		
		return advanced
	}
				
	override def dimensionOf(str:String):Position=
		if(useDisplayListCache){
			initDisplayCache();
		
			caches.get(str) match{
				case Some((cache,advanced))=>advanced
				case None=>cacheString(str,false)
			}
		}
		else
			cacheString(str,false)

	override def widthOf(str:String):Float=dimensionOf(str).x
	override def heightOf(str:String):Float=dimensionOf(str).y
}
