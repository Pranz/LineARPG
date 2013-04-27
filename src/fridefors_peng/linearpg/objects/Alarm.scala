package fridefors_peng.linearpg.objects

/**
 * The alarm applies a function after a certain amount of frames. Has an optional argument to loop.
 * Make the amount of frames -1 to make the Alarm instantly destroy itself.
 */

class Alarm(frames:Int, f: () => Unit, loop:Boolean = false) extends GameObject {
	
	if(frames == 0){
		execute
	}else if (frames == -1){
		destroy
	}

	var currentFrame = 1
	
	def update(){
		if (currentFrame == frames) {
			execute
		}
		else currentFrame += 1
	}
	
	def execute {
		f()
		if (loop) currentFrame = 0
		else destroy
	}
}