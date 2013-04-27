package fridefors_peng.linearpg.objects

/**
 * A timer is exactly like a stopwatch. It counts the time, and has an accessor for it.
 * You can also stop, reset, resume, pause and see if it is paused.
 */

class Timer(private var paused:Boolean = false, private var ticks:Int = 0) extends GameObject {
	def update(): Unit = {
		if (!paused) ticks += 1
	}

	def time  = ticks
	def stop  {ticks = 0; pause}
	def reset {ticks = 0; resume}
	def pause {paused = true}
	def resume{paused = false}
	def isPaused = paused
}