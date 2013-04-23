package fridefors_peng.linearpg.objects

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