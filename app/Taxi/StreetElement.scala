package Taxi

object StreetElement {
  var hasStart: Boolean = false;
  var hasEnd: Boolean = false;
  var objCount = 0;
  
  
  var startID = -1;
  var endID = -1;
  var startElement: StreetElement = null;
  var endElement: StreetElement = null;
}

class StreetElement (_count:Int, Border:Boolean, mdl:Model, X:Int, Y:Int){
  
	def this (_count:Int, Border:Boolean, mdl:Model) = this(_count, Border, mdl, 0, 0);
	var x: Int = -1;
	var y: Int = -1;
	var r = new scala.util.Random();
	var r1  = new random();
	var Bordernumber = 0;
	var isTaxi: Boolean = false;
	var count = _count;
	//val street1 = (-1,-1);
	var streetOne:Set[Int] = null; // r1.nextTuple(0,4,Set(-1,-1));
	//val streetTwo = Set[Int];
	var streetTwo:Set[Int] = null; //r1.nextTuple(r.nextInt(2)-1, 4, streetOne);
	var isGuest: Boolean = false;
	var isEnd: Boolean = false;
	var isStart: Boolean = false;
	
	if (Border == true) {
	  streetOne = Set(0,1);
	  streetTwo = Set(2,3);
	  this.x = X;
	  this.y = Y;
	  Bordernumber = StreetElement.objCount;
	  Console.println("Bordernumber: " + Bordernumber);
	  StreetElement.objCount += 1;
	  
	    
	    if (this.Bordernumber == StreetElement.startID) {
	      StreetElement.startElement  = this;
	      StreetElement.hasStart = true;
	      this.isStart = true;
	      //Console.println("start defined.." + this.Bordernumber);
	    }
	 
	    if (this.Bordernumber == StreetElement.endID) {
	      StreetElement.endElement  = this;
	      StreetElement.hasEnd = true;
	      this.isEnd = true;
	      //Console.println("end defined.." + this.Bordernumber);
	    }
	  
	} else {
	  //val street1 = (-1,-1);
		streetOne = r1.nextTuple(0,4,Set(-1,-1));
		//val streetTwo = Set[Int];
		streetTwo = r1.nextTuple(r.nextInt(2)-1, 4, streetOne);
		isGuest = false;
	}
	
	var isBorder: Boolean = Border;
	
	// -------------------------------------------------------------
	



	override def toString() : java.lang.String = {
			var ret: java.lang.String = "no Element";
		if (!streetOne.contains(-1))
		{
			ret = "StreetOne: " + streetOne;
		}
		if (!streetTwo.contains(-1))
		{
			ret += "streetTwo: " + streetTwo;
		}
		ret;
	}


	class random()
	{
		def  nextTuple(min:Int, max:Int, street:Set[Int]): Set[Int] =	  {
				var r = new scala.util.Random();
				var a:Int =  (r.nextInt(max));
				//println(min);
				if (min == -1) {
					return Set((-1),(-1));
				} else {
					while (street contains a)
					{
						a = (r.nextInt(max));
						//println("Element a: " + min + ": " + a);
					}
					var b:Int = a;
					while (a == b || (street contains b))
					{
						b = r.nextInt(max);
						//println("Element b: " + min + ": " + b);
					}
					//println("Elementab: " + min + "= " + a +"  " + b);
					return Set(a, b);
				}
		}
	}

	/**
	 * Liefert eine Liste aller Werte beider Sets zur�ck
	 */
	def getEndpointList() : List[Int] = {
		var list = List.empty[Int] ++ streetOne;

		if (!streetTwo.contains((-1)))
			list = list ++ streetTwo; 
		list;
	}

	def getEndPointofStreet(start:Int) : Int = {
	  	var end:Int = -1;	  
	  	//println("s1: " + (streetOne.toList sum))
	  	//println("s2: " + (streetTwo.toList sum))
	if (streetOne.contains(start))
	{
		end = (streetOne.toList sum) -start;
	}

	if (streetTwo.contains(start))
	{
		end = (streetTwo.toList sum) -start;
	}
	//println("Ende: " + end);
	end;
	}


	/**
	 * Gib den Ausgang des Elements zur�ck
	 * @param start: Eingang der zu �berpr�fenden Stra�e
	 */
	def getNextHop(start:Int) : Set[Int] = {
		// welcher der beiden Tupels ist der RIchtige?

		var end:Int = -1;	  
	if (streetOne.contains(start))
	{
		end = (streetOne.toSeq sum) -start;
	}

	if (streetTwo.contains(start))
	{
		end = (streetTwo.toSeq sum) -start;
	}
	// jetzt hat man den zweiten Wert des richtigen Tupels.
	/*
	 * 0 = nach oben
	 * 1 = nach unten
	 * 2 = nach rechts
	 * 3 = nach links
	 */
	end match {
		// Set(+/-x, +/-Y, Richtung)
	case 0 => Set(0,-1,0);
	case 1 => Set(0,1,1);
	case 2 => Set(1,0,2);
	case 3 => Set(-1,0,3);
	}		  
	
	
	}
	
	
	
	def isAccessible(byDirection:Int): Boolean = {
		val ret:List[Int] = getEndpointList map (_ + byDirection);
		byDirection match {
			// vert.
			case 0 | 1 => if(ret.contains(1)) return true else  return false;
			case 2 | 3 => if(ret.contains(5)) return true else  return false;
		}
	}

	def rotate {
		println(streetOne);
		streetOne = streetOne.map {
		case 0=>2;
		case 1=>3
		case 2=>1
		case 3=>0
		}

		if (!streetTwo.contains(-1)) {
			streetTwo = streetTwo.map {
			case 0=>2;
			case 1=>3
			case 2=>1
			case 3=>0
			case _=>(-1)
			}
		}

		println(streetOne);
	}

}