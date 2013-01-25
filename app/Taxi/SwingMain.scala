package Taxi

import scala.swing._
import scala.swing.event._;

import scala.io.Position
import java.awt.Font
import javax.swing.Timer
import java.util.Timer
import Swing._
import scala.io.Source
import javax.swing._

case object NextTimerStep extends Event {
  var posX = 0;
  var posY = 0;
}

class SwingMain(controller: Control) extends Frame{
  listenTo(controller);
  title = "Bad Taxi"
  val framewidth = 640
  val frameheight = 480
  val screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize()
  location = new java.awt.Point()
  val tsize = controller.getSize();
  var tcm:TaxiCostModel = null;
  
  // labels to render
  var lbldist:Label = null;
  var lblearn:Label = null
  var lblinfo:Label = null;
  
  var stepList:List[Seq[Int]] = Nil;
  var stepCount:Int = 0;
  val timerlistener = new java.awt.event.ActionListener() {
			def actionPerformed(evt: java.awt.event.ActionEvent) {
				// code
			  ////println("steplistlength: " + stepList.length)
			  if (stepCount < stepList.length) {
			  val item = stepList(stepCount);
			  //println("item: " + item);
			  val l = item.toList
			  val x = l(0);
			  val y = l(1);

			  if (stepCount > 0) {
				  val item2 = stepList(stepCount-1);
			  
				  val l0 = item2.toList
				  val x0 = l0(0);
				  val y0 = l0(1);
				  controller.getField(x0, y0).isTaxi = false;
				  //gridPanel(x0)(y0).repaint();
			  }
			  /* 
			  val nts = NextTimerStep;
			  nts.posX = x
			  nts.posY = y
			  //println("publish nts");
			  publish(nts);
			  * */
			  controller.getField(x,y).isTaxi = true;
			  
			  
			  
			  stepCount += 1;
			  }  else {
			    timer.stop();
			  }
			}
		}
  val timer:javax.swing.Timer = new javax.swing.Timer(400, timerlistener)
  timer.stop();
  listenTo(controller.routefinder);
  var bPanel:BorderPanel =  new BorderPanel();
	  	
		// add(gridPanel, BorderPanel.Position.West)
	//	add(gridbagPanel, BorderPanel.Position.East)
  //}
  /*
  var playPanel:GridPanel = gridPanel;
  var ctrlPanel:GridBagPanel = gridbagPanel;
  val l = (playPanel, BorderPanel.Position.West)
  bPanel.layout += l;
  val l2 = (ctrlPanel, BorderPanel.Position.East)
  bPanel.layout += l2
  lblinfo = new Label("Finde einen Weg von Start zum Ziel und nimm dabei alle Fahrg�ste mit.");
  val l3 = (lblinfo, BorderPanel.Position.South);
  bPanel.layout += l3
  
  contents = bPanel;
  */
  drawAll;
  
  reactions += {
    case RotateEvent => //println("rotateevent");
    //case RunEvent => //println("runevent");
    case NewRound => drawAll
    /*case RunEvent => {
          val t:TaxiCostModel = RunEvent.tcm;
          	lbldist.text = t.distance.toString;
          	lblearn.text = t.earnings.toString
          	lblinfo.text = { if (t.success == true) "Ziel gefunden" else "Ziel verfehlt" } 
        }*/
    case NextStep => {
    		//println("recieve nextstep" + NextStep.posY + " " + NextStep.posX);
    		if (timer.isRunning() == false) timer.start();
	        val s:Seq[Int] = Seq(NextStep.posX, NextStep.posY);
	        //println(s);
	        var stepList2: List[Seq[Int]] = Nil;
	        stepList2 = stepList ::: List(s);
	        stepList = stepList2;
	        //println(stepList);
	        //if (NextStep.ende == true) timer.stop();
	        
	   }
  }
  
