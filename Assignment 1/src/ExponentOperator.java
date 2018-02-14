/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zachenney
 */
public class ExponentOperator extends Operator {

  
  
  public ExponentOperator()
  {
    
  }
    
  @Override
  public int priority()
  {
      return 4;
  }
   
  @Override
  public Operand execute( Operand op1, Operand op2 )
  {
      int total = 0;
      for(int i = 0; i < op2.getValue(); i++)
      {
         total = op1.getValue() * op1.getValue();
      }
      
      Operand exp = new Operand(total);
      return exp;
  }

}