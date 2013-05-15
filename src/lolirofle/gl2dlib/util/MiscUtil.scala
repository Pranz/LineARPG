package lolirofle.gl2dlib.util

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.lwjgl.opengl.GL11;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;

object MiscUtil{	
	def getExecutionTimeOf(code: =>Unit,times:Int=1):Long={
		val startTime=System.nanoTime();
		var i=0;
		while(i<times){
			code;
			i+=1;
		}
		return System.nanoTime()-startTime;
	}
	
	def printExecutionTimeOf(code: =>Unit,times:Int=1){
		println("Execution time: " + (getExecutionTimeOf(code,times).toDouble/1000000) + " milliseconds");
	}
	
	/** If a string is on the system clipboard, this method returns it;
	 * otherwise it returns null.
	 * 
	 * @return Clipboard String
	 */
	def clipboardString:String={
	    val t:Transferable=Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

	    try{
	    	if(t!=null){
		    	if(t.isDataFlavorSupported(DataFlavor.stringFlavor)){
		    		return t.getTransferData(DataFlavor.stringFlavor).asInstanceOf[String];
		        }
	    	}
	    }
	    catch{
	    	case e:UnsupportedFlavorException=>println(e.getMessage());
	    	case e:IOException=>println(e.getMessage());
	    }
	    return null;
	}
	
	/** This method writes a string to the system clipboard.
	 * otherwise it returns null.
	 * 
	 * @param str String to be put into clipboard
	 */
	def clipboard_= (str:String){
	    val ss=new StringSelection(str);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}
}