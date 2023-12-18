import java.io.*;
import java.util.*;

public class Scanner extends GlobalConstants
{private char ch = ' '; // current char (greedy)
private int intValue = 0; // tmp for get numerical values
private inputBuffer buffer; // inner private class for reading stdin

public Scanner() {} // private constructor - making it uncallable
// have to give us a buffered input stream
public Scanner(BufferedReader in) {
  buffer = new inputBuffer(in);
  token = new Token(SEMICOLON);
} // Scanner

// main method - gets a returns next 'token'
public Token getToken() {
  while (Character.isWhitespace(ch)) { // SPACE RETURN TAB
    ch = buffer.get(); // skip!!
  }
  if (Character.isLetter(ch)) { // it is an IDENTIFIER !!
    String name = getString(); // tmp string - to build IDNET's name
    token = new Token(IDENT, name); // make new token
    token = symtab.add(token); // add to symbol table or get existing value

  } else if (Character.isDigit(ch)) { // it is a NUMBER!!!  or an INTEGER LITERAL
    intValue = getNumber(); // get digits - most sigificate 1st
    token = new Token(INT_LIT, intValue); // make a token
  } else { // okay so it has to be an operator or seperator
    switch (ch) { // so get it, idenify it, and make a token for it
      case ';':
        ch = buffer.get();
        token = new Token(SEMICOLON);
        break;
      case '.':
        ch = buffer.get();
        token = new Token(PERIOD);
        break;
      case '+':
        ch = buffer.get();
        token = new Token(ADD_OP);
        break;
      case '-':
        ch = buffer.get();
        token = new Token(SUB_OP);
        break;
      case '*':
        ch = buffer.get();
        token = new Token(MULT_OP);
        break;
      case '/':
        ch = buffer.get();
        token = new Token(DIV_OP);
        break;
      case '^':
        ch = buffer.get();
        token = new Token(POW_OP);
        break;
      case '%':
        ch = buffer.get();
        token = new Token(MOD_OP);
        break;
      case '=':
        ch = buffer.get();
        token = new Token(ASSIGN_OP);
        break;
      case '(':
        ch = buffer.get();
        token = new Token(LEFT_PAREN);
        break;
      case ')':
        ch = buffer.get();
        token = new Token(RIGHT_PAREN);
        break;

		  /**********************************

	      *********************************/
      default:
        error("Illegal character " + ch);
        break;
    } // switch
  } // if
  return token;
} // getToken

// return integer value of what was just read in
public int number() {
  return intValue;
} // number

// return name  of token  just read in
public String name() {
  return token.name;
} // name

// handle errors - print to stderr and exit program
//       - could try to keep going but would have to find a
//          'sane state'
public void error(String msg) {
  System.err.println(msg);
  System.exit(1);
} // error

//    read in chars and convert string (char by char) to number
//    assumes ch has been set
//    uses Buffer class
private int getNumber() {
  int result = 0;
  do {
    result = result * 10 + Character.digit(ch, 10);
    ch = buffer.get();
  } while (Character.isDigit(ch));
  return result;
} // getNumber

//  assumes ch is already a letter ([a-zA-Z]
//  read in chars until ch is NOT letter or digit  ([a-zA-Z]|[0-9])*
// into a Stringbuilder and convert to string
private String getString() {
  StringBuilder tmpstr = new StringBuilder("");
  do {
    tmpstr.append(ch);
    ch = buffer.get();
  } while ((Character.isDigit(ch) || (Character.isLetter(ch))));
  return tmpstr.toString();
}

/* PRIVATE INNER CLASS
   Buffer class private to Scanner (sub-class)
        reads line by line storing all char a tempary string
        get() returns a single char; reading line of text from stdin if needed
*/
class inputBuffer {
  private String line = ""; // buffer into which we read
  private int column = 0; // index into the current line - where we get char to return
  private int lineNo = 0; // line number increamented for every '\n' we see
  private BufferedReader in; // stdin as a buffered 'READER'

  private inputBuffer() {
    ;
  } // make it uncallable - must pass in a stdin

  public inputBuffer(BufferedReader in) {
    this.in = in; // set 'in' here
  } // Buffer

  public char get() { // main method - returns a char (next in input)
    column++;
    if (column >= line.length()) { // we need a to read in a new line
      try {
        line = in.readLine();
      } catch (Exception e) {
        System.err.println("Invalid read operation");
        System.exit(1); // gracefully leave
      } // try
      if (line == null) // end of the stream has been reached
      System.exit(0); // if we only get back a NULL we are done!
      column = 0; // reset index
      lineNo++; // we got another line
      // indentPrint(0,line);
      line = line + "\n"; // put 'newline' on end
    } // if column
    return line.charAt(column);
  } // get
}
}
