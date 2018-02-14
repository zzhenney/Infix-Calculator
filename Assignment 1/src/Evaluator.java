
import java.util.*;

public class Evaluator {
    
    private Stack<Operand> operandStack;
    private Stack<Operator> operatorStack;

    private StringTokenizer tokenizer;
    //TODO - make DELIMITER string dynamic by looping through hashmap and grabbing keys
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
        //checking at beginning
        Operator.operators.put("!", new BangOperator());
    }

    public int eval(String expression) {
        String token;
        
        //mark the end of string with ! 
        expression = expression + "!";
        System.out.println(expression);
        // The 3rd argument is true to indicate that the delimiters should be used
        // as tokens, too. But, we'll need to remember to filter out spaces.
        this.tokenizer = new StringTokenizer(expression, DELIMITERS, true);

        // initialize operator stack - necessary with operator priority schema
        // the priority of any operator in the operator stack other than
        // the usual mathematical operators - "+-*/" - should be less than the priority
        // of the usual operators
        // TODO Operator is abstract - this will need to be fixed:
        // operatorStack.push( new Operator( "#" ));
        
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
                    //System.out.println(operandStack.peek().getValue()); //debug
                } 
                
                else 
                {
                    if (!Operator.check(token)) 
                    {
                            //System.out.println("Trying to Push: " + token);
                            System.out.println("*****invalid token******");
                            System.exit(1);
                    }

                    // TODO Operator is abstract - these two lines will need to be fixed:
                    // The Operator class should contain an instance of a HashMap,
                    // and values will be instances of the Operators.  See Operator class
                    // skeleton for an example.
                    
                    Operator newOperator = Operator.operators.get(token);

                    while (operatorStack.peek().priority() >= newOperator.priority() && !newOperator.equals(Operator.operators.get("(")) && !operatorStack.peek().equals(Operator.operators.get("#"))){
                        
                        // note that when we eval the expression 1 - 2 we will
                        // push the 1 then the 2 and then do the subtraction operation
                        // This means that the first number to be popped is the
                        // second operand, not the first operand - see the following code
                        
                        if(newOperator.equals(Operator.operators.get(")")))
                        {
                            //System.out.println("Token: " + token);
                            
                            
                            //Calculate operands inside ()
                            while(!operatorStack.peek().equals(Operator.operators.get("(")) && !operatorStack.peek().equals(Operator.operators.get("#")))
                            {
                                Operator oldOpr = operatorStack.pop();                                
                                Operand op2 = operandStack.pop();                                 
                                Operand op1 = operandStack.pop();                                
                                operandStack.push(oldOpr.execute(op1, op2));
                            }
                            if(!operatorStack.peek().equals(Operator.operators.get("#")))
                            {                      
                                
                                operatorStack.pop(); //pop "("
                                //System.out.println("Popping (");
                                
                            }
                            
                            //had issue where token was not moving to next. would continue to pop 
                            // ( causing incorrect calculations, break was a quick fix as I
                            // realized very close to the deadline that i had been running the JUnit tests incorrectly
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
                        //System.out.print(token + " "); //debug
                    }    
                }
            }
            //System.out.println(operandStack.peek().getValue()); //debug
        }
        

        // Control gets here when we've picked up all of the tokens; you must add
        // code to complete the evaluation - consider how the code given here
        // will evaluate the expression 1+2*3
        // When we have no more tokens to scan, the operand stack will contain 1 2
        // and the operator stack will have + * with 2 and * on the top;
        // In order to complete the evaluation we must empty the stacks (except
        // the init operator on the operator stack); that is, we should keep
        // evaluating the operator stack until it only contains the init operator;
        // Suggestion: create a method that takes an operator as argument and
        // then executes the while loop; also, move the stacks out of the main
        // method
        
        operatorStack.clear();
        return (operandStack.pop().getValue());
    }
}
