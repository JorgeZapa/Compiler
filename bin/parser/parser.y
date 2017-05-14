%{
// * Java declarations
//   This code is copied in the beginning of the generated Java file
import scanner.Scanner;

import java.util.*;
import ast.*;
import ast.others.*;
import ast.definitions.*;
import ast.expresions.*;
import ast.statements.*;
import ast.types.*;
import errorhandler.EH;
%}

// * Yacc declarations
//   Token definition
%token INT_CONSTANT CHAR_CONSTANT REAL_CONSTANT
%token WHILE RETURN MAIN INT CHAR DOUBLE
%token STRUCT ID AND OR LESS_THAN IF ELSE VOID
%token READ WRITE EQUAL NOT_EQUAL LESS_EQUAL GREATER_EQUAL


//lower precedence
%nonassoc IF
%nonassoc ELSE
%right '='
%left AND OR NEGATION 
%left '<' '>' LESS_EQUAL GREATER_EQUAL EQUAL NOT_EQUAL
%left '+'  '-'
%left '*'  '/' '%'
%right UNARY_MINUS
%nonassoc CAST
%nonassoc '[' ']'    // * Array item acess. 
%left '.'            // * Field access.
%nonassoc '(' ')'    // * Valid for both (exp) cast as both have parenth
//More precedence

%%
// * Actions

program: body VOID MAIN '(' ')' '{' varDefinitionOP statementOP '}'  {List<Definition> list = (List<Definition>) $1;
																		List<Statement> varDefinitions = (List<Statement>) $7;
																		List<Statement> statements = (List<Statement>) $8;
																		varDefinitions.addAll(statements);
																		list.add(new FuncDefinition(scanner.getLine(), scanner.getColumn(), "MAIN", new FunctionType(new ArrayList<VarDefinition>(), VoidType.getInstance()), varDefinitions));
																		Program prog= new Program(scanner.getLine(), scanner.getColumn(), list);
																		astnode=prog;}
	   ;																	
body: body funDefinition   											  {List<Definition> list = (List<Definition>) $1;
																		list.add((Definition) $2);
																		$$=list;}
	| body varDefinition											  {List<Definition> list = (List<Definition>) $1;
																		list.addAll((List<Definition>) $2);
																		$$=list;}
	| /* empty */													  {$$= new ArrayList<Definition>();}
	
	
funDefinition: type ID '(' parameterOP ')' '{' varDefinitionOP statementOP '}' {List<Statement> varDefinitions = (List<Statement>) $7;
																					List<Statement> statements = (List<Statement>) $8;
																					varDefinitions.addAll(statements);
																					$$= new FuncDefinition(scanner.getLine(), scanner.getColumn(), (String) $2, new FunctionType((List<VarDefinition>) $4,(Type) $1), varDefinitions);}
			 | VOID ID '(' parameterOP ')' '{' varDefinitionOP statementOP '}'{List<Statement> varDefinitions = (List<Statement>) $7;
																					List<Statement> statements = (List<Statement>) $8;
																					varDefinitions.addAll(statements);
																					$$= new FuncDefinition(scanner.getLine(), scanner.getColumn(), (String) $2,  new FunctionType((List<VarDefinition>) $4, VoidType.getInstance()), varDefinitions);}
			 ;
			 
statementOP: statementS														  {$$=$1;}
		   | /* empty */													  {$$= new ArrayList<Statement>();}
		   ;

statementS: statementS statement 											  {List<Statement> list = (List<Statement>) $1;
																				list.add((Statement) $2);
																				$$= list;}
		  | statement														  {List<Statement> list = new ArrayList<Statement>();
		  																		list.add((Statement) $1);
		  																		$$=list;}
		  ;

statement: READ expressionS ';'												  {$$= new Read(scanner.getLine(), scanner.getColumn(), (List<Expression>) $2);}
		 | WRITE expressionS ';'											  {$$= new Write(scanner.getLine(), scanner.getColumn(), (List<Expression>) $2);}
		 | expression '=' expression ';'									  {$$= new Assignment(scanner.getLine(), scanner.getColumn(), (Expression) $1, (Expression) $3);}
		 | ID '(' argumentOP ')' ';'										  {$$= new Invocation(scanner.getLine(), scanner.getColumn(), (List<Expression>) $3, new Variable(scanner.getLine(), scanner.getColumn(), (String) $1));}
		 | WHILE '(' expression ')' complexStatement					      {$$= new WhileStatement(scanner.getLine(), scanner.getColumn(), (Expression) $3, (List<Statement>) $5);}
		 | ifStatement									  					  {$$= $1;}
		 | RETURN expression ';'											  {$$= new Return(scanner.getLine(), scanner.getColumn(),(Expression) $2);}
		 ;

