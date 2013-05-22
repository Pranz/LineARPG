package fridefors_peng.linearpg.console

import scala.collection.mutable.HashMap
import fridefors_peng.linearpg.console.applications._
import lolirofle.util.NumUtil

abstract class Application(name:String){
	Application.list+=((name,Application.this))
	
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
	def start():IOExecutable
}

object Application{
	/**
	 * List of applications with the associated name
	 */
	var list=HashMap[String,Application]()
	
	new Application("test"){
		override val help="Testing application"
		override def start()=new FunctionIOExecutable(_=>"OKAY")
	}
	
	new Application("echo"){
		override val help="Echoes arguments\n\nSyntax: echo [str] <str ...>"//TODO: Fix newline bug
		
		override def start()=new IOExecutable{
			override def main(args:Seq[String],in:InputHandler,out:OutputHandler){
				if(args.isEmpty)
					out.writeln(help)
				else
					args.foreach{
						out.writeln(_)
					}
			}
		}
	}
	
	new Application("timer"){
		override val help="Sets a timer that will warn on specified time\n\nSyntax: timer [time] <unit:millisecond/ms|second/s|minute/m|hour/h=ms>"
		
		override def start()=new IOExecutable{
			override def main(args:Seq[String],in:InputHandler,out:OutputHandler){
				args.length match{
					case len if(len>0)=>{
						NumUtil.parse(args(0)) match{
							case Some(n)=>{
								val ms=
									if(len==1)
										n
									else args(1) match{
										case "hours"|"h"=>n*3600000
										case "minutes"|"m"=>n*60000
										case "seconds"|"s"=>n*1000
										case _=>n
									}

								out.writeln("Timer set to "+ms+" ms")
								Thread.sleep(ms.toLong)
								out.writeln("Timer done")
							}
								
							case None=>out.writeln(help)
						}
					}
					case _=>out.writeln(help)
				}
			}
		}
	}
}