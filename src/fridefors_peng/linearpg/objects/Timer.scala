package fridefors_peng.linearpg.objects

class Timer(private var paused:Boolean = false, private var milliseconds:Int = 0) extends GameObject {
	def update(delta:Int): Unit = {
		if (!paused) milliseconds += delta
	}

	def time  = milliseconds
	def stop  {milliseconds = 0; pause}
	def reset {milliseconds = 0; resume}
	def pause {paused = true}
	def resume{paused = false}
	def isPaused = paused
}