package controllers

import play.api._
import Taxi._
import play.api.mvc._

object Application extends Controller {
  
  
  val model: TaxiModel  = new TaxiModel(8,2);
  val controller: TaxiControl= new TaxiControl(model);
  var results: TaxiCostModel = null;
  
  val gui = new SwingMain(controller);
		
		
  //new TaxiTextUi(controller);
  
  def index = Action { request => {
	  
    controller.newRounde(controller.getSize -2,controller.getGuestCount);
    Ok(views.html.index("Welcome to BadTaxi!", controller)).withNewSession;
    
  }
  }
  
  def calculate() = Action {
    results = controller.calculateRoute
    Ok("OK")
  }
  
  def getDist() = Action {
    Ok(results.distance.toString);
  }
  
  def getEarnings() = Action {
    if (results.success == true) {
    	Ok(results.earnings.toString)
    } else {
      Ok("0");
    }
  }
  
  def getInfo() = Action {
    if (results.success == true) {
      Ok("Ziel erreicht!")
    } else {
      Ok("Ziel verfehlt!")
    }
  }
  
  def updateField(x: String, y:String) = Action  {
    val x1 = x.toInt;
    val y1 = y.toInt;
    
    controller.rotate(x1, y1);
    val streetname:String = getImageName(x1,y1);
    Ok(streetname.toString)
    
  }
  
  def getImage(x: String, y:String) = Action  {
    val x1 = x.toInt;
    val y1 = y.toInt;
    val streetname = getImageName(x1,y1);
    Ok(streetname.toString())
  }
  
  
  // *********APP*************
  
  def getImageName(X: Int, Y:Int): String = {
    val street = controller.getField(X, Y);
    val list:List[Int] = street.getEndpointList;
      var sb: StringBuilder = new StringBuilder();
      list.addString(sb);
      var imgPath = "";
    if (street.getEndpointList.length > 2) {
      // 2 stra�en enthalten
      val t : String = sb.toString.substring(0, 2);
      ////println("match " + t)
    	t match {
    	  case "31" | "13" => imgPath = "2_02"; 
    	  case "03" | "30" => imgPath = "2_03"; 
    	  case "02" | "20" => imgPath = "2_02"; 
    	  case "21" | "12" => imgPath = "2_03";
    	  case "32" | "23" | "01" | "10" =>  imgPath = "2_01";
    	}
    } else {
      // 1 stra�e enthalten
      val t : String = sb.toString;
      //println("match " + t)
    	t match {
		    case "31" | "13" => imgPath = "1_13";
		    case "30" | "03" => imgPath = "1_03";
		    case "02" | "20" => imgPath = "1_02";
		    case "21" | "12" => imgPath = "1_12";
		    case "32" | "23" => imgPath = "1_32";
		    case "01" | "10" => imgPath = "1_01";
		    case _ => imgPath = "blank";
    	}
    }
    imgPath = (imgPath+".png")
    
    return imgPath
  }
  
}