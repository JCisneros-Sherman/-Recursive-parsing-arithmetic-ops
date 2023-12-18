import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.function.*;

/*****************************************************************************
 * COURSE CSC 321
 * M McCullough
 * Spring 2021
 *
 * Recursive descent parsing for arithmetic operations
 *
 * This is part of a program illustrates to recursive descent
 * parsing for arithmetic operations
 *
 * Sysmbol Table for a simple parser
 * keeps a tken for each IDENT
 * used mostly just to store current value
 *
 *************************************************************************/
public class SymbolTable extends GlobalConstants{
	ArrayList<Token> table; // list of current idents seen
	  boolean result;

	  public SymbolTable() {
	    table = new ArrayList<Token>();
	  }

	  // add (or update) current 'token' to table
	  public Token add(Token t) {
	    int location = search(t); // if there is already one in table
	    if (location != -1) {
	      t = table.get(location); // return the one from the table (may have value)
	      // output(verbosity,0,"SymTab:Add()  token: "+t+" already in SymTab - getting value");
	      return t;
	    }

	    result = table.add(t);
	    // if(result) output(verbosity, 0, "SymTab:Add() token "+t+" added ");
	    return t;
	  }

	  // updates an existing token in table
	  //   I am sure there is a better way to do this
	  //   it currently removes the existing (if one) and then
	  //       adds a new one  - rplacing old vaule with new
	  public boolean update(Token t) {
	    // output(verbosity, 0, "SymbolTable updateing "+t);

	    int location = search(t); // is there already on in talbe?
	    if (location != -1) {
	      // output(verboisty, 0, "SymTab:update()   found token "+t);
	      table.remove(location); // remove it and then add new one
	      // output(verbosity, 0, "SymTab:update()   updating token "+t);
	      result = table.add(t); // with new 'value'
	      // if(table.add(result)) output(verbosity, 0, "SymbolTable added "+t);
	      return true;
	    }
	    // if(table.add(t)) output(verbosity, 0, "SymbolTable added "+t);
	    table.add(t);
	    return false;
	  }

	  // looks for a token with same name as t
	  // returns the index into the table
	  public int search(Token t) {
	    for (int i = 0; i < table.size(); i++) {
	      Token cur = table.get(i);
	      if (cur.name.equals(t.name)) {
	        // output(verbosity, 0, "SymbolTable: found "+t+"at "+i);
	        return i;
	      }
	    }
	    // output(verbosity, 0, "SymbolTable: did NOT find "+t);
	    return -1;
	  }

	  // removes a token from the table
	  public boolean delete(Token t) {
	    return table.remove(t);
	  }

	  // for debugging
	  // prints out all tokens in table
	  public void print() {
	    // Printing elements one by one
	    System.out.println("/*************** Symbol Table ***************/");
	    for (int i = 0; i < table.size(); i++) {
	      System.out.println("\t" + table.get(i) + " ");
	    }
	    System.out.println("");
	    System.out.println("/********************************************/");
	  }
	

}
