package lolirofle.gl2dlib.gl

import org.lwjgl.opengl.GL11
import lolirofle.gl2dlib.graphics.Color
import lolirofle.gl2dlib.graphics.text.DefaultFont
import lolirofle.gl2dlib.graphics.text.Font

object GLDraw{
	def getCircleSegments(radius:Float):Int=(10*math.sqrt(radius)).toInt
	
	def drawCircle(x:Float,y:Float,radius:Float,filled:Boolean=false){drawCircle(x,y,radius,getCircleSegments(radius),filled);}
	def drawCircle(x:Float,y:Float,radius:Float,segments:Int,filled:Boolean){ 
		//Precalculated sin and cos values
		val theta:Float=(2f*math.Pi/segments).toFloat; 
		val cos=math.cos(theta).toFloat;
		val sin=math.sin(theta).toFloat;
	
		var circleX:Float=radius;//Start at angle=0 
		var circleY:Float=0; 
	    
		(if(filled)GLGeom.POLYGON else GLGeom.LINE_LOOP).draw{
			for(i <- 0 to segments){
				GL11.glVertex2f(x+circleX,y+circleY); 

				//Apply the rotation matrix
				val _circleX=circleX;
				circleX=cos*circleX-sin*circleY;
				circleY=sin*_circleX+cos*circleY;
			}
		};
	}
	
	def drawPoint(x:Float,y:Float){
		GLGeom.POINTS.draw{
			GL11.glVertex3f(x,y,0);
		};
	}
	
	def drawLine(x1:Float,y1:Float,x2:Float,y2:Float){
		GLGeom.LINES.draw{
			GL11.glVertex3f(x1,y1,0);
			GL11.glVertex3f(x2,y2,0);
		};
	}
	
	def drawRectangle(x1:Float,y1:Float,x2:Float,y2:Float,filled:Boolean=false){
		(if(filled)GLGeom.QUADS else GLGeom.LINE_LOOP).draw{
			GL11.glVertex3f(x1,y1,0);
			GL11.glVertex3f(x2,y1,0);
			GL11.glVertex3f(x2,y2,0);
			GL11.glVertex3f(x1,y2,0);
		};
	}
	
	private var _xOffset:Float=0;
	private var _yOffset:Float=0;
	private var _xScale:Float=1;
	private var _yScale:Float=1;
	private var _rotation:Float=0;
	private var _color:Color=Color.WHITE;

	def xOffset=_xOffset;
	def yOffset=_yOffset;
	def xScale=_xScale;
	def yScale=_yScale;
	def rotation=_rotation;
	def lineWidth=GL11.glGetFloat(GL11.GL_LINE_WIDTH);
	def color=_color
	var font:Font=DefaultFont.font

	/**
	 * @param rotation 0-360 degrees
	 */
	def rotation_=(rotation:Float)={
		_rotation=rotation;
		GL11.glRotatef(rotation,0f,0f,1f);
	}
	
	def offset_(x:Float,y:Float)={
		_xOffset=x;
		_yOffset=y;
		GL11.glTranslatef(x,y,0);
	}
	
	def xOffset_=(x:Float)={
		_xOffset=x;
		GL11.glTranslatef(x,_yOffset,0);
	}
	
	def yOffset_=(y:Float)={
		_yOffset=y;
		GL11.glTranslatef(_xOffset,y,0);
	}
	
	def offset_=(x:Float,y:Float)={
		_xOffset=x;
		_yOffset=y;
		GL11.glTranslatef(x,y,0);
	}
	
	def xScale_=(xScale:Float)={
		_xScale=xScale;
		GL11.glScalef(xScale,_yScale,0);
	}
	
	def yScale_=(yScale:Float)={
		_yScale=yScale;
		GL11.glScalef(_xScale,yScale,0);
	}
	
	def scale_=(xScale:Float,yScale:Float)={
		_xScale=xScale;
		_yScale=yScale;
		GL11.glScalef(xScale,yScale,0);
	}
	
	def lineWidth_=(width:Float)={
		GL11.glLineWidth(width);
	}
	
	def color_=(color:Color){
		_color=color;
		GL11.glColor4ub(color.red,color.blue,color.green,color.alpha)
	}
}