package lolirofle.gl2dlib.data

case class Position(val x:Float,val y:Float){
	def +(v:Vector)=Position(this.x+v.x,this.y+v.y)
	def -(v:Vector)=Position(this.x-v.x,this.y-v.y)
	def -(pos:Position)=Vector(this.x-pos.x,this.y-pos.y)
	def distanceTo(other:Position)=math.hypot(other.x-this.x,other.y-this.y)
	def toTuple = (x,y)
	override def toString='('+x.toString+','+y.toString+')'
}