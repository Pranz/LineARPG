package lolirofle.gl2dlib.geom

import lolirofle.gl2dlib.graphics.Drawable
import lolirofle.gl2dlib.gl.GLDraw
import lolirofle.gl2dlib.data.Position
import lolirofle.gl2dlib.data.TwoDimensionalAxis

case class PositionedShape[T<:Shape](val shape:T,var x:Float,var y:Float) extends Drawable{
	def colliding[S<:Shape](other:PositionedShape[S]):Boolean=shape match{
		case s:Tetragon=>other.shape match{
			case o:Tetragon=>
				x+shape.width  >= other.x &&
				x              <= other.x+o.width &&
				y+shape.height >= other.y &&
				y              <= other.y+o.height;
			case Point=>
				x+shape.width  >= other.x &&
				x              <= other.x &&
				y+shape.height >= other.y &&
				y              <= other.y;
			case _=>false
		}
		case s:Circle=>other.shape match{
			case o:Circle=>math.hypot(x-other.x,y-other.y) <= s.radius+o.radius
			case Point=>math.hypot(x-other.x,y-other.y) <= s.radius
			case _=>false
		}
		/*case Point => other.shape match{
			case Point=>this.x==other.x&&this.y==other.y
			case _=>false
		}*/
		case _=>false
	}
	
	def intersects[S<:Shape](other:PositionedShape[S]):Boolean=shape match{
		case s:Polygon=>other.shape match{
			case o:Polygon=>{
				val verticeCount=s.vertices.length
				val otherVerticeCount=o.vertices.length
				
				var x1=s.vertices(verticeCount-1).x+this.x;
				var y1=s.vertices(verticeCount-1).y+this.y;
				var x2=0f;
				var y2=0f;
				
				var x1Other=o.vertices(otherVerticeCount-1).x+other.x;
				var y1Other=o.vertices(otherVerticeCount-1).y+other.y;
				var x2Other=0f;
				var y2Other=0f;
				
				for(i <- 0 until verticeCount){
					x2=x
					y2=y
					x1=s.vertices(i).x+this.x
					y1=s.vertices(i).y+this.y
					
					for(j <- 0 until otherVerticeCount){						
						x2Other=x1Other
						y2Other=y1Other
						x2Other=o.vertices(j).x+other.x;
						y2Other=o.vertices(j).y+other.y;
						
						val u:Double=(((x2-x1)*(y1Other-y1).toDouble)-((y2-y1)*(x1Other-x1)))/
								(((y2-y1)*(x2Other-x1Other))-((x2-x1)*(y2Other-y1Other)));
						val otherU:Double=(((x2Other-x1Other)*(y1Other-y1).toDouble)-
								((y2Other-y1Other)*(x1Other-x1)))/
								(((y2-y1)*(x2Other-x1Other))-
								((x2-x1)*(y2Other-y1Other)));
		
						if(u>=0&&u<=1&&otherU>=0&&otherU<=1){
							return true;
						}
					}
				}
				return false;
			}
				
			case _=>false
		}		
		case _=>false
	}
	
	override def draw(){
		draw(false);
	}
	
	def draw(filled:Boolean){
		shape.draw(x,y,filled);
	}
	
	def width=shape.width
	def height=shape.height
	
	def flipped(axis:TwoDimensionalAxis){}
	def rotated(angle:Float){}
}
