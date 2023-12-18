import java.io.*;
import java.util.*;

/*****************************************************************************
 * COURSE CSC 321
 * M McCullough
 * Spring 2021
 *
 * Recursive descent parsing for arithmetic operations
 *
 *
 * This is part of a program illustrates to recursive descent
 * parsing for arithmetic operations
 *
 * Token class for simple parser
 * type  shouls be enum
 * value is currently just int
 * name  is String (for Ident)
 *
 ************************************************************************/
public class Token extends GlobalConstants implements Comparable 
{
	Type ttype; // enumeration
	  int type;
	  int value;
	  String name;

	  public Token() {
	    ttype = Type.UNKNOWN;
	    type = UNKNOWN;
	    value = 0;
	    name = spelling[UNKNOWN];
	  }

	  public Token(int type) {
	    this();
	    if (type >= UNKNOWN && type <= SEMICOLON) {
	      this.type = type;
	      this.ttype = Type.fromInt(type);
	      name = new String(spelling[this.type]);
	    }
	  }

	  public Token(int type, int value) {
	    this(type);
	    this.value = value;
	  }

	  public Token(int type, String name) {
	    this(type);
	    this.name = new String(name);
	    ;
	  }

	  // @Override
	  public String toString() {
	    return toString(this.type);
	  }

	  public String toString(int i) {
	    if (i < 0 || i > SEMICOLON) return "";
	    // String tmpstr = "Type:"+spelling[i] + ", enum:" + type + ", name:"+name+", value:"+value;
	    String tmpstr = "" + type + ":" + spelling[i] + ", " + name + ", " + value;
	    return tmpstr;
	  } // toString

	  public String print() {
	    switch (type) {
	      case IDENT:
	        return " " + name + " ";
	      case INT_LIT:
	        return " " + value + " ";
	      default:
	        return " " + spelling[type] + " ";
	    } // switch
	  } // print()

	  public String toStringfull(int i) {
	    if (i < 0 || i > SEMICOLON) return "";
	    String tmpstr =
	        "Type:" + spelling[i] + ", enum:" + type + ", name:" + name + ", value:" + value;
	    return tmpstr;
	  } // toString

	  // if we are comparing to tokens currently just using the name (idents)
	  //     use this for SymTab
	  //          e.g.  we might have a token called "foo" with value =2
	  // @Override
	  public int compareTo(Object that) {
	    return this.name.compareTo(((Token) that).name);
	  }
	
}
