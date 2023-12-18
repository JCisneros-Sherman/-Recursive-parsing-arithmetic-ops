# -Recursive-parsing-arithmetic-ops
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
