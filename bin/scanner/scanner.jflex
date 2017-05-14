// ************  User Code  ********************

package scanner;
import parser.Parser;

%%
// ************  Options ********************

// % debug // * For debugging purposes
%byaccj
%class Scanner
%public
%unicode
%line
%column

%{
// ************  Fields and methods ********************

// * To get the line number
public int getLine() { 
	// * JFlex starts in zero
	return this.yyline+1;
}

// * To get the column number
public int getColumn() { 
	// * JFlex starts in zero
	return yycolumn+1;
}

// * Semantic value of the token
private Object yylval;
public Object getYylval() {
	return this.yylval;
}


%}

// ************  Macros ********************
IntConstant = [0-9]+
LETTER = [a-zA-Z]
DIGIT = [0-9]
VARIABLE = [a-zA-Z][a-zA-Z0-9]*
REAL = {DIGIT}+"."{DIGIT}*|{DIGIT}*"."{DIGIT}+
MANTISSA = ({REAL}|{IntConstant})[Ee]["+""-"]?{IntConstant}


%%
// ************  Lexical Rules ********************

// * Pass ones.
[ \t\n\r\f]          {}
"/*"~"*/"            {}
"//".* \n            {}

// * Keywords
"int"                {return Parser.INT;}
"char"               {return Parser.CHAR;}
"double"             {return Parser.DOUBLE;}
"return"             {return Parser.RETURN;}
"while"              {return Parser.WHILE;}
"main"               {return Parser.MAIN;}
"void"               {return Parser.VOID;}
"struct"             {return Parser.STRUCT;}
"if"                 {return Parser.IF;}
"else"               {return Parser.ELSE;}
"read"               {return Parser.READ;}
"write"              {return Parser.WRITE;}


// * Constants
{IntConstant}		 {this.yylval = new Integer(yytext());
         			  return Parser.INT_CONSTANT;  }
{MANTISSA}           {String[] parts = yytext().split("E");
							if(parts.length==1){
							  	parts= parts[0].split("e");
							  }
							  Double first = Double.parseDouble(parts[0]);
							  int second = Integer.parseInt(parts[1]);
							  this.yylval = first*Math.pow(10,second);
							  return Parser.REAL_CONSTANT;}
{REAL}               {this.yylval = new Double(yytext());
                                        return Parser.REAL_CONSTANT; }
'\\{IntConstant}'    {int value= Integer.parseInt(yytext().substring(2,yytext().length()-1));
						this.yylval = new Character((char)value);
						return Parser.CHAR_CONSTANT;}
'\\n'|'\\t'          {String specialString= yytext().replace("'", "").replace("\\n", "\n").replace("\\t", "\t");
        	           this.yylval = new Character(specialString.charAt(0));
					   return Parser.CHAR_CONSTANT;}
'.'                  {this.yylval = new Character(yycharat(1));
					   return Parser.CHAR_CONSTANT;}
{VARIABLE}           {this.yylval = yytext();
					   return Parser.ID;}
			
					   
// * Single char operands
"+"|"/"|
"-"|";"|
"="|")"|
"("|"{"|
"}"|","|
"["|"]"|
"!"|"*"|
"<"|"%"|
">"|"."                 {return (int) yycharat(0);}


// * Double char Operands

"||"                  {return Parser.OR;}
"&&"                  {return Parser.AND;}
"=="                  {return Parser.EQUAL;}
"<="                  {return Parser.LESS_EQUAL;}
">="                  {return Parser.GREATER_EQUAL;}
"!="                  {return Parser.NOT_EQUAL;}



// * LastOne the error one anything else.
.                    {System.err.println("error in line: "+ getLine() + " and column: "+getColumn());}
 

