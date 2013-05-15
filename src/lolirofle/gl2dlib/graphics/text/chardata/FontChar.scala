package lolirofle.gl2dlib.graphics.text.chardata

trait FontChar{
	def xAdvance:Short;
	def yAdvance:Short;
	def draw(x:Float,y:Float,fontSize:Short);
}