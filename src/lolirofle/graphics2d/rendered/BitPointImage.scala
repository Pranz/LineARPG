package lolirofle.graphics2d.rendered

import lolirofle.gl2d.GLGeom
import lolirofle.util.NumUtil
import lolirofle.gl2d.GLDraw
import lolirofle.graphics2d.Drawable

class BitPointImage(val bits:Seq[Boolean],val bitWidth:Short) extends Drawable{
	def this(data:Long,bitWidth:Short)=this(NumUtil.longToBits(data),bitWidth);
	def this(data:Long,bitWidth:Int)=this(NumUtil.longToBits(data),bitWidth.toShort);
	
	def getPoint(x:Short,y:Short=0):Boolean=bits(x+y*bitWidth);
	
	lazy val bitHeight=math.ceil(length.toDouble/bitWidth).toShort
	
	def length=bits.length;
	
	override def width=bitWidth;
	override def height=bitHeight;
	
	override def draw(){
		GLGeom.POINTS.draw{
			for(i <- 0 until bits.length){
				if(bits(i))
					GLDraw.drawPoint(i%bitWidth,(i/bitWidth).toInt);
			}
		}
	};
		
	override def toString:String=bits.view.zipWithIndex.foldLeft(""){case(result,(bool,i))=>{result+(if(i%bitWidth==0)"\n" else if(bool)"1" else "0")}}
		/*var str=""; 
		for(i <- 0 until bits.length){
			if(i%bitsbitWidth==0)
				str+="\n"
			if(bits(i))
				str+="1";
			else
				str+="0";
		}
		return str;*/
}