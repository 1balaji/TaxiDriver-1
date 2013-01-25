package Taxi

class TaxiCostModel {
  
	var distance = 0;
	var earnings = 0;
	var success: Boolean = false;
	
	def modifyVals(earning:Int, distance:Int): TaxiCostModel = {
	  this.earnings += earning;
	  this.distance += distance;
	  return this;
	}

}