//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package parser;



//#line 2 "../../src/parser/parser.y"
/* * Java declarations*/
/*   This code is copied in the beginning of the generated Java file*/
import scanner.Scanner;

import java.util.*;
import ast.*;
import ast.others.*;
import ast.definitions.*;
import ast.expresions.*;
import ast.statements.*;
import ast.types.*;
import errorhandler.EH;
//#line 30 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Object
String   yytext;//user variable to return contextual strings
Object yyval; //used to return semantic vals from action routines
Object yylval;//the 'lval' (result) I got from yylex()
Object valstk[] = new Object[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Object();
  yylval=new Object();
  valptr=-1;
}
final void val_push(Object val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Object[] newstack = new Object[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Object val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Object val_peek(int relative)
{
  return valstk[valptr-relative];
}
final Object dup_yyval(Object val)
{
  return val;
}
//#### end semantic value section ####
public final static short INT_CONSTANT=257;
public final static short CHAR_CONSTANT=258;
public final static short REAL_CONSTANT=259;
public final static short WHILE=260;
public final static short RETURN=261;
public final static short MAIN=262;
public final static short INT=263;
public final static short CHAR=264;
public final static short DOUBLE=265;
public final static short STRUCT=266;
public final static short ID=267;
public final static short AND=268;
public final static short OR=269;
public final static short LESS_THAN=270;
public final static short IF=271;
public final static short ELSE=272;
public final static short VOID=273;
public final static short READ=274;
public final static short WRITE=275;
public final static short EQUAL=276;
public final static short NOT_EQUAL=277;
public final static short LESS_EQUAL=278;
public final static short GREATER_EQUAL=279;
public final static short NEGATION=280;
public final static short UNARY_MINUS=281;
public final static short CAST=282;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    4,    4,    3,    3,    8,    8,
    9,    9,    9,    9,    9,    9,    9,   14,   15,   15,
   13,   13,   11,   11,   11,   11,   11,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   10,   10,   16,   16,
    5,   17,   17,   18,   18,    2,    2,   19,   19,   12,
   12,   20,   21,   21,    7,    7,   22,   23,   23,    6,
    6,    6,    6,    6,
};
final static short yylen[] = {                            2,
    9,    2,    2,    0,    9,    9,    1,    0,    2,    1,
    3,    3,    4,    5,    5,    1,    3,    6,    2,    0,
    3,    1,    3,    3,    3,    3,    3,    2,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    4,    2,    4,
    3,    4,    1,    1,    1,    1,    3,    1,    1,    0,
    3,    3,    1,    2,    1,    1,    0,    3,    1,    1,
    0,    2,    3,    1,    1,    0,    3,    2,    1,    1,
    1,    1,    4,    4,
};
final static short yydefred[] = {                         4,
    0,    0,   70,   71,   72,    0,    0,    2,    3,    0,
    0,    0,    0,    0,    0,    0,    0,   69,    0,    0,
    0,    0,    0,   51,    0,   53,    0,   74,   68,    0,
    0,    0,   64,    0,    0,   73,   52,   67,    0,   62,
    0,    0,    0,    0,   55,    0,    0,    0,   63,    0,
   44,   46,   45,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   10,    0,   16,   54,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    9,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   17,    0,    0,    0,    0,   11,
    0,   12,    0,   29,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   41,
    6,    5,    0,    0,    0,    0,    0,    0,    0,   13,
   42,    0,   22,   15,   38,   14,    0,    0,    0,    0,
   18,   21,   19,
};
final static short yydgoto[] = {                          1,
    2,   44,   63,    8,   45,   46,   32,   64,   65,   76,
   66,  107,  144,   67,  151,    0,   16,   47,  108,   33,
   34,   18,   19,
};
final static short yysindex[] = {                         0,
    0, -140,    0,    0,    0, -108, -225,    0,    0,  -90,
 -144,    4,    5,    6, -189,  -32,  -80,    0,  -58,   30,
 -144, -144,   14,    0, -174,    0,  -30,    0,    0,  -25,
  -78,   58,    0,   64,   86,    0,    0,    0, -144,    0,
   11, -144,   18,  120,    0,  -80, -144, -144,    0, -144,
    0,    0,    0,  103,  182,  104,  105,  182,  182,  182,
  478,  182,   21,  120,    0,  121,    0,    0,  120,  120,
  182,  107,  133,  182,  182,  -16,  385,   -4,  -26,  -39,
  157,  411,    0,    0,  182,  182,  182,  182,  182,  182,
  182,  182,  182,  182,  182,  182,  182,  182,  182, -119,
   25,   26,  209,  182,    0,  385,  111,  115,  236,    0,
  182,    0,  182,    0,  411,  411,   27,   27,   27,   27,
  270,   27,   27,  673,  673,  -26,  -26,  -26,  183,    0,
    0,    0,  269,  128,  102,  182,  269,  385,  -26,    0,
    0,  120,    0,    0,    0,    0,  385, -101,   47,  269,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    3,    0,    0,    0,    0,    0,    0,
  132,  132,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  143,    0,    0,    0,    0,  373,    0,
    0,    0,    0,   49,    0,    0,  410,  373,    0,  373,
    0,    0,    0,    0,    0,  297,    0,    0,    0,    0,
    0,    0,    0,   60,    0,    0,    0,    0,   49,   49,
    0,  -37,    0,  145,    0,    0,   36,    0,  -11,    0,
    0,   56,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  145,    0,   -6,    0,  149,    0,    0,
    0,    0,    0,    0,  501,  662,  523,  536,  549,  563,
    0,  614,  638,  448,  510,   16,   42,   69,    0,    0,
    0,    0,    0,    0,  323,    0,    0,   37,   95,    0,
    0,   49,    0,    0,    0,    0,   50,  434,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   -9,  -52,    0,   19,  461,  169,    0,  -45,  137,
  775,   93, -134,    0,    0,    0,  184,    0,    0,  167,
    0,  191,    0,
};
final static int YYTABLESIZE=931;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         43,
   15,  113,  148,   43,   43,   43,   43,   43,   43,   43,
   15,   25,   15,   25,   11,  153,  101,  102,   84,  100,
    9,   43,   43,   43,   43,   28,   24,  111,   38,   28,
   28,   28,   28,   28,   59,   28,   12,   59,   69,  111,
   70,   13,  110,   20,   21,   22,   53,   28,   28,   28,
   28,   15,   25,   43,  112,   43,   25,   25,   25,   25,
   25,   53,   25,   98,   99,   68,   28,   23,   96,   94,
   30,   95,  100,   97,   25,   25,   25,   25,   26,   48,
   47,   28,   26,   26,   26,   26,   26,  143,   26,  149,
   58,  143,   37,   58,   48,   47,   39,   39,   41,   39,
   26,   26,   26,   26,  143,   27,   36,   42,   25,   27,
   27,   27,   27,   27,   39,   27,   39,   99,    3,    4,
    5,    6,    3,    4,    5,    6,   43,   27,   27,   27,
   27,   40,    7,   48,   26,   40,   40,   40,   40,   40,
   50,   40,   71,   74,   75,   83,  104,  130,   39,  131,
  132,  135,   62,   40,   40,   40,   40,   98,  136,   61,
  146,   27,   96,   94,   60,   95,  100,   97,  145,   98,
  150,  152,   66,    8,   96,   94,   14,   95,  100,   97,
   92,   91,   93,   65,    7,   61,   26,   40,   40,   60,
   35,  105,   92,   98,   93,   78,  134,  114,   96,   94,
   27,   95,  100,   97,    3,    4,    5,    6,   49,   29,
    0,   99,    0,    0,   62,    0,   92,    0,   93,   98,
    0,   61,    0,   99,   96,   94,   60,   95,  100,   97,
   43,   43,    0,    0,    0,    0,    0,    0,   43,   43,
   43,   43,   92,    0,   93,   98,    0,   99,    0,  133,
   96,   94,    0,   95,  100,   97,   28,   28,    0,    0,
    0,    0,    0,    0,   28,   28,   28,   28,   92,    0,
   93,    0,   98,   99,    0,  141,  137,   96,   94,    0,
   95,  100,   97,   25,   25,    0,    0,    0,    0,    0,
    0,   25,   25,   25,   25,   92,    0,   93,    0,   99,
    0,   62,    0,    0,    0,    0,   98,    0,   61,   26,
   26,   96,   94,   60,   95,  100,   97,   26,   26,   26,
   26,    0,    0,   39,   39,    0,   99,    0,  140,   92,
    0,   93,    0,   43,    0,    0,   27,   27,   43,   43,
    0,   43,   43,   43,   27,   27,   27,   27,    0,    0,
    0,    0,    0,    0,    0,    0,   43,   43,   43,   38,
   99,    0,   40,   40,   38,   38,    0,   38,   38,   38,
   40,   40,   40,   40,    0,    0,   51,   52,   53,   54,
   55,    0,   38,   38,   38,    0,   56,   43,   85,   86,
   57,  142,    0,   58,   59,    0,   87,   88,   89,   90,
   85,   86,    0,    0,    0,   57,    0,    0,   87,   88,
   89,   90,   57,   38,    0,    0,    0,   57,    0,    0,
    0,   98,    0,    0,   85,   86,   96,   94,    0,   95,
  100,   97,   87,   88,   89,   90,    0,    0,   51,   52,
   53,    0,   56,    0,   92,    0,   93,   98,   72,   56,
   85,   86,   96,   94,   56,   95,  100,   97,   87,   88,
   89,   90,   10,    0,    0,    0,   20,    0,    0,    0,
   92,   17,   93,   20,    0,   99,   85,   86,   20,   17,
    0,   31,   31,    0,   87,   88,   89,   90,   23,    0,
   23,   23,   23,    0,    0,    0,    0,   57,    0,    0,
    0,   99,   31,   85,   86,    0,   23,   23,   23,   23,
   62,   87,   88,   89,   90,    0,    0,   61,    0,    0,
    0,   80,   60,    0,    0,   51,   52,   53,   54,   55,
    0,    0,    0,    0,   56,   56,    0,   85,   86,   57,
   23,   36,   58,   59,   36,   87,   88,   89,   90,    0,
   24,    0,   24,   24,   24,    0,    0,    0,   20,   36,
    0,   36,    0,   34,   43,   43,   34,    0,   24,   24,
   24,   24,   43,   43,   43,   43,   35,    0,    0,   35,
    0,   34,   34,   34,   34,    0,    0,    0,    0,   32,
   38,   38,   32,   36,   35,   35,   35,   35,   38,   38,
   38,   38,   24,   33,    0,    0,   33,   32,   32,   32,
   32,    0,    0,    0,    0,   34,    0,    0,    0,    0,
    0,   33,   33,   33,   33,    0,    0,    0,   35,   57,
   57,   57,   57,   57,    0,    0,    0,    0,    0,   57,
    0,   32,    0,   57,    0,    0,   57,   57,    0,    0,
    0,    0,   85,   86,   31,   33,    0,   31,    0,    0,
   87,   88,   89,   90,    0,    0,   56,   56,   56,   56,
   56,    0,   31,   31,   31,   31,   56,    0,   30,    0,
   56,   30,    0,   56,   56,    0,   87,   88,   89,   90,
   20,   20,   20,   20,   20,    0,   30,   30,   30,   30,
   20,    0,   37,    0,   20,   37,   31,   20,   20,   98,
    0,    0,    0,    0,   96,   23,   23,    0,  100,   97,
   37,    0,   37,   23,   23,   23,   23,    0,    0,    0,
   30,    0,    0,    0,   51,   52,   53,    0,    0,    0,
    3,    4,    5,    6,   72,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   37,    0,    0,    0,    0,    0,
    0,    0,    0,   99,    0,    0,    0,    0,   36,   36,
    0,    0,    0,    0,    0,    0,    0,   24,   24,    0,
    0,    0,    0,    0,    0,   24,   24,   24,   24,    0,
   34,   34,    0,    0,    0,    0,    0,    0,   34,   34,
   34,   34,    0,   35,   35,    0,    0,    0,    0,    0,
    0,   35,   35,   35,   35,    0,   32,   32,    0,    0,
    0,    0,    0,    0,   32,   32,   32,   32,    0,   73,
   33,   33,   77,   77,   79,   81,   82,    0,   33,   33,
   33,   33,    0,    0,    0,  103,    0,    0,  106,  109,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  115,
  116,  117,  118,  119,  120,  121,  122,  123,  124,  125,
  126,  127,  128,  129,    0,    0,    0,    0,  106,    0,
    0,   31,   31,    0,    0,  138,    0,  139,    0,   31,
   31,   31,   31,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   30,   30,    0,    0,    0,
  147,    0,    0,   30,   30,   30,   30,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   37,
   37,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
   91,   41,  137,   41,   42,   43,   44,   45,   46,   47,
   91,   44,   91,   44,  123,  150,   69,   70,   64,   46,
    2,   59,   60,   61,   62,   37,   59,   44,   59,   41,
   42,   43,   44,   45,   41,   47,  262,   44,   48,   44,
   50,  267,   59,   40,   40,   40,   44,   59,   60,   61,
   62,   91,   37,   91,   59,   93,   41,   42,   43,   44,
   45,   59,   47,   37,   91,   47,  125,  257,   42,   43,
   41,   45,   46,   47,   59,   60,   61,   62,   37,   44,
   44,   93,   41,   42,   43,   44,   45,  133,   47,  142,
   41,  137,  267,   44,   59,   59,   41,  123,   41,   44,
   59,   60,   61,   62,  150,   37,   93,   44,   93,   41,
   42,   43,   44,   45,   59,   47,   61,   91,  263,  264,
  265,  266,  263,  264,  265,  266,   41,   59,   60,   61,
   62,   37,  273,  123,   93,   41,   42,   43,   44,   45,
  123,   47,   40,   40,   40,  125,   40,  267,   93,  125,
  125,   41,   33,   59,   60,   61,   62,   37,   44,   40,
   59,   93,   42,   43,   45,   45,   46,   47,   41,   37,
  272,  125,   41,  125,   42,   43,  267,   45,   46,   47,
   60,   61,   62,   41,  125,   41,  267,   93,  267,   41,
   22,   59,   60,   37,   62,   59,  104,   41,   42,   43,
   17,   45,   46,   47,  263,  264,  265,  266,   42,   19,
   -1,   91,   -1,   -1,   33,   -1,   60,   -1,   62,   37,
   -1,   40,   -1,   91,   42,   43,   45,   45,   46,   47,
  268,  269,   -1,   -1,   -1,   -1,   -1,   -1,  276,  277,
  278,  279,   60,   -1,   62,   37,   -1,   91,   -1,   41,
   42,   43,   -1,   45,   46,   47,  268,  269,   -1,   -1,
   -1,   -1,   -1,   -1,  276,  277,  278,  279,   60,   -1,
   62,   -1,   37,   91,   -1,   93,   41,   42,   43,   -1,
   45,   46,   47,  268,  269,   -1,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,  279,   60,   -1,   62,   -1,   91,
   -1,   33,   -1,   -1,   -1,   -1,   37,   -1,   40,  268,
  269,   42,   43,   45,   45,   46,   47,  276,  277,  278,
  279,   -1,   -1,  268,  269,   -1,   91,   -1,   59,   60,
   -1,   62,   -1,   37,   -1,   -1,  268,  269,   42,   43,
   -1,   45,   46,   47,  276,  277,  278,  279,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   60,   61,   62,   37,
   91,   -1,  268,  269,   42,   43,   -1,   45,   46,   47,
  276,  277,  278,  279,   -1,   -1,  257,  258,  259,  260,
  261,   -1,   60,   61,   62,   -1,  267,   91,  268,  269,
  271,  123,   -1,  274,  275,   -1,  276,  277,  278,  279,
  268,  269,   -1,   -1,   -1,   33,   -1,   -1,  276,  277,
  278,  279,   40,   91,   -1,   -1,   -1,   45,   -1,   -1,
   -1,   37,   -1,   -1,  268,  269,   42,   43,   -1,   45,
   46,   47,  276,  277,  278,  279,   -1,   -1,  257,  258,
  259,   -1,   33,   -1,   60,   -1,   62,   37,  267,   40,
  268,  269,   42,   43,   45,   45,   46,   47,  276,  277,
  278,  279,    2,   -1,   -1,   -1,   33,   -1,   -1,   -1,
   60,   11,   62,   40,   -1,   91,  268,  269,   45,   19,
   -1,   21,   22,   -1,  276,  277,  278,  279,   41,   -1,
   43,   44,   45,   -1,   -1,   -1,   -1,  125,   -1,   -1,
   -1,   91,   42,  268,  269,   -1,   59,   60,   61,   62,
   33,  276,  277,  278,  279,   -1,   -1,   40,   -1,   -1,
   -1,   61,   45,   -1,   -1,  257,  258,  259,  260,  261,
   -1,   -1,   -1,   -1,  125,  267,   -1,  268,  269,  271,
   93,   41,  274,  275,   44,  276,  277,  278,  279,   -1,
   41,   -1,   43,   44,   45,   -1,   -1,   -1,  125,   59,
   -1,   61,   -1,   41,  268,  269,   44,   -1,   59,   60,
   61,   62,  276,  277,  278,  279,   41,   -1,   -1,   44,
   -1,   59,   60,   61,   62,   -1,   -1,   -1,   -1,   41,
  268,  269,   44,   93,   59,   60,   61,   62,  276,  277,
  278,  279,   93,   41,   -1,   -1,   44,   59,   60,   61,
   62,   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,
   -1,   59,   60,   61,   62,   -1,   -1,   -1,   93,  257,
  258,  259,  260,  261,   -1,   -1,   -1,   -1,   -1,  267,
   -1,   93,   -1,  271,   -1,   -1,  274,  275,   -1,   -1,
   -1,   -1,  268,  269,   41,   93,   -1,   44,   -1,   -1,
  276,  277,  278,  279,   -1,   -1,  257,  258,  259,  260,
  261,   -1,   59,   60,   61,   62,  267,   -1,   41,   -1,
  271,   44,   -1,  274,  275,   -1,  276,  277,  278,  279,
  257,  258,  259,  260,  261,   -1,   59,   60,   61,   62,
  267,   -1,   41,   -1,  271,   44,   93,  274,  275,   37,
   -1,   -1,   -1,   -1,   42,  268,  269,   -1,   46,   47,
   59,   -1,   61,  276,  277,  278,  279,   -1,   -1,   -1,
   93,   -1,   -1,   -1,  257,  258,  259,   -1,   -1,   -1,
  263,  264,  265,  266,  267,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,  268,  269,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  268,  269,   -1,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  279,   -1,
  268,  269,   -1,   -1,   -1,   -1,   -1,   -1,  276,  277,
  278,  279,   -1,  268,  269,   -1,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,  279,   -1,  268,  269,   -1,   -1,
   -1,   -1,   -1,   -1,  276,  277,  278,  279,   -1,   55,
  268,  269,   58,   59,   60,   61,   62,   -1,  276,  277,
  278,  279,   -1,   -1,   -1,   71,   -1,   -1,   74,   75,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   85,
   86,   87,   88,   89,   90,   91,   92,   93,   94,   95,
   96,   97,   98,   99,   -1,   -1,   -1,   -1,  104,   -1,
   -1,  268,  269,   -1,   -1,  111,   -1,  113,   -1,  276,
  277,  278,  279,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  268,  269,   -1,   -1,   -1,
  136,   -1,   -1,  276,  277,  278,  279,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  268,
  269,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=282;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"INT_CONSTANT","CHAR_CONSTANT",
"REAL_CONSTANT","WHILE","RETURN","MAIN","INT","CHAR","DOUBLE","STRUCT","ID",
"AND","OR","LESS_THAN","IF","ELSE","VOID","READ","WRITE","EQUAL","NOT_EQUAL",
"LESS_EQUAL","GREATER_EQUAL","NEGATION","UNARY_MINUS","CAST",
};
final static String yyrule[] = {
"$accept : program",
"program : body VOID MAIN '(' ')' '{' varDefinitionOP statementOP '}'",
"body : body funDefinition",
"body : body varDefinition",
"body :",
"funDefinition : type ID '(' parameterOP ')' '{' varDefinitionOP statementOP '}'",
"funDefinition : VOID ID '(' parameterOP ')' '{' varDefinitionOP statementOP '}'",
"statementOP : statementS",
"statementOP :",
"statementS : statementS statement",
"statementS : statement",
"statement : READ expressionS ';'",
"statement : WRITE expressionS ';'",
"statement : expression '=' expression ';'",
"statement : ID '(' argumentOP ')' ';'",
"statement : WHILE '(' expression ')' complexStatement",
"statement : ifStatement",
"statement : RETURN expression ';'",
"ifStatement : IF '(' expression ')' complexStatement else",
"else : ELSE complexStatement",
"else :",
"complexStatement : '{' statementOP '}'",
"complexStatement : statement",
"expression : expression '+' expression",
"expression : expression '-' expression",
"expression : expression '*' expression",
"expression : expression '/' expression",
"expression : expression '%' expression",
"expression : '-' expression",
"expression : '(' expression ')'",
"expression : expression '>' expression",
"expression : expression '<' expression",
"expression : expression LESS_EQUAL expression",
"expression : expression GREATER_EQUAL expression",
"expression : expression EQUAL expression",
"expression : expression NOT_EQUAL expression",
"expression : expression AND expression",
"expression : expression OR expression",
"expression : ID '(' argumentOP ')'",
"expression : '!' expression",
"expression : '(' type ')' expression",
"expression : expression '.' ID",
"expression : expression '[' expression ']'",
"expression : ID",
"expression : INT_CONSTANT",
"expression : REAL_CONSTANT",
"expression : CHAR_CONSTANT",
"expressionS : expressionS ',' expression",
"expressionS : expression",
"expressionOP : expressionS",
"expressionOP :",
"varDefinition : type IDList ';'",
"IDList : IDList ',' ID",
"IDList : ID",
"varDefinitionS : varDefinitionS varDefinition",
"varDefinitionS : varDefinition",
"varDefinitionOP : varDefinitionS",
"varDefinitionOP :",
"argumentS : argumentS ',' expression",
"argumentS : expression",
"argumentOP : argumentS",
"argumentOP :",
"param : type ID",
"parameterS : parameterS ',' param",
"parameterS : param",
"parameterOP : parameterS",
"parameterOP :",
"campo : type IDList ';'",
"campoS : campoS campo",
"campoS : campo",
"type : INT",
"type : CHAR",
"type : DOUBLE",
"type : type '[' INT_CONSTANT ']'",
"type : STRUCT '{' campoS '}'",
};

