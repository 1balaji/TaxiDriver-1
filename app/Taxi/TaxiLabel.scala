package Taxi

import java.awt.image.BufferedImage
import javax.imageio.ImageIO  
import java.io.File     
import java.awt.Graphics2D

class TaxiLabel extends scala.swing.Label {
	self =>
	var posx:Int = -1;
	var posy:Int = -1;
	
	object Position {
	  var x:Int = -1;
	  var y:Int = -1;
	  
	  def getX = x;
	  def getY = y;
	  
	  def setX(_x:Int) {
	    x = _x;
	  }
	  def setY(_y:Int) {
	    y = _y
	  }
	}
	
	var isGuest:Boolean = false;
	var isBorder:Boolean = false;
	var bufImg: BufferedImage = null;
	var isTaxi: Boolean = false;
	var Taxi: BufferedImage = null;
	var isStart: Boolean = false;
	var startImg: BufferedImage = null;
	var isEnd: Boolean = false;
	var endImg: BufferedImage = null;
	
	private var _imagePath = ""                                                 
	private var bufferedImage:BufferedImage = null
	
	
	try {
		bufImg = ImageIO.read(new File("./grafiken/guest.png"));
	} catch {
	  case e: Exception => println("exception caught: " + e);
	}
	try {
		startImg = ImageIO.read(new File("./grafiken/start.png"));
	} catch {
	  case e: Exception => println("exception caught1: " + e);
	}
	try {
		endImg = ImageIO.read(new File("./grafiken/final.png"));
	} catch {
	  case e: Exception => println("exception caught2: " + e);
	}
	
	try {
		Taxi = ImageIO.read(new File("./grafiken/taxi.png"));
	} catch {
	  case e: Exception => println("exception caught3: " + e);
	}
	
	def position(x:Int, y:Int): Set[Int] =  {
	  if (x != null) {
		  this.posx = x;
	  }
	  if (y != null) {
		  this.posy = y;
	  }
	  return Set(this.posx, this.posy);
	}
/*	
  def imagePath = _imagePath
  
	def imagePath_=(value:String)                                               
  {                                                                           
    _imagePath = value                                                        
    bufferedImage = ImageIO.read(new File(_imagePath))    
    
    minimumSize = new java.awt.Dimension(bufferedImage.getWidth(), bufferedImage.getHeight()) 
    
  }   
	*/
	
	override def paintComponent(g:Graphics2D) =                                 
	  {    
	  super.paintComponent(g);
	  //println("override")
	    // if (null != bufferedImage) g.drawImage(bufferedImage, 0, 0, null)    
	  
		if (this.isGuest == true) {
		  println("guest")
		  g.drawImage(this.bufImg, 20, 20, null)
		  
		}
	  	if (this.isStart == true) {
		  println("start")
		  g.drawImage(this.startImg, 0, 0, null)
		  
		}
	  	if (this.isEnd == true) {
		  println("end")
		  g.drawImage(this.endImg, 0, 0, null)
		  
		}
	  	if (this.isTaxi == true) {
		  println("taxi")
		  g.drawImage(this.Taxi, 0, 0, null)
		  
		}
		//g.drawImage(this.bufferedImage, 0,0, null)
	
	  } 
	  
}