ifStatement: IF '(' expression ')' complexStatement else	                  {$$= new IfStatement(scanner.getLine(), scanner.getColumn(), (Expression) $3, (List<Statement>) $5, (List<Statement>) $6);}				 
		 ;		 
		 
else: ELSE 	complexStatement												  {$$= $2;}					  		
	| /* empty */ %prec IF 													  {$$= new ArrayList<Statement>();}
	;
	
complexStatement: '{' statementOP '}'										  {$$= $2;}									
 				| statement													  {List<Statement> list = new ArrayList<Statement>();
 																				list.add((Statement) $1);
 																				$$= list;}
 				;
 				
// * Index tiene cardinalidad 1?	
// * comparadores con operador, (debería ser obvio que si)
// * En field acess el ID es un string  yno una variable?
expression: expression '+' expression										  {$$= new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, "+", (Expression) $3);}
		  | expression '-' expression										  {$$= new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, "-", (Expression) $3);}
		  | expression '*' expression										  {$$= new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, "*", (Expression) $3);}
		  | expression '/' expression										  {$$= new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, "/", (Expression) $3);}
		  | expression '%' expression										  {$$= new Arithmetic(scanner.getLine(), scanner.getColumn(), (Expression) $1, "%", (Expression) $3);}
		  | '-' expression %prec UNARY_MINUS								  {$$= new UnaryMinus(scanner.getLine(), scanner.getColumn(), (Expression) $2);}
		  | '(' expression ')'												  {$$=$2;}
		  | expression '>' expression										  {$$= new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) $1, ">", (Expression) $3);}
		  | expression '<' expression										  {$$= new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) $1, "<", (Expression) $3);}
		  | expression LESS_EQUAL expression								  {$$= new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) $1, "<=", (Expression) $3);}
		  | expression GREATER_EQUAL expression								  {$$= new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) $1, ">=", (Expression) $3);}
		  | expression EQUAL expression										  {$$= new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) $1, "==", (Expression) $3);}
		  | expression NOT_EQUAL expression									  {$$= new Comparison(scanner.getLine(), scanner.getColumn(), (Expression) $1, "!=", (Expression) $3);}
		  | expression AND expression										  {$$= new Logical(scanner.getLine(), scanner.getColumn(), (Expression) $1, "&&", (Expression) $3);}
		  | expression OR expression										  {$$= new Logical(scanner.getLine(), scanner.getColumn(), (Expression) $1, "||", (Expression) $3);}
		  | ID '(' argumentOP ')'											  {$$= new Invocation(scanner.getLine(), scanner.getColumn(), (List<Expression>) $3, new Variable(scanner.getLine(), scanner.getColumn(), (String) $1));}
		  | '!' expression %prec NEGATION									  {$$= new UnaryNot(scanner.getLine(), scanner.getColumn(), (Expression) $2);}
		  | '(' type ')' expression	 %prec CAST								  {$$= new Cast(scanner.getLine(), scanner.getColumn(), (Type) $2, (Expression) $4);}
		  | expression '.' ID      	 										  {$$= new FieldAccess(scanner.getLine(), scanner.getColumn(),(String) $3, (Expression) $1 );}
		  | expression '[' expression ']'									  {$$= new Indexing(scanner.getLine(), scanner.getColumn(),(Expression) $1, (Expression) $3 );}
		  |ID																  {$$= new Variable(scanner.getLine(), scanner.getColumn(), (String) $1);}
		  |INT_CONSTANT														  {$$= new IntLiteral(scanner.getLine(), scanner.getColumn(), (int) $1);}
		  |REAL_CONSTANT													  {$$= new RealLiteral(scanner.getLine(), scanner.getColumn(), (double) $1);}
		  |CHAR_CONSTANT													  {$$= new CharLiteral(scanner.getLine(), scanner.getColumn(), (char) $1);}
		  ;