//#line 235 "../../src/parser/parser.y"

// * Code
//   Members of the generated Parser class
// We must implement at least:
// - int yylex()
// - void yyerror(String)

// * Lexical analyzer
private Scanner scanner;

// * Invocation to the scanner
private int yylex () {
    int token=0;
    try { 
		token=scanner.yylex();
		this.yylval = scanner.getYylval(); 
    } catch(Throwable e) {
	    System.err.println ("Lexical error in line " + scanner.getLine()+
		" and column "+scanner.getColumn()+":\n\t"+e); 
    }
    return token;
}

// * Syntax error handler
public void yyerror (String error) {
    System.err.println ("Syntax error in line " + scanner.getLine()+
		" and column "+scanner.getColumn()+":\n\t"+error);
}

// * Constructor
public Parser(Scanner scanner) {
	this.scanner= scanner;
	//this.yydebug = true;
}

private ASTNode astnode;

public Object getAST() {
	return astnode;
}
//#line 554 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 42 "../../src/parser/parser.y"
{List<Definition> list = (List<Definition>) val_peek(8);
																		List<Statement> varDefinitions = (List<Statement>) val_peek(2);
																		List<Statement> statements = (List<Statement>) val_peek(1);
																		varDefinitions.addAll(statements);
																		list.add(new FuncDefinition(scanner.getLine(), scanner.getColumn(), "MAIN", new FunctionType(new ArrayList<VarDefinition>(), VoidType.getInstance()), varDefinitions));
																		Program prog= new Program(scanner.getLine(), scanner.getColumn(), list);
																		astnode=prog;}
