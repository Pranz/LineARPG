package fridefors_peng.linearpg.timing

import fridefors_peng.linearpg.objects.GameObject
import fridefors_peng.linearpg.objects.Updatable

/**
 * The alarm applies a function after a certain amount of frames. Has an optional argument to loop.
 * Make the amount of frames -1 to make the Alarm instantly destroy itself.
 */
class Alarm(val milliseconds:Int,func:()=>Unit,var loop:Boolean=false) extends GameObject with Updatable{	
	if(milliseconds==0)
		execute
	else if(milliseconds<0)
		onDestroy

	var currentFrame = 1
	
	override def update(delta:Int){
		if(currentFrame >= milliseconds)
			execute
		else
			currentFrame += delta
	}
	
	def execute{
		func()
		if(loop)
			currentFrame = 0
		else
			onDestroy
	}
	
	def percentage:Double=currentFrame.toDouble/milliseconds
}
