package lolirofle.gl2dlib

import org.lwjgl.opengl.Display
import org.lwjgl.opengl.DisplayMode
import org.lwjgl.opengl.GL11
import org.lwjgl.LWJGLException
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard

object GameHandler{
	private var fps:Int=0;
	private var targetFPS:Int=60;
	private var fpsCounter:Int=0;
	private var lastFPSUpdateTime:Long=0
	private var previousFrameTime:Long=0;
	
	private var initiated=false;
	
	var game:Game=null;
	
	def init(game:Game,title:String,displayWidth:Int=640,displayHeight:Int=480,fullscreen:Boolean=false,resizable:Boolean=false){
		if(initiated)return;else initiated=true;
		
		this.game=game;

		val targetDisplayMode=new DisplayMode(displayWidth,displayHeight);
		println("Sets Display Mode: " + targetDisplayMode.getWidth() + " x " + targetDisplayMode.getHeight() + " x "+targetDisplayMode.getBitsPerPixel() + "bit @ "+targetDisplayMode.getFrequency() + "hz");
		Display.setDisplayMode(targetDisplayMode);
		
		if(targetDisplayMode.isFullscreenCapable())
			Display.setFullscreen(fullscreen);
		
		Display.setResizable(resizable);
		Display.setVSyncEnabled(false);
		Display.setTitle(title);
		
		Display.create();
		
		println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
		
		Keyboard.enableRepeatEvents(true);
		Keyboard.create()
	
		gl2dInit();
		
		//Initialise variables
		calcDelta()
		lastFPSUpdateTime=time;
	}

	final def start(){
		while(!Display.isCloseRequested && !game.isCloseRequested){
			//Input
			if(Keyboard.isCreated)
				while(Keyboard.next()){
					if(Keyboard.getEventKeyState&&Keyboard.getEventCharacter!=0)
						game.onKeyCharEvent(Keyboard.getEventCharacter);
					if(!Keyboard.isRepeatEvent)
						game.onKeyEvent(Keyboard.getEventKey(),Keyboard.getEventKeyState());
				}
			
			//Update
			updateFPS();
			game.onUpdate(calcDelta());
			
			//Render
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glPushMatrix();
			game.onRender();
			GL11.glPopMatrix();
			
			Display.update();
			Display.sync(targetFPS);
			
			if(Display.wasResized()){
				val w=Display.getWidth();
				val h=Display.getHeight()
				val f=Display.isFullscreen();
				System.out.println("Application Window Resized: "+w+" x "+h+", Fullscreen: "+f);
				gl2dInitView(w,h);
				game.onWindowResize(w,h,f);
			}
		}
		close();
	}
	
	protected final def close(){
		if(initiated)initiated=false;else return;
		
		game.onClose();
		Keyboard.destroy();
		Display.destroy();
	}
	
	/** 
	 * Calculate how many milliseconds have passed 
	 * since last frame.
	 * 
	 * @return milliseconds passed since last frame 
	 */
	def calcDelta():Int={
	    val time=GameHandler.time;
	    val delta:Int=((time.asInstanceOf[Int])-(previousFrameTime.asInstanceOf[Int]));
	    previousFrameTime=time;
	 
	    return delta;
	}
	
	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	def time=(Sys.getTime()*1000)/Sys.getTimerResolution();
	
	/**
	 * Calculates the FPS
	 */
	def updateFPS(){
		if(time-lastFPSUpdateTime>1000){
			fps=fpsCounter;
			fpsCounter=0;
			lastFPSUpdateTime+=1000;
		}
		fpsCounter+=1;
	}
	
	def gl2dInit(){
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
        
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		gl2dInitView(Display.getWidth,Display.getHeight);
	}
	
	def gl2dInitView(width:Int,height:Int){
		GL11.glViewport(0,0,width,height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0,width,height,0,1,-1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	def FPS=fps
	def FPS_=(fps:Int){
		System.out.println("Sets Target FPS: "+fps);
		targetFPS=fps;
	}
	
	def keyIsDown(key:Int)=Keyboard.isKeyDown(key);
}