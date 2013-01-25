package Taxi

import scala.swing
import scala.swing.event._

case object NextStep extends Event {
  var posX = 0;
  var posY = 0;
  var ende:Boolean = false;
}

object RouteFinder {
  var success: Boolean = false;
}

trait Route extends scala.swing.Publisher {
  def calculate : TaxiCostModel;
  def isAccessible(street:StreetElement, lastPoint:Int): Int;
  def route  (newPosX:Int, newPosY:Int, cost:TaxiCostModel, lastendPoint:Int): TaxiCostModel;
  
}

class RouteFinder(model:Model, controller:Control) extends Route {

	var distance=0;
	var hasGuest:Boolean = false;
	val startX=0;
	val startY=0;
	val startD=0;
	
	def calculate : TaxiCostModel={
			// du musst fr das aller erste streetelement (0|0) erst getNextHop ausf�hren
			// um die richtung bestimmen zu k�nnen
			// startID = 0 heit, dass deine Stra�e bei 0 anf�ngt, aber wohin sie geht, wei�t du noch nicht..
			
	 
	  //if (model.getStreetElement(0, 0).getEndpointList.contains(0))
	  var start:Int = -1;
	  var initHop:Set[Int] = Set(-1,-1);
	  if (StreetElement.startElement.x ==0 ) {
	    //println("X=0");
	    start = 2;
	    initHop = Set((1),(0),start);
	  }
	  if (StreetElement.startElement.y ==0 ) {
	    //println("Y=0");
	    start = 1;
	    initHop = Set((0),(1),start);
	  }
	  if (StreetElement.startElement.x == controller.getSize -1) {
	    //println("X=max");
	    start = 3;
	    initHop = Set((-1),(0),start);
	  }
	  if (StreetElement.startElement.y == controller.getSize -1 ) {
	    //println("Y=max");
	    start = 0;
	    initHop = Set((0),(-1),start);
	  }
	  //println(initHop);
	  //println(StreetElement.startElement.x +":: "+StreetElement.startElement.y);
	  var costmodel = new TaxiCostModel();
	  val newPos:List[Int] = StreetElement.startElement.x + initHop.toList(0) :: StreetElement.startElement.y + initHop.toList(1) :: List();
	  //println(newPos);
	  return route(newPos(0),newPos(1),costmodel,start).modifyVals(0, 1);
	  
	  /*
	  if (model.getStreetElement(0, 0).getEndpointList.contains(0))
	  {
	   println("Start gefunden!"); 
	   
	    val endpoint = StreetElement.startElement.getEndPointofStreet(0);
	    var costmodel = new TaxiCostModel();
	    
	    val Pos:List[Int] = 0 :: 0 ::List();
	    val hop:Set[Int] = model.getStreetElement(0, 0).getNextHop(0);
	    val newPos:List[Int] = Pos(0) + hop.toList(0) :: Pos(1) + hop.toList(1) :: List();
	    return route(newPos(0),newPos(1),costmodel,endpoint).modifyVals(0, 1);
	    
	  } else { 
	    
	    	new TaxiCostModel();;
		}
		* */
		
	}
	
	def isAccessible(street:StreetElement, lastPoint:Int): Int = {
			// lastPoint = der Ausgangspunkt der LETZTEN Strae:
			// dadurch lsst sich durch eine summe berprfen, ob die aktuelle Stra�e, street
			// 
		val ret:List[Int] = street.getEndpointList map (_ + lastPoint);
		lastPoint match {
			// vert.
			case 0 | 1 => if(ret.contains(1)) return (1-lastPoint) else  return -1;
			case 2 | 3 => if(ret.contains(5)) return (5-lastPoint) else  return -1;
		}
	}
	
	def route (newPosX:Int, newPosY:Int, cost:TaxiCostModel, lastendPoint:Int): TaxiCostModel = {
	  if (newPosX < controller.getSize && newPosX >= 0)
	  {
	    if (newPosY < controller.getSize && newPosY >= 0) {
	      
	      val street:StreetElement = model.getStreetElement(newPosX, newPosY);
	      if (street.isEnd == true) {
	        cost.success = true;
	        return cost;
	      }
		  var  startPoint = this.isAccessible(street, lastendPoint); 
		  if ( startPoint  > -1 ){
		    val n = NextStep;
		      n.posX = newPosX;
		      n.posY = newPosY;
		      n.ende = false;
		      //println("publish: " + n.posX + "  " + n.posY + "  " + n.ende);
		      publish(n);
		    val hop:Set[Int] =street.getNextHop(startPoint);
		    if (street.isGuest == true) model.hasGuest += 1;
		    route(newPosX + hop.toList(0), newPosY + hop.toList(1),cost.modifyVals(if (model.hasGuest > 0) model.hasGuest else 0, 1),street.getEndPointofStreet(startPoint));
		  } else {
		    val n = NextStep;
		      n.posX = newPosX;
		      n.posY = newPosY;
		      n.ende = true;
		      //println("publish: " + n.posX + "  " + n.posY + "  " + n.ende);
		      publish(n);
		    //println("Come back");
		    return cost;
		  }
	    } else {
	    	return cost;
	    }
	  } else {
	    return cost;
	  }
	  
	  
	      
	}

	def route (curX:Int,curY:Int, direction:Int):Unit={
		
		var current=model.getStreetElement(curX,curY);
		if (current.isAccessible(direction)){
			var vector=current.getNextHop(direction);
			// so macht mans nich... eigentlich
			/* mach lieber 
			 * if ( is accessible)
			 * 	-> return route +1
			 * else
			 * return 0
			 * 
			 * dann z�hlts die distance mit jedem rekursionsschritt selber hoch �ber den returnwert
			 */
			distance=distance+1;
			route(vector.toSeq(0),vector.toSeq(1),vector.toSeq(2));
		}
	}
}