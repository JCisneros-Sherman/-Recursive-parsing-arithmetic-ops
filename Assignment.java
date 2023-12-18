import java.io.*;
import java.util.*;
import java.lang.Math; 

/*****************************************************************************
 * COURSE CSC 321
 * M McCullough
 * Josahandi Cisneros 
 * Project 01
 *
 * Recursive descent parsing for arithmetic operations
 *
 * Driver for a very simple Recursive Desent Parser for Arithmetic statements
 *
 * This program illustrates recursive descent parsing for arithmetic operations
 *
 * The grammar:
 *
 * S  =>  { A; } .
 * A  =>  I = E
 * E  =>  T | T (+|-) E
 * T  =>  F | F (*|/) T
 * F  =>  P | P
 * P  =>  I | N |( E )
 * I  =>  letter{letter|digit}
 * N  =>  digit {digit}
 ********************************************************************/
 
public class Assignment extends GlobalConstants
{
	public static void main(String[] args) 
		{
			if (args.length != 0)
			{
				System.out.println("Usage: java  ");
				System.exit(1);
			}
		System.out.println("This program illustrates recursive descent parsing");
	    System.out.println("         for simple arithmetic expressions ");
	    System.out.println("    S  =>  { A ; } .                   ");
	    System.out.println("    A  =>  I = E                           ");
	    System.out.println("    E  =>  T | T (+|-) E                  ");
	    System.out.println("    T  =>  F | F (*|/|%) T                   ");
	    System.out.println("    F  =>  P | P ^ F                       ");
	    System.out.println("    P  =>  I | N | ( E )                   ");
	    System.out.println("    I  =>  letter{letter|digit}            ");
	    System.out.println("    N  =>  digit {digit}                   ");
	    System.out.println("\n\nEnter a statement: ");
	    // Create a parser object
	    parser parser = new parser (new BufferedReader(new InputStreamReader(System.in)));
	    // start the parsing with STATEMENT
	      parser.start(); // call's statement();
	    } // main
} // class assignment

//A Simple Recursive Descent Syntactical Analyzer
	class parser extends GlobalConstants
	{  
		private Scanner scanner;
		public parser(BufferedReader br) 
		    {
		      scanner = new Scanner(br);
		    }

		public parser(Scanner scanner) 
		    {
		      this.scanner = scanner;
		    }

	    public void start() 
		    {
		      scanner.getToken(); // get first token
		      S(); // call statement
		    }
//     <Statement>
//     S -> { A ; } .
	    public void S() 
	    {
	    	while (token.type != PERIOD) 
	    	{
	    		int value = 0;
	    		Token id = token; // save current indentifer
	    		if (token.type != IDENT) 
	    		{
	    			scanner.error("Expecting Identifier or period found: " + token.toString());
	    			System.exit(1);
	    		}
	        
	        value = A(); // call Expression
	        System.out.println(" " + id.name + " = " + value);
	        if (token.type == PERIOD) break; // if we have a PERIOD we done
	        if (token.type == SEMICOLON)
	        {
	          scanner.getToken(); // else get next token
	        } else {
	          scanner.error("Expecting operator, 'semicolon', or 'period' found: " + token.toString());
	          break; }
	        } // while
	    } // statement

//   <assignment>  => <id> = <expression>
//      A -> I =  E
	    private int A()
	    {
	    	Token lhs = token;
	    	scanner.getToken();
	    	if (token.type != ASSIGN_OP)
	    	{
	    		scanner.error("Expecting assignment operator '='   found: " + token.toString());
	    	}
	    	scanner.getToken();
	    	int rhs = E();
	    	lhs.value = rhs;
// update token in SymbolTable
	       symtab.update(lhs);
           return lhs.value;
	    } // assignment

//     <Expression>
//     E  =>  T  |  T (+|-) E
	    private int E() 
	    {

	      int left = 0;
	      left = T(); // call Term save value (left-hand-side of operation)
	      // if we have an addition operation - we have work to do
	      if (token.type == ADD_OP || token.type == SUB_OP) 
	      {
	        int saveOp = token.type; // save operation - well need it later
	        scanner.getToken(); // get next token
	        
	        switch (saveOp) 
	        { // + or -
	          case ADD_OP:
	            left += E(); // call Expression (get right-hand-side) and add it left
	            break;
	          case SUB_OP:
	            left -= E(); // call Expression (get right-hand-side) and sub it from left
	            break;
	          default: // if current token is not + or -  syntax error?
	            scanner.error("Expecting a '+' or '-' operator");
	        } // switch
	      } // if

	      return left; // return current value
	    } // expression

//  <Term>
//  T  =>  F | T (*|/|%) T
	    private int T() 
	    {
	      int left = 0; // left-hand-side or operation
	      left = F(); // call Factor and save return value
	      // if token is * | / | % then
	      if (token.type == MULT_OP || token.type == DIV_OP || token.type == MOD_OP) 
	      {
	        int saveOp = token.type; // save operation - we'll need it
	        scanner.getToken(); // get next token
	       
	        switch (saveOp) 
	        { // do operation
	          	case MULT_OP:
	            left *= T(); // left = left * return value from Term
	            break;
	            
	          case DIV_OP:
	        	  int divisor = T(); // temporary 
		            if (divisor == 0) 
		            { // Division by zero is invalid 
		              System.err.println("divide by zero avoided");
		            } 
		            left /= divisor; // left = left / return value from Term
		            break;
	          
	          case MOD_OP: 
	        	  int divo = T(); // assigns divo the return value from <Term> (J.C)
		            if (divo == 0)  // saves the divisor # checks for invalid input (J.C)
		            { //divisor can't be zero (J.C)
		              divo = 1; //          -
		              System.err.println("Mod 0 is undefinied ");
		            } //if divo = 0 error is output to screen (J.C)
	        	  
	        	  left -=(divo * Math.floor(left/divo)); // Mathematical expression for MOD_OP, uses Math.floor function(J.C)  
		          break;
	         default:
	            scanner.error("Expecting a '*' or '/' operator");
	        } // switch
	      } // if
	     return left;
	    } // term

//     <Factor>
//     F  =>  P | P ^ F
	    private int F() 
	    {
	    	int left = 0, value = 0;
	        left = value = P(); // left-hand-side set to be return value from Primary (J.C)
	        if (token.type == POW_OP) 
	        	{ // if token is ^ (power) 
	        		scanner.getToken(); // get next token
	        		double x = left; //sets left side integer <Primary> to x (J.C)
	        		double y= F(); //sets right side integer <Factor> to y  (J.C)
	        		value = (int) Math.pow(x, y); //Math.pow function casted to set value = integer (J.C)
	        	}
	      return value;
	    } // primary

//     <Primary>
//     P = I | N |( E )
	    private int P() 
	    {
	      int value = 0;
	      switch (token.type)
	      {
	        case INT_LIT: // if token integer literal
	          value = scanner.number(); // save its value
	          scanner.getToken(); // get next token
	          break;
	        case IDENT:
	          value = token.value; // value set to id's value (if any)
	          Token save = token; // if identifier - save token
	          scanner.getToken(); // get next token
	          break;
	        case LEFT_PAREN: // if current token is (  we have  ( <Expression> )
	          scanner.getToken(); // call Expression save return value
	          value = E();
	          if (token.type != RIGHT_PAREN) // if token not )  - syntac error
	          scanner.error("Missing ')'");
	          scanner.getToken();
	          break;
	        default: // token was not <int> <id> or )
	          scanner.error("Expecting number, identifier,  or (   found: " + token.toString());
	          break; // printout message and exit
	      } // switch
	      return value;
	    } // primary
}


