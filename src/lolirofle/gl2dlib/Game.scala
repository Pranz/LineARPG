package lolirofle.gl2dlib

import org.lwjgl.opengl.Display

trait Game{
	def onUpdate(delta:Int);
	def onRender();
	def onClose();
	def onWindowResize(width:Int,height:Int,fullscreen:Boolean);
	def onKeyEvent(key:Int,state:Boolean);
	def onKeyCharEvent(chr:Char);

	def isCloseRequested():Boolean;
	
	def keyIsDown(key:Int)=GameHandler.keyIsDown(key)

	def title=Display.getTitle;
	def title_=(title:String){Display.setTitle(title);}

	def windowResizable=Display.getTitle;
	def windowResizable_=(resizable:Boolean){Display.setResizable(resizable);}

	def vsync_=(vsync:Boolean){Display.setVSyncEnabled(vsync);}
	
	def FPS=GameHandler.FPS
	def FPS_=(fps:Int){GameHandler.FPS=fps;}
}