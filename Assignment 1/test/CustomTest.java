import org.junit.Test;
import static org.junit.Assert.*;

public class CustomTest {

  @Test
  public void testHardThing() {
    Evaluator instance = new Evaluator();

    //String expression = "2+3-5*((2-3)*2-5*2+3*(2-3-5-5*6)+4/2)";
    //int result = 595;
    
    String expression = "5^2*((2-4)+1)^2";
    int result = 25;

    assertEquals( instance.eval( expression ), result );
  }

}
