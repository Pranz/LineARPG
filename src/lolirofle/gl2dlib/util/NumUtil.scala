package lolirofle.gl2dlib.util

import scala.collection.mutable.MutableList

object NumUtil {
	def ceilToPowerOfTwo(value:Int):Int={
		var v=1;
		while(v<value){
			v*=2;
		}
		return v;
	}
	
	/**
	 * Round to any number of decimals
	 * 
	 * roundTo(12.345, 3) = 12.345
	 * roundTo(12.345, 2) = 12.35
	 * roundTo(12.345, 1) = 12.3
	 * roundTo(12.345, 0) = 12.0
	 * roundTo(12.345,-1) = 10.0
	 * roundTo(12.345,-2) =  0.0
	 */
	def roundTo(x:Double,decimals:Int):Double={
		if(decimals>0)//Faster with "*0.1" but it is more inaccurate, instead of "/10"
			roundTo(x*10,decimals-1)/10
		else if(decimals<0) 
			roundTo(x/10,decimals+1)*10
		else
			math.round(x)
	}
	
	/**
	 * Ceil to any number of decimals
	 */
	def ceilTo(x:Double,decimals:Int):Double={
		if(decimals>0)//Faster with "*0.1" but it is more inaccurate, instead of "/10"
			ceilTo(x*10,decimals-1)/10
		else if(decimals<0) 
			ceilTo(x/10,decimals+1)*10
		else
			math.ceil(x)
	}
	
	/**
	 * Floor to any number of decimals
	 */
	def floorTo(x:Double,decimals:Int):Double={
		if(decimals>0)//Faster with "*0.1" but it is more inaccurate, instead of "/10"
			floorTo(x*10,decimals-1)/10
		else if(decimals<0) 
			floorTo(x/10,decimals+1)*10
		else
			math.floor(x)
	}
	
	def min(number:Int,numbers:Int*):Int={
		var min=number;
		for(num:Int <- numbers)
			if(num<min)
				min=num;
		return min;
	}

	def max(number:Int,numbers:Int*):Int={
		var max=number;
		for(num:Int <- numbers)
			if(num<max)
				max=num;
		return max;
	}
	
	/**
	 * Modulo, will work with negative numbers and big numbers comparing to the built-in % operation.<br/>
	 * Like (a mod n) or (a % n)
	 * 
	 * @param a
	 * @param n
	 * @return The remainder after division
	 */
	def mod(a:Int,n:Int):Int=(a%n+n)%n;
	def mod(a:Float,n:Float):Float=(a%n+n)%n;//A little inaccurate, "/" is inaccurate too sometimes with floats and doubles

	def rand(min:Double=0,max:Double):Double=math.random*(max-min)+min;
	def rand(max:Double):Double=math.random*max;
	
	def limit(number:Int,min:Int,max:Int):Int={
		if(number<min)
			return min;
		if(number>max)
			return max;
		return number;
	}
	
	def limit(number:Double,min:Double,max:Double):Double={
		if(number<min)
			return min;
		if(number>max)
			return max;
		return number;
	}
	
	def limit(number:Float,min:Float,max:Float):Float={
		if(number<min)
			return min;
		if(number>max)
			return max;
		return number;
	}

	def inRange(number:Int,min:Int,max:Int):Boolean=(number>=min&&number<=max);
	def inRange(number:Float,min:Float,max:Float):Boolean=(number>=min&&number<=max);

/*	def numberBaseConversionString(number:Int,chars:String="0123456789ABCDEF"):String={
		val baseFrom=10;
		val baseTo=chars.size;
		val numberStr=number.toString;
		for(i <- 0 until numberStr.length){
			numberStr(0)
		}
		return "";
	}
*/	
	
	def longToBits(long:Long):Seq[Boolean]={
		val seq=new MutableList[Boolean];
		if(long==0)
			seq+=false;
		else{
			var i:Long=0;
			var pow:Long=1;
			do{
				seq+=(long&pow)==pow;
				i+=1;
				pow=1L<<i;
			}
			while(pow<long)
		}
		return seq;
	}
	
	def towards(value:Float,relativeValue:Float,towardsValue:Float=0):Float={
		if(value>towardsValue){
			if(relativeValue<value)
				return value-relativeValue;
		}
		else
		if(value<towardsValue){
			if(relativeValue>value)
				return value+relativeValue;
		}
		return towardsValue;
	}
}