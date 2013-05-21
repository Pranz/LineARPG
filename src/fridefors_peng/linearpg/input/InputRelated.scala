package fridefors_peng.linearpg.input

trait InputRelated{
	def keyIsDown(key:ControlKey):Boolean
	def keyIsDown(key:Int):Boolean
	def onKeyEvent(key:Int,state:Boolean)
	def onKeyCharEvent(chr:Char)
	
	def close()
	def reset()
}