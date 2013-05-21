package fridefors_peng.linearpg.objects

trait GameObject{
	private var _destroyed = false
	def destroyed=_destroyed

	def onDestroy{}
	
	final def destroy(){
		if (!_destroyed)
			_destroyed=true
			onDestroy
	} 
}
