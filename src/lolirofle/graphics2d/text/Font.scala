package lolirofle.graphics2d.text

trait Font{
	/**
	 * Width of a rendered string
	 *
	 * @param str String
	 * @return Width
	 */
	def widthOf(str:String):Float;
	
	/**
	 * Height of a rendered string
	 *
	 * @param str String
	 * @return Width
	 */
	def heightOf(str:String):Float;
	
	/**
	 * Dimensions of a rendered string
	 *
	 * @param str String
	 * @return Dimensions
	 */
	def dimensionOf(str:String)=lolirofle.data.Position(widthOf(str),heightOf(str))
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
	
	/**
	 * Renders a string
	 * 
	 * @param x x-position
	 * @param y y-position
	 * @param str String to render
	 * @return Width and height of the rendered string
	 */
	def drawString(x:Float,y:Float,str:String)
	
	/**
	 * Renders a string
	 * 
	 * @param pos position
	 * @param str String to render
	 * @return Width and height of the rendered string
	 */
	def drawString(pos:lolirofle.data.Position,str:String):Unit=drawString(pos.x,pos.y,str)
}