package Taxi

import scala.swing
import scala.swing.event._

case object RotateEvent extends Event {
  var X:Int = -1;
  var Y:Int = -1;
}
case object RunEvent extends Event {
  var tcm:TaxiCostModel = null;
}
case object NewRound extends Event
case object FieldChange extends Event {
  var X:Int = 0;
  var Y:Int = 0;
}

trait Control extends scala.swing.Publisher {
  var routefinder : RouteFinder;
  def rotate(x: Int, y: Int);
  def getSize() :Int;
  def calculateRoute : TaxiCostModel;
  def getField(x:Int, y:Int) : StreetElement;
  def newRounde(size:Int, guests:Int)
  def getGuestCount : Int;
}

trait View {
  
}



class TaxiControl(btm: Model) extends Control
{
  var routefinder = new RouteFinder(btm, this);
//  var mybtm = btm;
  

  def rotate (x:Int, y:Int)
  {
    btm.getStreetElement(x,y).rotate;
    val r = RotateEvent;
    r.X = x;
    r.Y = y;
    publish(r);
  }
  
  def getSize : Int =  {
    btm.getSize;
  }
  
  def getGuestCount : Int = {
    btm.guestSize;
  }
  
  def calculateRoute : TaxiCostModel ={
  	val r:TaxiCostModel =  routefinder.calculate;
  	if (r.success == false) r.earnings = 0;
  	val revent = RunEvent;
  	revent.tcm = r;
  	publish(revent);
  	return r;
  }
  
  def getField(x:Int, y:Int) : StreetElement = {
    val n = FieldChange;
    n.X = x;
    n.Y = y;
    publish(n);
    btm.getStreetElement(x, y);
    
  }
  
  def newRounde(size: Int, guests: Int) = {
   btm.newGame(size, guests); 
   publish(NewRound)
  }
  
  
  
}