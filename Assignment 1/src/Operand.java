public class Operand {

public int operand;
private String opStr;
    
  public Operand( String token ) {
      operand = Integer.parseInt(token);
     
  }

  public Operand( int value ) {
      operand = value;
  }

  public int getValue() {
      
      return operand;
  }

  public static boolean check( String token ) {
      try
        { 
          Integer.parseInt(token); 
          return true; 
        }

      catch(NumberFormatException er)
        { 
          return false;
          
        }
  }
}