break;
case 2:
//#line 50 "../../src/parser/parser.y"
{List<Definition> list = (List<Definition>) val_peek(1);
																		list.add((Definition) val_peek(0));
																		yyval=list;}
break;
case 3:
//#line 53 "../../src/parser/parser.y"
{List<Definition> list = (List<Definition>) val_peek(1);
																		list.addAll((List<Definition>) val_peek(0));
																		yyval=list;}
break;
case 4:
//#line 56 "../../src/parser/parser.y"
{yyval= new ArrayList<Definition>();}
break;
case 5:
//#line 59 "../../src/parser/parser.y"
{List<Statement> varDefinitions = (List<Statement>) val_peek(2);
																					List<Statement> statements = (List<Statement>) val_peek(1);
																					varDefinitions.addAll(statements);
																					yyval= new FuncDefinition(scanner.getLine(), scanner.getColumn(), (String) val_peek(7), new FunctionType((List<VarDefinition>) val_peek(5),(Type) val_peek(8)), varDefinitions);}
break;
case 6:
//#line 63 "../../src/parser/parser.y"
{List<Statement> varDefinitions = (List<Statement>) val_peek(2);
																					List<Statement> statements = (List<Statement>) val_peek(1);
																					varDefinitions.addAll(statements);
																					yyval= new FuncDefinition(scanner.getLine(), scanner.getColumn(), (String) val_peek(7),  new FunctionType((List<VarDefinition>) val_peek(5), VoidType.getInstance()), varDefinitions);}
