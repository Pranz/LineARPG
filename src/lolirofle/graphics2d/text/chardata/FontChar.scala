package lolirofle.graphics2d.text.chardata

trait FontChar{
	def xAdvance:Short;
	def yAdvance:Short;
	def draw(x:Float,y:Float,fontSize:Short);
}