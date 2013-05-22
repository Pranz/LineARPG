package fridefors_peng.linearpg.camera

trait Camera{
	def x(viewWidth:Float):Float
	def y(viewHeight:Float):Float
	
	def render(renderFunc:()=>Unit,windowWidth:Float,windowHeight:Float)
}

object NullCamera extends Camera{
	override def x(viewWidth:Float)=0f
	override def y(viewHeight:Float)=0f
	
	override def render(renderFunc:()=>Unit,windowWidth:Float,windowHeight:Float){
		renderFunc()
	}
}