break;
case 7:
//#line 69 "../../src/parser/parser.y"
{yyval=val_peek(0);}
break;
case 8:
//#line 70 "../../src/parser/parser.y"
{yyval= new ArrayList<Statement>();}
break;
case 9:
//#line 73 "../../src/parser/parser.y"
{List<Statement> list = (List<Statement>) val_peek(1);
																				list.add((Statement) val_peek(0));
																				yyval= list;}
break;
case 10:
//#line 76 "../../src/parser/parser.y"
{List<Statement> list = new ArrayList<Statement>();
		  																		list.add((Statement) val_peek(0));
		  																		yyval=list;}
break;
case 11:
//#line 81 "../../src/parser/parser.y"
{yyval= new Read(scanner.getLine(), scanner.getColumn(), (List<Expression>) val_peek(1));}
break;
case 12:
//#line 82 "../../src/parser/parser.y"
{yyval= new Write(scanner.getLine(), scanner.getColumn(), (List<Expression>) val_peek(1));}
break;
case 13:
//#line 83 "../../src/parser/parser.y"
{yyval= new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), (Expression) val_peek(1));}
break;
case 14:
//#line 84 "../../src/parser/parser.y"
{yyval= new Invocation(scanner.getLine(), scanner.getColumn(), (List<Expression>) val_peek(2), new Variable(scanner.getLine(), scanner.getColumn(), (String) val_peek(4)));}
break;
case 15:
//#line 85 "../../src/parser/parser.y"
{yyval= new WhileStatement(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), (List<Statement>) val_peek(0));}
break;
case 16:
//#line 86 "../../src/parser/parser.y"
{yyval= val_peek(0);}
break;
case 17:
//#line 87 "../../src/parser/parser.y"
{yyval= new Return(scanner.getLine(), scanner.getColumn(),(Expression) val_peek(1));}
break;
case 18:
//#line 90 "../../src/parser/parser.y"
{yyval= new IfStatement(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(3), (List<Statement>) val_peek(1), (List<Statement>) val_peek(0));}
break;
case 19:
//#line 93 "../../src/parser/parser.y"
{yyval= val_peek(0);}
break;
case 20:
//#line 94 "../../src/parser/parser.y"
{yyval= new ArrayList<Statement>();}
break;
case 21:
//#line 97 "../../src/parser/parser.y"
{yyval= val_peek(1);}
break;
case 22:
//#line 98 "../../src/parser/parser.y"
{List<Statement> list = new ArrayList<Statement>();
 																				list.add((Statement) val_peek(0));
 																				yyval= list;}
