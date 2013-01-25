package Taxi

import java.io._
import java.io.PrintWriter
import java.io.OutputStreamWriter
import java.util.Scanner
import scala.collection.mutable.StringBuilder
import scala.swing._
import scala.swing.event._;

class TaxiTextUi (controller:TaxiControl) extends Publisher{
	
  println("baue TextUI")
  var in:Scanner = new Scanner(System.in);
  var out:PrintWriter = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8") );
  var posX = 0;
  var posY = 0;
  var readin:String = "";
  
  var console:Array[Array[String]] = Array.fill(controller.getSize+2,controller.getSize+2)("   ");
  // inital drawing
  drawConsole(out);
  
  reactions += {
    case RotateEvent => drawConsole(out);//println("rotateevent");
    //case RunEvent => //println("runevent");
    case NewRound => drawConsole(out);
  }
  // read console
  while ( {readin = in.nextLine(); !readin.equals("exit")} )
  {
    //if (readin.equals("exit"))
      
    readin.substring(0, 1) match
    {
      case "R" => 
        	out.write("drehen..");
        	  println(readin.substring(1,2))
        	  println(readin.substring(2, 3))
        	  
        	controller.rotate(readin.substring(1,2).toInt -1, readin.substring(2, 3).toInt -1);
      case "S" => out.write ("Starte Taxifahrt");
      				val cost:TaxiCostModel = controller.calculateRoute;
      				println("Gefahrene Distanz: " + cost.distance +"\n\rYou earned " + cost.earnings);
      				if (cost.success == true) {
      				  println("Level erfolgreich!")
      				  controller.newRounde(controller.getSize -2, controller.getGuestCount);
      				} else println("Ziel nicht gefunden!");
      case "N" => controller.newRounde(controller.getSize-2, controller.getGuestCount);
      case _ => out.write(readin);
    }
    // drawing new state
    drawConsole(out);
    out.flush();
  }
  
  // exit
  out.write("\n\r")
  private def drawConsole(out: PrintWriter){
    for (y:Int <- (-1) to controller.getSize -1) {
      for (x:Int <-(-1) to controller.getSize -1) {
        //console(x)(y) = this.getCode(controller.getField(x, y));

        if (x > (-1) && x < controller.getSize)
        {
          if (y > (-1) && y< controller.getSize) {
            //println("X: " + x + ";; Y: " + y)
            val bytedata: Seq[Byte] = this.getCode(controller.getField(x,y));
        	//out.write(this.getCode(controller.getField(x, y))+ " ");
            val charset: java.nio.charset.Charset = java.nio.charset.Charset.forName( "UTF-8" );
            var sdata: java.lang.String = new java.lang.String(bytedata.toArray[Byte], charset);
            if (controller.getField(x, y).isGuest == false)
            {
	            if (controller.getField(x, y).getEndpointList.length > 2)
	              { sdata += " "; }
	            else
	            { sdata += "  "; }
            } else {
              if (controller.getField(x, y).getEndpointList.length > 2)
	              { sdata += "G"; }
	            else
	            { sdata += "G "; }
            
              
            }
            if (controller.getField(x, y).isStart == true && controller.getField(x, y).isBorder == true) {
              sdata = "S" + sdata;
            }else {
              sdata += "";
            
            }
            if (controller.getField(x, y).isEnd == true && controller.getField(x, y).isBorder == true) {
              sdata = "E"+sdata;
            }else {
              sdata += "";
            
            }
            out.write(sdata);
          }
        }
        
        
         if ( x == -1 && y == -1)
           out.write("   ");
         if (x == -1 && y != -1) {
            out.write ((y +1) +"  ");
          }
          if (y == -1 && x != controller.getSize -1)
            out.write((x+2) + "  ");
        
        
      }
      out.write("\n\r");
        out.flush();
    }
    
      
  }
  
  def getCode(street: StreetElement): Seq[Byte] = {
    val list:List[Int] = street.getEndpointList;
      var sb: StringBuilder = new StringBuilder();
      list.addString(sb);
      var b: Byte = 0x00;
      
    if (street.getEndpointList.length > 2) {
      // 2 Tupels;
      
      
      val t : String = sb.toString.substring(0, 2);
    	t match {
    	  case "31" | "13" => return Seq(0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0x97.asInstanceOf[Byte],0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0x9A.asInstanceOf[Byte]);
    	  case "03" | "30" => return Seq(0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0x9D.asInstanceOf[Byte],0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0x94.asInstanceOf[Byte]);
    	  case "02" | "20" => return Seq(0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0x97.asInstanceOf[Byte],0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0x9A.asInstanceOf[Byte]);
    	  case "21" | "12" => return Seq(0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0x9D.asInstanceOf[Byte],0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0x94.asInstanceOf[Byte]);
    	  case "32" | "23" | "01" | "10" => return Seq(0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0xAC.asInstanceOf[Byte],0x20.asInstanceOf[Byte]);
    	}
      
    } else {
      
      val t : String = sb.toString;
    	t match {
		    case "31" | "13" => return Seq(0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0x97.asInstanceOf[Byte]);
		    case "30" | "03" => return Seq(0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0x9D.asInstanceOf[Byte]);
		    case "02" | "20" => return Seq(0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0x9A.asInstanceOf[Byte]);
		    case "21" | "12" => return Seq(0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0x94.asInstanceOf[Byte]);
		    case "32" | "23" => return Seq(0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0x90.asInstanceOf[Byte]);
		    case "01" | "10" => return Seq(0xe2.asInstanceOf[Byte],0x95.asInstanceOf[Byte],0x91.asInstanceOf[Byte]);
		    case _ => return Seq(0x46.asInstanceOf[Byte], 0x46.asInstanceOf[Byte]);;
    	}
    }
    
      
     
  }
  /*
  def getCode(streetCode: Int, line: Int) : String  = streetCode match {
      	/*case 31 => Set(9556, 9565, 160);
    	case 30 => Set(9556, 160, 160);
    	case _ => Set(160);
    	* */
    case 31 => "\\";
    case 30 => "/";
    case 32 => "-";
    case 3 => "/";
    case 2 => "\\";
    case 1 => "|";
    case 20 => "\\";
    case 23 => "-";
    case 21 => "/";
    case 6 => "+";
      
    	
    	
    	/*
    	 * kurve links2oben = 9565
    	 * kurve unten2rechts = 9556
    	 * kurve oben2rechts = 9562
    	 * kurve links2unten = 9559
    	 * links2rechts = 9552
    	 * oben2unten = 9553
    	 * kreuzung = 9580
    	 */
  }
  * */
  
  
  
}