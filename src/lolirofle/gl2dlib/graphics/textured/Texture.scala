package lolirofle.gl2dlib.graphics.textured
/*
import scala.collection.mutable.ArrayBuffer
import org.lwjgl.opengl.GL11
import java.nio.{ByteBuffer,IntBuffer,ByteOrder}
import java.io.IOException
import org.lwjgl.BufferUtils
import lolirofle.gl2dlib.util.NumUtil
import lolirofle.gl2dlib.gl.GLTextureFilter

object Texture{
	protected val textures=new ArrayBuffer[Texture];
	
	def get(index:Int)=textures(index);
	def length=textures.length;
}

class Texture(val refName:String,val id:Int,val target:Int,val width:Int,val height:Int,val hasAlpha:Boolean){
	Texture.textures+=this;
	
	def exists=Texture.textures.contains(this);
	
	val textureWidth:Int=NumUtil.ceilToPowerOfTwo(width);
	val textureHeight:Int=NumUtil.ceilToPowerOfTwo(height);
	
	def data:Array[Byte]={
		val buffer = BufferUtils.createByteBuffer((if(hasAlpha)4 else 3)*textureWidth*textureHeight);
		GL11.glGetTexImage(GL11.GL_TEXTURE_2D,0,if(hasAlpha)GL11.GL_RGBA else GL11.GL_RGB,GL11.GL_UNSIGNED_BYTE,buffer);
		val data=new Array[Byte](buffer.limit());
		buffer.get(data);
		buffer.clear();
		return data;
	};
	
	def release(){
		Texture.textures-=this;
		
		val texBuf=TextureLoader.createIntBuffer(1); 
		texBuf.put(id);
		texBuf.flip();
		
		GL11.glDeleteTextures(texBuf);
	};
}

object TextureLoader{	
	private var dstPixelFormat=GL11.GL_RGBA8;
	
	def newTextureId():Int={
		return GL11.glGenTextures();
	}
	
	def createIntBuffer(size:Int):IntBuffer={
		val temp:ByteBuffer=ByteBuffer.allocateDirect(size*4);
		temp.order(ByteOrder.nativeOrder());
		return temp.asIntBuffer();
	}
	
	/**
	 * Get a texture from a image file
	 * 
	 * @param in The stream from which we can load the image
	 * @param resourceName The name to give this image in the internal cache
	 * @param flipped True if we should flip the image on the y-axis while loading
	 * @param target The texture target we're loading this texture into
	 * @param minFilter Filter to apply when scaling down
	 * @param magFilter Filter to apply when scaling up
	 * @param transparent The color to interpret as transparent or null if none
	 * @return The loaded texture
	 * @throws IOException Indicates a failure when loading a image that is too big
	 */
	def getTexture(imageData:ImageData,resourceName:String,magFilter:GLTextureFilter=GLTextureFilter.NEAREST,minFilter:GLTextureFilter=GLTextureFilter.NEAREST,target:Int=GL11.GL_TEXTURE_2D):Texture={
		val hasAlpha=imageData.depth==32;
		val texture=new Texture(resourceName,newTextureId(),target,imageData.width,imageData.height,hasAlpha);
		Renderer.g.texture=texture;

		val max=GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE);
		if(texture.textureWidth>max||texture.textureHeight>max)
			throw new IOException("Attempt to allocate a texture that is too big for the current hardware ("+texture.textureWidth+","+texture.textureHeight+") [Max: "+max+"]");

		GL11.glTexParameteri(target,GL11.GL_TEXTURE_MIN_FILTER,minFilter.GLInt);
		GL11.glTexParameteri(target,GL11.GL_TEXTURE_MAG_FILTER,magFilter.GLInt);
		GL11.glTexImage2D(target,0,dstPixelFormat,texture.textureWidth,texture.textureHeight,0,if(hasAlpha)GL11.GL_RGBA else GL11.GL_RGB,GL11.GL_UNSIGNED_BYTE,imageData.data); 

		return texture; 
	}
}
*/
