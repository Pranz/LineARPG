package fridefors_peng.linearpg.objects

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