break;
case 23:
//#line 106 "../../src/parser/parser.y"
{yyval= new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), "+", (Expression) val_peek(0));}
break;
case 24:
//#line 107 "../../src/parser/parser.y"
{yyval= new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), "-", (Expression) val_peek(0));}
break;
case 25:
//#line 108 "../../src/parser/parser.y"
{yyval= new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), "*", (Expression) val_peek(0));}
break;
case 26:
//#line 109 "../../src/parser/parser.y"
{yyval= new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), "/", (Expression) val_peek(0));}
break;
case 27:
//#line 110 "../../src/parser/parser.y"
{yyval= new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), "%", (Expression) val_peek(0));}
break;
case 28:
//#line 111 "../../src/parser/parser.y"
{yyval= new UnaryMinus(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(0));}
break;
case 29:
//#line 112 "../../src/parser/parser.y"
{yyval=val_peek(1);}
break;
case 30:
//#line 113 "../../src/parser/parser.y"
{yyval= new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), ">", (Expression) val_peek(0));}
break;
case 31:
//#line 114 "../../src/parser/parser.y"
{yyval= new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), "<", (Expression) val_peek(0));}
break;
case 32:
//#line 115 "../../src/parser/parser.y"
{yyval= new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), "<=", (Expression) val_peek(0));}
break;
case 33:
//#line 116 "../../src/parser/parser.y"
{yyval= new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), ">=", (Expression) val_peek(0));}
break;
case 34:
//#line 117 "../../src/parser/parser.y"
{yyval= new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), "==", (Expression) val_peek(0));}
break;
case 35:
//#line 118 "../../src/parser/parser.y"
{yyval= new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), "!=", (Expression) val_peek(0));}
break;
case 36:
//#line 119 "../../src/parser/parser.y"
{yyval= new Logical(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), "&&", (Expression) val_peek(0));}
break;
case 37:
//#line 120 "../../src/parser/parser.y"
{yyval= new Logical(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(2), "||", (Expression) val_peek(0));}
break;
case 38:
//#line 121 "../../src/parser/parser.y"
{yyval= new Invocation(scanner.getLine(), scanner.getColumn(), (List<Expression>) val_peek(1), new Variable(scanner.getLine(), scanner.getColumn(), (String) val_peek(3)));}
break;
case 39:
//#line 122 "../../src/parser/parser.y"
{yyval= new UnaryNot(scanner.getLine(), scanner.getColumn(), (Expression) val_peek(0));}
break;
case 40:
//#line 123 "../../src/parser/parser.y"
{yyval= new Cast(scanner.getLine(), scanner.getColumn(), (Type) val_peek(2), (Expression) val_peek(0));}
break;
case 41:
//#line 124 "../../src/parser/parser.y"
{yyval= new FieldAccess(scanner.getLine(), scanner.getColumn(),(String) val_peek(0), (Expression) val_peek(2) );}
break;
case 42:
//#line 125 "../../src/parser/parser.y"
{yyval= new Indexing(scanner.getLine(), scanner.getColumn(),(Expression) val_peek(3), (Expression) val_peek(1) );}
break;
case 43:
//#line 126 "../../src/parser/parser.y"
{yyval= new Variable(scanner.getLine(), scanner.getColumn(), (String) val_peek(0));}
break;
case 44:
//#line 127 "../../src/parser/parser.y"
{yyval= new IntLiteral(scanner.getLine(), scanner.getColumn(), (int) val_peek(0));}
break;
case 45:
//#line 128 "../../src/parser/parser.y"
{yyval= new RealLiteral(scanner.getLine(), scanner.getColumn(), (double) val_peek(0));}
break;
case 46:
//#line 129 "../../src/parser/parser.y"
{yyval= new CharLiteral(scanner.getLine(), scanner.getColumn(), (char) val_peek(0));}
break;
case 47:
//#line 132 "../../src/parser/parser.y"
{List<Expression> list = (List<Expression>) val_peek(2);
																				list.add((Expression) val_peek(0));
																				yyval=list;}