  def drawAll() {
    tcm = null;
  bPanel.layout.clear
  var playPanel:GridPanel = gridPanel;
  var ctrlPanel:GridBagPanel = gridbagPanel;
  val l = (playPanel, BorderPanel.Position.West)
  bPanel.layout += l;
  val l2 = (ctrlPanel, BorderPanel.Position.East)
  bPanel.layout += l2
  lblinfo = new Label("Finde einen Weg von Start zum Ziel und nimm dabei alle Fahrg�ste mit.");
  
  val l3 = (lblinfo, BorderPanel.Position.South);
  bPanel.layout += l3
  contents = bPanel;
    /* contents = new BorderPanel {
		add(gridPanel, BorderPanel.Position.West)
		add(gridbagPanel, BorderPanel.Position.East)
		
		lblinfo = new Label("Finde einen Weg von Start zum Ziel und nimm dabei alle Fahrg�ste mit.");
		add(lblinfo, BorderPanel.Position.South);
    }
    * */
    
  }
  

  
  def gridbagPanel = new GridBagPanel {
    val gbc = new Constraints();
    gbc.gridx = 0
    gbc.gridy = 0
    add(new Label("Gefahrene Distanz"), gbc)
    gbc.gridx = 1
    lbldist = new Label("0");
    add (lbldist, gbc);
    gbc.gridx = 0
    gbc.gridy = 1
    add(new Label("Lohn"),gbc);
    gbc.gridx = 1
    lblearn = new Label("0.00");
    
    add(lblearn,gbc)
    
    gbc.gridx = 0
    gbc.gridy = 2
    add(new Button("Fahre los") {
      listenTo(mouse.clicks)
      reactions += {
        case e: MouseClicked => 
          
          tcm = controller.calculateRoute;
          lbldist.text = tcm.distance.toString;
          lblearn.text = tcm.earnings.toString
          lblinfo.text = { if (tcm.success == true) "Ziel gefunden" else "Ziel verfehlt" } 
          
      }
    }, gbc);
    gbc.gridx = 1
    gbc.gridy = 2
    add(new Button("Neue Runde") {
      listenTo(mouse.clicks)
      reactions += {
        case e: MouseClicked => {
          
          controller.newRounde(controller.getSize -2, controller.getGuestCount);
        }
      }
    }, gbc)
    
  }
  
  def gridPanel = new GridPanel(tsize,tsize) {
    contents.clear;
    border = LineBorder(java.awt.Color.BLACK, 30)
    background = java.awt.Color.WHITE
    //icon = new ImageIcon("./grafiken/blank.png");
    //add(new Button { title = "test"}, BorderPanel.Position.Center)
    for (y <- (-1) to controller.getSize -1) {
	    for (x <- (-1) to controller.getSize -1) {
	      if (x > (-1) && x < controller.getSize)
	        {
	          if (y > (-1) && y< controller.getSize) {
	            val streeticon:ImageIcon = getImage(controller.getField(x,y));
	      //val panel = 
	      
	      contents += new TaxiLabel() {
	        listenTo(mouse.clicks)
	        listenTo(controller);
	        //val element:StreetElement = controller.getField(x, y);
	        Position.setX(x)
	        Position.setY(y);
	        text = ""
	        visible = true
	        icon = streeticon
	        isGuest = controller.getField(x, y).isGuest
	        isBorder = controller.getField(x, y).isBorder
	        isStart = controller.getField(x, y).isStart
	        isEnd = controller.getField(x, y).isEnd
	        //imagePath = getImage(controller.getField(x,y))
	        //foreground = java.awt.Color.RED
	        
	        reactions += {
	          case RotateEvent => {
	            if (RotateEvent.X == Position.getX && RotateEvent.Y == Position.getY) {
	              icon = getImage(controller.getField(Position.getX, Position.getY))
	            }
	          }
	            case e: MouseClicked =>  {
	            	//println("Mouse clicked at " + e.point)
	            	controller.rotate(Position.getX, Position.getY);
	            	icon = getImage(controller.getField(Position.getX, Position.getY))
	        		}
	            case FieldChange => {
	              if (timer.isRunning()) {
	              if (FieldChange.X == Position.getX && FieldChange.Y == Position.getY) {
	                isTaxi = true;
	              } else {
	                isTaxi = false;
	              }
	              self.repaint();
	            }
	          }
	        }
	        }
	      	};
	          
	        }
	      
	      
	    }
    }
   // ende contents = new gridpanel
  }
  
  
  
  
  
  def getImage(street:StreetElement): ImageIcon = {
    var ic:ImageIcon = null;
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
    imgPath = (".\\public\\images\\"+imgPath+".png")
    ic = new ImageIcon(imgPath);
    return ic;
  }
  
  
  
  
  //location = new java.awt.Point((screenSize.width - framewidth) / 2, (screenSize.height - frameheight) / 2)
  
  this.open()
  
  
  

}