package Taxi


object Main extends App {
		val model= new TaxiModel(8,2);
		
		val fields = model.getFields().toString();
		
		val controller= new TaxiControl(model);
		val gui = new SwingMain(controller);
		
		
		new TaxiTextUi(controller);
		
}