break;
case 48:
//#line 135 "../../src/parser/parser.y"
{List<Expression> list = new ArrayList<Expression>();
		   																		list.add((Expression) val_peek(0));
		   																		yyval= list;}
break;
case 49:
//#line 140 "../../src/parser/parser.y"
{yyval=val_peek(0);}
break;
case 50:
//#line 141 "../../src/parser/parser.y"
{yyval= new ArrayList<Expression>();}
break;
case 51:
//#line 144 "../../src/parser/parser.y"
{List<VarDefinition> list = new ArrayList<VarDefinition>();
																				List<Variable> variables = (List<Variable>) val_peek(1);
																				for(Variable var: variables){
																					list.add(new VarDefinition(var.getLine(),var.getColumn(), var.getName(), (Type) val_peek(2)));
																					}
																				yyval= list;
																				}
break;
case 52:
//#line 153 "../../src/parser/parser.y"
{List<Variable> variables = (List<Variable>) val_peek(2);
																			    Variable var= new Variable(scanner.getLine(), scanner.getColumn(), (String) val_peek(0)); 
																				if(variables.contains(var)){
																					new ErrorType(scanner.getLine(), scanner.getColumn(), "Duplicated variable");
																				}
																				else{
	  																				variables.add(var);
	  																			}
	  																			yyval= variables;}
