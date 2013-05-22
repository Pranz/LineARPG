package fridefors_peng.linearpg.console.applications

import fridefors_peng.linearpg.console.{OutputHandler,InputHandler}

trait IOExecutable{
	def main(args:Seq[String],in:InputHandler,out:OutputHandler)
}

class FunctionIOExecutable(func:Seq[String]=>String) extends IOExecutable{
	override def main(args:Seq[String],in:InputHandler,out:OutputHandler){
		out.writeln(func(args))
	}
}

