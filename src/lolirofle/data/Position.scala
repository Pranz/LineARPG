package lolirofle.data

case class Position(val x:Float,val y:Float){
	def +(v:Vector)=Position(this.x+v.x,this.y+v.y)
	def -(v:Vector)=Position(this.x-v.x,this.y-v.y)
	def -(pos:Position)=Vector(this.x-pos.x,this.y-pos.y)
	def distanceTo(other:Position)=math.hypot(other.x-this.x,other.y-this.y)
	def rightBelow = Position(x, y+1)
	final def toTuple = (x,y)
	def withX(x:Float)=Position(x,y)
	def withY(y:Float)=Position(x,y)
	override def toString="Position("+x.toString+','+y.toString+')'
}