expressionS: expressionS ',' expression										  {List<Expression> list = (List<Expression>) $1;
																				list.add((Expression) $3);
																				$$=list;}
		   | expression 													  {List<Expression> list = new ArrayList<Expression>();
		   																		list.add((Expression) $1);
		   																		$$= list;}
		   ;

expressionOP: expressionS													  {$$=$1;}
			| /* empty */													  {$$= new ArrayList<Expression>();}
			;

varDefinition: type IDList ';'												  {List<VarDefinition> list = new ArrayList<VarDefinition>();
																				List<Variable> variables = (List<Variable>) $2;
																				for(Variable var: variables){
																					list.add(new VarDefinition(var.getLine(),var.getColumn(), var.getName(), (Type) $1));
																					}
																				$$= list;
																				}
			 ;

IDList: IDList ',' ID														  {List<Variable> variables = (List<Variable>) $1;
																			    Variable var= new Variable(scanner.getLine(), scanner.getColumn(), (String) $3); 
																				if(variables.contains(var)){
																					new ErrorType(scanner.getLine(), scanner.getColumn(), "Duplicated variable");
																				}
																				else{
	  																				variables.add(var);
	  																			}
	  																			$$= variables;}
	  | ID																	  {List<Variable> variable = new ArrayList<Variable>();
	  																			variable.add(new Variable(scanner.getLine(), scanner.getColumn(), (String) $1));
	  																			$$=variable;}
	  ;

varDefinitionS: varDefinitionS varDefinition								  {List<VarDefinition> vars = (List<VarDefinition>) $1;
																				vars.addAll((List<VarDefinition>) $2);
																				$$= vars;}
			  | varDefinition												  {$$= $1;}
			  ;
			  
varDefinitionOP: varDefinitionS												  {$$= $1;}
			   | /* empty */												  {$$= new ArrayList<VarDefinition>();}
			   ;

// * arguments for the method invoc
argumentS: argumentS ',' expression											  {List<Expression> list = (List<Expression>) $1;
																				list.add((Expression) $3);
																				$$= list;}
		 | expression														  {List<Expression> list = new ArrayList<Expression>();
		 																		list.add((Expression) $1);
		 																		$$=list;}
		 ;
		 
argumentOP: argumentS														  {$$= $1;}
		  | /* empty */														  {$$= new ArrayList<Expression>();}
		  ;

param: type ID																  {$$= new VarDefinition(scanner.getLine(), scanner.getColumn(), (String) $2, (Type) $1);}
	 ;

parameterS: parameterS ',' param											  {List<VarDefinition> list = (List<VarDefinition>) $1;
		  																		list.add((VarDefinition)$3);
		  																		$$= list;}									 
		  | param															  {List<VarDefinition> list = new ArrayList<VarDefinition>();
		  																		list.add((VarDefinition)$1);
		  																		$$= list;}
		  ;
parameterOP: parameterS														  {$$=$1;}
		   |/* empty */ 													  {$$= new ArrayList<VarDefinition>();}
		   ;

campo: type IDList ';'												  		  {List<RecordField> list = new ArrayList<RecordField>();
																				List<Variable> variables = (List<Variable>) $2;
																				for(Variable var: variables){
																					list.add(new RecordField(var.getName(),(Type)$1));
																					}
																				$$= list;
																				}
			 ;

campoS: campoS campo										  				  {List<RecordField> list = (List<RecordField>) $1;
																				for(RecordField rf: (List<RecordField>) $2){
																					if(list.contains(rf)){
																						new ErrorType(scanner.getLine(), scanner.getColumn(), "Duplicated field");
																					}
																					else{
																						list.add(rf);
																					}
																				}
																				$$= list;}
	  | campo												  				  {$$= $1;}
			  ;

//* Linea y colu al ser node?
//* record field tambien es un tipo?  
type: INT													{$$ = IntType.getInstance();}
	| CHAR													{$$ = CharType.getInstance();}
	| DOUBLE												{$$ = RealType.getInstance();}
	| type '[' INT_CONSTANT ']' 							{$$ = ArrayType.prepareArray(scanner.getLine(), scanner.getColumn(),(int) $3, (Type) $1);}
	| STRUCT '{' campoS '}'									{$$ = new RecordType((List<RecordField>) $3);}
	;
%%

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
