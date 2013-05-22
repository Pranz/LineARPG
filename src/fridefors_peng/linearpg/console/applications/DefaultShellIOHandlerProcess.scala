package fridefors_peng.linearpg.console.applications

import fridefors_peng.linearpg.console.Application

class DefaultShellIOHandlerProcess extends IOHandlerProcess{
	var shouldClose=false
	
	override def onInit(args:Seq[String]){}
	
	override def onInput(in:String){ 
		val(command :: args)=in.toString.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)").toList
		Application.list.get(command) match{
			case Some(app)=>{
				val ioHandler=this
				new Thread{//TODO: Check this code and see if we should stick to this way of doing multi threading
					override def run(){
						app.start().main(args,ioHandler,ioHandler)
					}
				}.start()
			}
			case None=>{}
		}
	}
}