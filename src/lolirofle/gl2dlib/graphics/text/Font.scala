package lolirofle.gl2dlib.graphics.text

trait Font{
	def getWidthOf(str:String):Int;
	def getHeightOf(str:String):Int;
	def lineHeight:Int;
	
	/**
	 * Font name
	 * @return Font name in string
	 */
	def name:String;
	
	/**
	 * Font size in pixels, often indicates the height of the font. E.g. 14px
	 * @return Font size in pixels
	 */
	def size:Short;
	def getCharsAllocated:Int;
	def drawString(x:Float,y:Float,str:String);
	//def drawString(g:Renderer,x:Float,y:Float,str:String,direction:Directional=LEFT,lengthWrap:Float=0);
	//def drawStringFormatted(g:Renderer,x:Float,y:Float,str:String);
}