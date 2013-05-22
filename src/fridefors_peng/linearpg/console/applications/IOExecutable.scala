package fridefors_peng.linearpg.console.applications

import scala.collection.mutable.HashMap
import scala.collection.mutable.Queue
import fridefors_peng.linearpg.input.KeyboardInputControl
import java.io.OutputStream
import fridefors_peng.linearpg.console.ApplicationConsole
import lolirofle.gl2dlib.util.MiscUtil
import fridefors_peng.linearpg.console.ConsoleFunction

abstract class ApplicationManifest(name:String){
	Applications.list+=((name,this))
	
	/**
	 * Help text for the application
	 * 
	 * @returns Help text
	 */
	def help:String
	
	/**
	 * Starts the application
	 * 
	 * @returns Application process
	 */
	def start():IOApplication
}

object Applications{
	/**
	 * List of applications with the associated name
	 */
	var list=HashMap[String,ApplicationManifest]()
	
	new ApplicationManifest("test"){
		override val help="Testing application"
		override def start()=new FunctionIOApplication(_=>"OKAY")
	}
	
	new ApplicationManifest("echo"){
		override val help="Echoes arguments\n\nSyntax: echo [str] <str ...>"//TODO: Fix newline bug
		
		override def start()=new IOApplication{
			override def main(out:IOHandlerApplication,args:Seq[String]){
				if(args.isEmpty)
					out.println(help)
				else
					args.foreach{
						out.println(_)
					}
			}
		}
	}
}

trait Application{
	def output(visibleLines:Int):Seq[String]
	def shouldClose:Boolean
	def onChar(char:Char)
	def onFunc(f:ConsoleFunction.Value)
	
	def onInit(args:Seq[String])
}

abstract class IOHandlerApplication extends OutputStream with Application{
	/**
	 * The lines of text in output
	 */
	var lines = new Queue[String]()
	
	val outputBuffer:StringBuilder=new StringBuilder()
	val inputBuffer:StringBuilder=new StringBuilder()
	
	override def output(visibleLines:Int)=lines:+"> "+inputBuffer
	override def onChar(char:Char){
		if(char==13){//Enter
			val in=inputBuffer.toString
			inputBuffer.clear()
			println(in)
			input(in)
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
	
	override def write(b:Int){print(b.toChar)}
	override def flush(){println()}
	
	def print(char:Char) {outputBuffer.append(char)}
	def print(str:String){outputBuffer.append(str)}
	def print(obj:Any)   {outputBuffer.append(obj)}
	def println(){lines.enqueue(outputBuffer.toString());outputBuffer.clear()}
	def println(char:Char) {print(char);println()}
	def println(str:String){print(str);println()}
	def println(obj:Any)   {print(obj);println()}
	
	def input(in:String)
}

trait IOApplication{
	def main(out:IOHandlerApplication,args:Seq[String])
}

class FunctionIOApplication(func:Seq[String]=>String) extends IOApplication{
	override def main(out:IOHandlerApplication,args:Seq[String]){
		out.println(func(args))
	}
}

