package fridefors_peng.linearpg.objects

/**
 * The alarm applies a function after a certain amount of frames. Has an optional argument to loop.
 * Make the amount of frames -1 to make the Alarm instantly destroy itself.
 */
class Alarm(milliseconds:Int, f: () => Unit, loop:Boolean = false) extends GameObject {	
	if(milliseconds==0)
		execute
	else if(milliseconds<0)
		destroy

	var currentFrame = 1
	
	def update(delta:Int){
		if(currentFrame >= milliseconds)
			execute
		else
			currentFrame += delta
	}
	
	def execute{
		f()
		if(loop)
			currentFrame = 0
		else
			destroy
	}
}
