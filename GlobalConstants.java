import java.io.*;
import java.util.*;


/*****************************************************************************
 * COURSE CSC 321
 * M McCullough
 * Spring 2021
 *
 * Recursive descent parsing for arithmetic operations
 *
 * Globals for a simple parser (recursive descent)
 *
 * There are better ways of making these avalible but they are
 * (but that seem cumbersome to an old C programmer or maybe I am just lazy)
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
 *
 * note: it does not correctly handle '%' or '^'
 * that needs to be fixed!
 *
 */
public class GlobalConstants
{
	public static Token token; // global token - everone uses
	  // table of symbols store indent
	  public static SymbolTable symtab = new SymbolTable();
	  //  array of strings used in displaying
	  static String spelling[] = {
	    "unknown", "Int", "Ident", "Assign", "Add",
	    "Sub", "Mod", "Mul", "Div", "Pow",
	    "LParn", "RParn", "Period", "Semicolon"
	  };
	  static String abbreviation[] = {
	    "0", "int", "ID", "=", "+", "-", "%", "*", "/", "^", "(", ")", ".", ";"
	  };

	  //    int values for each token
	  static final int UNKNOWN = 0,
	      INT_LIT = 1,
	      IDENT = 2,
	      ASSIGN_OP = 3,
	      ADD_OP = 4,
	      SUB_OP = 5,
	      MOD_OP = 6,
	      MULT_OP = 7,
	      DIV_OP = 8,
	      POW_OP = 9,
	      LEFT_PAREN = 10,
	      RIGHT_PAREN = 11,
	      PERIOD = 12,
	      SEMICOLON = 13;

	  // enumeration for all the types of tokens
	  //    C enums would be better
	  enum Type {
	    UNKNOWN,
	    INT_LIT,
	    IDENT,
	    ASSIGN_OP,
	    ADD_OP,
	    SUB_OP,
	    MOD_OP,
	    MULT_OP,
	    DIV_OP,
	    POW_OP,
	    LEFT_PAREN,
	    RIGHT_PAREN,
	    PERIOD,
	    SEMICOLON;

	    public static Type fromInt(int x) {
	      switch (x) {
	        case 0:
	          return UNKNOWN;
	        case 1:
	          return INT_LIT;
	        case 2:
	          return IDENT;
	        case 3:
	          return ASSIGN_OP;
	        case 4:
	          return ADD_OP;
	        case 5:
	          return SUB_OP;
	        case 6:
	          return MOD_OP;
	        case 7:
	          return MULT_OP;
	        case 8:
	          return DIV_OP;
	        case 9:
	          return POW_OP;
	        case 10:
	          return LEFT_PAREN;
	        case 11:
	          return RIGHT_PAREN;
	        case 12:
	          return PERIOD;
	        case 13:
	          return SEMICOLON;
	      }
	      return null;
	    }
	  }

}
