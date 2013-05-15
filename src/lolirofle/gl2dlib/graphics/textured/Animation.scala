package lolirofle.gl2dlib.graphics.textured;

import lolirofle.gl2dlib.util.NumUtil
/*
//TODO: Make this a "trait" or something like that and have a ImageAnimation class
class Animation(val images:Seq[Image],var speed:Float=1,var order:AnimationOrder=AnimationOrder.NORMAL,var end:AnimationEnd=AnimationEnd.LOOP){
	private var updatable=true;
	var i:Float=0;
	lazy val beginIndex=NumUtil.limit(order.getIndexOf(0,size),0,size);
	def index=if(i==0)beginIndex else NumUtil.limit(order.getIndexOf(i,size),0,size);
	def iteration=i/order.iterationEnd(size);//TODO: Rename function
	def size=5//images.size;
	
	def next:Unit={
		i+=speed
	}
	
	def previous:Unit={
		i-=speed
	}
	def pause:Unit={
		updatable=false
	}
	def stop:Unit={
		pause;
		i=0;
	}
	def play:Unit={
		updatable=true
	}
	
	def update(){
		if(updatable){
			next;
			if(i+speed>=order.iterationEnd(size))
				end.endAction(this);
		}
	}
}

trait AnimationOrder{
	def getIndexOf(iteration:Float,size:Int):Int;
	def iterationEnd(size:Int):Float;
}

object AnimationOrder{
	case object NORMAL extends AnimationOrder{
		override def getIndexOf(iteration:Float,size:Int):Int=iteration.toInt
		override def iterationEnd(size:Int)=size;
	}
	case class REVERSED(order:AnimationOrder) extends AnimationOrder{
		override def getIndexOf(iteration:Float,size:Int):Int=size-1-order.getIndexOf(iteration,size)
		override def iterationEnd(size:Int)=order.iterationEnd(size)
	}
	case object PINGPONG extends AnimationOrder{
		override def getIndexOf(iteration:Float,size:Int):Int=if(iteration<size)iteration.toInt else size-2-(iteration.toInt-size)
		override def iterationEnd(size:Int)=size*2-2
	}
}

trait AnimationEnd{
	def endAction(a:Animation);
}

object AnimationEnd{
	case object STOP extends AnimationEnd{
		override def endAction(a:Animation){
			a.stop;
		}
	}
	
	case object LOOP extends AnimationEnd{
		override def endAction(a:Animation){
			a.i=NumUtil.mod(a.i,a.order.iterationEnd(a.size))
		}
	}
}
*/
