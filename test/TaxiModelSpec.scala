import org.specs2.mutable._
import Taxi._


class TaxiModelSpec extends SpecificationWithJUnit {

  //def org$specs2$mutable$ArgumentsArgs$$super$args(arg0: ArgProperty, arg1: ArgProperty, arg2: ArgProperty, arg3: ArgProperty, arg4: ArgProperty, arg5: ArgProperty, arg6: ArgProperty, arg7: ArgProperty, arg8: ArgProperty, arg9: ArgProperty, arg10: ArgProperty, arg11: ArgProperty, arg12: ArgProperty, arg13: ArgProperty, arg14: ArgProperty, arg15: ArgProperty): Arguments = { null }
  "Create a new Model with size 1 and guests 1" should {
    var model:Taxi.TaxiModel = new TaxiModel(1,1);
    
    "have value fieldsize = 1+2" in {
      model.fieldsize must be_==(3);
    }
    
    "have value guests = 1" in {
      model.guestSize must be_== (1);
    }
    
    "arraysize must be 3" in {
      model.getSize must be_==(3);
    }
    
    "have 1 guests defined in a streetelement" in {
      var guestcount = 0;
      for(field:Array[StreetElement] <- model.fields) {
        for(street:StreetElement <- field)
        	if (street.isGuest) guestcount += 1;
      }
      guestcount must be_== (1);
    }
    
  }


}