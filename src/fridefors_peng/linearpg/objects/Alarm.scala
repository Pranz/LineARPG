package fridefors_peng.linearpg.objects

class Alarm(milliseconds:Int, f: () => Unit, loop:Boolean = false) extends GameObject {
	
	if(milliseconds == 0){
		execute
	}else if (milliseconds == -1){
		destroy
	}

	var currentFrame = 1
	
	def update(delta:Int){
		if(currentFrame >= milliseconds)
			execute
		else
			currentFrame += delta
	}
	
	def execute {
		f()
		if(loop)
			currentFrame = 0
		else
			destroy
	}
}
