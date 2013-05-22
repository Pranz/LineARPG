package lolirofle.graphics2d.text

trait Font{
	def widthOf(str:String):Float;
	def heightOf(str:String):Float;
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
	//def drawString(x:Float,y:Float,str:String,direction:Directional=LEFT,lengthWrap:Float=0);
	/**
	 * @param formattingFunc Applies function to each char in str. This can be used to replace char with strings and apply colours in the function for example
	 */
	//def drawStringFormatted(x:Float,y:Float,str:String,formattingFunc:Char=>String)//TODO:drawStringFormatted is needed in console
}