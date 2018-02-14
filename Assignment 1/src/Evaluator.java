
import java.util.*;

public class Evaluator {
    
    private Stack<Operand> operandStack;
    private Stack<Operator> operatorStack;

    private StringTokenizer tokenizer;
    private static final String DELIMITERS = "+*-^/#!() ";

    public Evaluator() {
        operandStack = new Stack<>();
        operatorStack = new Stack<>();
        Operator.operators.put("*", new MultiplyOperator());
        Operator.operators.put("/", new DivideOperator());
        Operator.operators.put("+", new AdditionOperator());
        Operator.operators.put("-", new SubtractOperator());
        Operator.operators.put("#", new HashOperator());
        Operator.operators.put("^", new ExponentOperator());
        Operator.operators.put("(", new LeftParent());
        Operator.operators.put(")", new RightParent());
        Operator.operators.put("!", new BangOperator());
    }

    public int eval(String expression) {
        String token;
        
        //mark the end of string with ! 
        expression = expression + "!";
        
        // The 3rd argument is true to indicate that the delimiters should be used
        // as tokens, too. But, we'll need to remember to filter out spaces.
        this.tokenizer = new StringTokenizer(expression, DELIMITERS, true);     
        
        //equations will begin with # and end with !
        operatorStack.push(Operator.operators.get("#"));

        // When is it a good time to add the "!" operator?
        while (this.tokenizer.hasMoreTokens()) {
            // filter out spaces
            if (!(token = this.tokenizer.nextToken()).equals(" ")) {
                // check if token is an operand
                if (Operand.check(token)) 
                {
                    operandStack.push(new Operand(token));
                    
                } 
                
                else 
                {
                    if (!Operator.check(token)) 
                    {                   
                            System.out.println("*****invalid token******");
                            System.exit(1);
                    }
                                   
                    Operator newOperator = Operator.operators.get(token);
                    
                    //Main calculations
                    while (operatorStack.peek().priority() >= newOperator.priority() && !newOperator.equals(Operator.operators.get("(")) && !operatorStack.peek().equals(Operator.operators.get("#"))){
                    
                        //No need to place ) on stack. Calculate until ( is reached
                        if(newOperator.equals(Operator.operators.get(")")))
                        {                         
                            //Calculate operands inside (). Once ( is reached move down to pop stack
                            while(!operatorStack.peek().equals(Operator.operators.get("(")) && !operatorStack.peek().equals(Operator.operators.get("#")))
                            {
                                Operator oldOpr = operatorStack.pop();                                
                                Operand op2 = operandStack.pop();                                 
                                Operand op1 = operandStack.pop();                                
                                operandStack.push(oldOpr.execute(op1, op2));
                            }
                            
                            //Pop ( after calculating inside ()
                            if(!operatorStack.peek().equals(Operator.operators.get("#")))
                            {                                                    
                                operatorStack.pop();    
                            }
                            
                            //Exit loop in order to grab next token                          
                            break;                            
                        }
                        
                        //Calculate operands without ()
                        else if(!operatorStack.peek().equals(Operator.operators.get("#")))
                        {   
                            Operator oldOpr = operatorStack.pop();      
                            Operand op2 = operandStack.pop();                            
                            Operand op1 = operandStack.pop();                           
                            operandStack.push(oldOpr.execute(op1, op2));                            
                        } 
                    }
                    
                    //Push all operators, except ), onto operator stack
                    if(!newOperator.equals(Operator.operators.get(")")))
                    {
                        operatorStack.push(newOperator);   
                    }    
                }
            }         
        }
              
        //Clean up stack
        operatorStack.clear();
        return (operandStack.pop().getValue());
    }
}
