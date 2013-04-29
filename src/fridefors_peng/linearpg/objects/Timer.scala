package fridefors_peng.linearpg.objects

/**
 * A timer is exactly like a stopwatch. It counts the time, and has an accessor for it.
 * You can also stop, reset, resume, pause and see if it is paused.
 */
class Timer(private var paused:Boolean = false, private var milliseconds:Int = 0) extends GameObject {
	def update(delta:Int): Unit = {
		if(!paused)milliseconds+=delta
	}

	def time=milliseconds
	def stop  {reset;pause}
	def reset {milliseconds=0;}
	def pause {paused=true}
	def resume{paused=false}
	def isPaused=paused
}