break;
case 53:
//#line 162 "../../src/parser/parser.y"
{List<Variable> variable = new ArrayList<Variable>();
	  																			variable.add(new Variable(scanner.getLine(), scanner.getColumn(), (String) val_peek(0)));
	  																			yyval=variable;}
break;
case 54:
//#line 167 "../../src/parser/parser.y"
{List<VarDefinition> vars = (List<VarDefinition>) val_peek(1);
																				vars.addAll((List<VarDefinition>) val_peek(0));
																				yyval= vars;}
break;
case 55:
//#line 170 "../../src/parser/parser.y"
{yyval= val_peek(0);}
break;
case 56:
//#line 173 "../../src/parser/parser.y"
{yyval= val_peek(0);}
break;
case 57:
//#line 174 "../../src/parser/parser.y"
{yyval= new ArrayList<VarDefinition>();}
break;
case 58:
//#line 178 "../../src/parser/parser.y"
{List<Expression> list = (List<Expression>) val_peek(2);
																				list.add((Expression) val_peek(0));
																				yyval= list;}
break;
case 59:
//#line 181 "../../src/parser/parser.y"
{List<Expression> list = new ArrayList<Expression>();
		 																		list.add((Expression) val_peek(0));
		 																		yyval=list;}
break;
case 60:
//#line 186 "../../src/parser/parser.y"
{yyval= val_peek(0);}
break;
case 61:
//#line 187 "../../src/parser/parser.y"
{yyval= new ArrayList<Expression>();}
break;
case 62:
//#line 190 "../../src/parser/parser.y"
{yyval= new VarDefinition(scanner.getLine(), scanner.getColumn(), (String) val_peek(0), (Type) val_peek(1));}
break;
case 63:
//#line 193 "../../src/parser/parser.y"
{List<VarDefinition> list = (List<VarDefinition>) val_peek(2);
		  																		list.add((VarDefinition)val_peek(0));
		  																		yyval= list;}
