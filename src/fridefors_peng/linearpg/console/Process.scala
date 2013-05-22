package fridefors_peng.linearpg.console

trait Process{
	def output(visibleLines:Int):Seq[String]
	def shouldClose:Boolean
	def onChar(char:Char)
	def onFunc(f:ConsoleFunction.Value)
	
	def onInit(args:Seq[String])
}

trait OutputHandler{	
	def write(char:Char)
	def write(str:String){str.foreach{print _}}
	def write(obj:Any)   {print(obj.toString)}
	def writeln()
	def writeln(char:Char) {write(char);writeln()}
	def writeln(str:String){write(str);writeln()}
	def writeln(obj:Any)   {write(obj);writeln()}
}

trait InputHandler{
	def readLine():String
	//def readWait //TODO: Should we have a readWait? 
}