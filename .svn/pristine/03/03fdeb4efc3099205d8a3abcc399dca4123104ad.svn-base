package Taxi

trait Model {
  def newGame(size:Int, guests:Int);
  def getFields() : Array[Array[StreetElement]];
  def getSize() : Int;
  def getStreetElement(x:Int, y:Int): StreetElement
  var hasGuest: Int;
  var fieldsize: Int;
  var guestSize: Int;
   
  
}

class TaxiModel(size:Int, guests:Int) extends Model {
	// modell beinhaltet eine NxN Matrix aus boolschen Werten, die angeben ob zwischen den jeweiligen Stra�enelementen ein Durchkommen m�glich ist
  var i =0;
  
  var fieldsize = size+2;
  var guestSize = guests;
  var hasGuest: Int = 0;
   var fields:Array[Array[StreetElement]] = Array.fill(fieldsize, fieldsize)(new StreetElement(i,false,this));
   
   var r = new scala.util.Random();

   newGame(size, guests);
	
	def newGame(size:Int, guests:Int) {
	  this.fieldsize = size +2;
	  this.guestSize = guests;
	  fields = Array.fill(fieldsize, fieldsize)(new StreetElement(i,false,this));
	  var x: Int = 0;
	  var y:Int = 0;
	  hasGuest = 0;
	  StreetElement.endElement = null;
	  StreetElement.startElement = null;
	  StreetElement.hasStart = false;
	  StreetElement.hasEnd = false;
	  StreetElement.objCount = 0;
	  StreetElement.startID = r.nextInt((this.getSize*4-5))+1;
	  StreetElement.endID = r.nextInt((this.getSize*4-5))+1;
	  for {
	    y <- fields.indices;
	    x <- fields(y).indices
	  } {
	    if (x == 0 || y == 0 || x == fieldsize-1 || y == fieldsize-1) {
	    	fields(y)(x) = new StreetElement(i,true,this,x,y);
	    } else {
	      fields(y)(x) = new StreetElement(i,false, this,x,y);
	    }
	  } 
	  
	  /*
	  fields = Array.fill(fieldsize,fieldsize)( { 
		  
		  if ((x == 0 || y == 0) || (x == fieldsize || y == fieldsize) ) {
		    new StreetElement(i,true);
		  } else {
		    new StreetElement(i,false);
		  }
		  
	  } );
	  * 
	  */
		// define x GUestfields
		for (i <- 1 to this.guests)
		{
		  
		  var ax:Int =  (r.nextInt(this.size)+1);
		  var ay:Int =  (r.nextInt(this.size)+1)
		  fields(ay)(ax).isGuest = true;
		}
		
	}
	
	def getFields() : Array[Array[StreetElement]] = {
		  fields;
		}
	
	def getSize : Int = {
	  fieldsize;
	}
	/**
	 * Man �bergibt den Ausgang des aktuellen Elements
	 * R�ckgabe ist false, wenn das n�chste Element keinen �bergang zum aktuellen Element hat
	 * R�ckgabe ist true, wenn Element �bergang hat
	 */
//	def hastStartPoint(street:StreetElement, lastPoint:Int): Boolean = {
//		val ret:List[Int] = street.getEndpointList map (_ + lastPoint);
//		lastPoint match {
//		  // vert.
//		  case 0 | 1 => if(ret.contains(1)) return true else  return false;
//		  case 2 | 3 => if(ret.contains(5)) return true else  return false;
//		}
//	}
	  
	
	/**
	 * �bergibt eine Instanz aus fields zur�ck der Stelle X|Y
	 */
	def getStreetElement(x:Int, y:Int): StreetElement = {
	  /* Demo code, damit keine fehler kommen */
	  //val Street: StreetElement = new StreetElement(1);
	  //Street;
	  fields(y)(x);
	  // ende demo
	}
	
	def defineStart() {
	  
	}
	
	def defineEnd() {
	  
	}

}