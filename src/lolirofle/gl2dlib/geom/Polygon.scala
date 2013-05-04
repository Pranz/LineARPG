package lolirofle.gl2dlib.geom

import scala.collection.mutable.ArrayBuffer
import lolirofle.gl2dlib.gl.GLGeom
import org.lwjgl.opengl.GL11
import lolirofle.gl2dlib.data.Position

object Polygon{
	def apply(points:Seq[Position])=new Polygon{
		override def sides=vertices.size
		var vertices=points
		override val width=0f
		override val height=0f
		override val perimeter=0f
		override val area=0f
	}
}

trait Polygon extends Shape{
	def sides:Int;
	
	def vertices:Seq[Position];
	
	def draw(x:Float,y:Float,filled:Boolean){
		(if(filled)GLGeom.QUADS else GLGeom.LINE_LOOP).draw{
			vertices.foreach(p=>{
				GL11.glVertex3f(p.x,p.y,0);//TODO: Use GLDraw functions, make one
			});
		};
	}
	
	def angleSum:Float=if(sides<3)0 else (sides-2)*180
}

trait RegularPolygon extends Polygon{
	def sides:Int;
	def side:Float;
	
	def perimeter=side*sides
	def area=
		if(sides==0)0;
		else if(sides==1)side;
		else if(sides==2)side*2;
		else if(sides==3)(side*0.5*math.sqrt(side*side*0.75)).toFloat;
		else if(sides==4)side*side;
		else (side*side*sides)/(4*math.sqrt(3.0)).toFloat;
	
	def angle=angleSum/sides
}
