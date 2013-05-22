package fridefors_peng.linearpg.console.applications

import java.io.OutputStream
import scala.collection.mutable.Queue
import fridefors_peng.linearpg.console.{ConsoleFunction,Process,OutputHandler,InputHandler}
import lolirofle.util.MiscUtil

/**
 * IOHandlerProcess
 * 
 * A application that takes care of standard input/output behaviours
 */
abstract class IOHandlerProcess extends OutputStream with Process with OutputHandler with InputHandler{
	/**
	 * The lines of text in output
	 */
	var lines = new Queue[String]()
	
	protected val outputBuffer:StringBuilder=new StringBuilder()
	protected val inputBuffer:StringBuilder=new StringBuilder()
	
	override def output(visibleLines:Int)=lines:+"> "+inputBuffer
	override def onChar(char:Char){
		if(char==13){//Enter
			val in=inputBuffer.toString
			inputBuffer.clear()
			writeln(in)
			onInput(in)
		}
		else if(!char.isControl)
			inputBuffer.append(char)
	}
	
	override def onFunc(f:ConsoleFunction.Value){
		if(f==ConsoleFunction.CLIPBOARD_COPY)//Copy
			MiscUtil.clipboard_=(inputBuffer.toString)
		else if(f==ConsoleFunction.CLIPBOARD_PASTE)//Paste
			inputBuffer.append(MiscUtil.clipboardString)
	}
	
	override def write(b:Int){write(b.toChar)}
	override def flush(){writeln()}
	
	override def write(char:Char) {outputBuffer.append(char)}
	override def write(str:String){outputBuffer.append(str)}
	override def write(obj:Any)   {outputBuffer.append(obj)}
	override def writeln(){lines.enqueue(outputBuffer.toString());outputBuffer.clear()}
	override def writeln(char:Char) {write(char);writeln()}
	override def writeln(str:String){write(str);writeln()}
	override def writeln(obj:Any)   {write(obj);writeln()}
	
	override def readLine()=inputBuffer.toString
	
	def onInput(in:String)
}