break;
case 64:
//#line 196 "../../src/parser/parser.y"
{List<VarDefinition> list = new ArrayList<VarDefinition>();
		  																		list.add((VarDefinition)val_peek(0));
		  																		yyval= list;}
break;
case 65:
//#line 200 "../../src/parser/parser.y"
{yyval=val_peek(0);}
break;
case 66:
//#line 201 "../../src/parser/parser.y"
{yyval= new ArrayList<VarDefinition>();}
break;
case 67:
//#line 204 "../../src/parser/parser.y"
{List<RecordField> list = new ArrayList<RecordField>();
																				List<Variable> variables = (List<Variable>) val_peek(1);
																				for(Variable var: variables){
																					list.add(new RecordField(var.getName(),(Type)val_peek(2)));
																					}
																				yyval= list;
																				}
break;
case 68:
//#line 213 "../../src/parser/parser.y"
{List<RecordField> list = (List<RecordField>) val_peek(1);
																				for(RecordField rf: (List<RecordField>) val_peek(0)){
																					if(list.contains(rf)){
																						new ErrorType(scanner.getLine(), scanner.getColumn(), "Duplicated field");
																					}
																					else{
																						list.add(rf);
																					}
																				}
																				yyval= list;}
break;
case 69:
//#line 223 "../../src/parser/parser.y"
{yyval= val_peek(0);}
break;
case 70:
//#line 228 "../../src/parser/parser.y"
{yyval = IntType.getInstance();}
break;
case 71:
//#line 229 "../../src/parser/parser.y"
{yyval = CharType.getInstance();}
break;
case 72:
//#line 230 "../../src/parser/parser.y"
{yyval = RealType.getInstance();}
break;
case 73:
//#line 231 "../../src/parser/parser.y"
{yyval = ArrayType.prepareArray(scanner.getLine(), scanner.getColumn(),(int) val_peek(1), (Type) val_peek(3));}
break;
case 74:
//#line 232 "../../src/parser/parser.y"
{yyval = new RecordType((List<RecordField>) val_peek(1));}
break;
//#line 1066 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
