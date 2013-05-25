package lolirofle.graphics2d.text

import lolirofle.data.Direction
import lolirofle.data.Position

trait FormattableFont extends Font{
	//def drawString(x:Float,y:Float,str:String,direction:Direction=Direction.Left,lengthWrap:Float=0)
	
	//TODO: Function that replaces characters with strings
	
	/**
	 * Renders a string while applying formattingFunc before each character
	 * This can be used to apply colours or render markers for example
	 * 
	 * @param formattingFunc Function to apply
	 */
	def drawStringFormatted(x:Float,y:Float,str:String,formattingFunc:Char=>Boolean)//TODO:drawStringFormatted is needed in console for colours and the position marker
}