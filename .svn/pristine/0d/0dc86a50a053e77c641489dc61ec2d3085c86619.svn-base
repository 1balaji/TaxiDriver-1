import org.specs2.mutable._
import Taxi._

class StreetElementSpec extends SpecificationWithJUnit {

  //def org$specs2$mutable$ArgumentsArgs$$super$args(arg0: ArgProperty, arg1: ArgProperty, arg2: ArgProperty, arg3: ArgProperty, arg4: ArgProperty, arg5: ArgProperty, arg6: ArgProperty, arg7: ArgProperty, arg8: ArgProperty, arg9: ArgProperty, arg10: ArgProperty, arg11: ArgProperty, arg12: ArgProperty, arg13: ArgProperty, arg14: ArgProperty, arg15: ArgProperty): Arguments = { null }
  "A new StreetElement at not_zero position 1,1 with border" should {
    var model:TaxiModel = new TaxiModel(5,1);
    var street:StreetElement = new StreetElement(0,true,model,1,1);
    
    "set X to 1" in {
      street.x must be_==(1);
    }
    "set Y to 1" in {
      street.y must be_==(1);
    }
    
    "have at least 1 tupel with 2 diggits > -1 for a street" in {
      val sum1 = street.streetOne.toList.sum[Int];
      val sum2 = street.streetTwo.toList.sum[Int];
      sum1 + sum2 must be_>= (0)
    }
  }

}