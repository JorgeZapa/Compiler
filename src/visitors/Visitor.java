package visitors;

import ast.Program;
import ast.definitions.*;
import ast.expresions.*;
import ast.statements.*;
import ast.types.*;

public interface Visitor {
	
	//Program
	public Object visit(Program p, Object o);
	
	
	//Defintions
	public Object visit(VarDefinition varDef, Object o);
	
	public Object visit(FuncDefinition funcDef, Object o);
	
	
	//Statements
	public Object visit(Assignment assign, Object o);
	
	public Object visit(IfStatement ifStmt, Object o);

	public Object visit(Invocation inv, Object o);
	
	public Object visit(Return ret, Object o);
	
	public Object visit(WhileStatement whileStmt, Object o);
	
	public Object visit(Write write, Object o);
	
	public Object visit(Read read, Object o);
	
	//Expressions
	public Object visit(Arithmetic arith, Object o);
	
	public Object visit(Cast cast, Object o);
	
	public Object visit(CharLiteral charLit, Object o);
	
	public Object visit(Comparison comp, Object o);
	
	public Object visit(FieldAccess fa, Object o);
	
	public Object visit(Indexing indexing, Object o);
	
	public Object visit(IntLiteral intLit, Object o);
	
	public Object visit(Logical log, Object o);
	
	public Object visit(RealLiteral realLit, Object o);
	
	public Object visit(UnaryMinus uMin, Object o);
	
	public Object visit(UnaryNot unaryNot, Object o);
	
	public Object visit(Variable var, Object o);
	
	//Types
	public Object visit(ArrayType aType, Object o);
	
	public Object visit(CharType charType, Object o);
	
	public Object visit(FunctionType funcType, Object o);
	
	public Object visit(RecordField var, Object o);
	
	public Object visit(IntType intType, Object o);
	
	public Object visit(RealType realType, Object o);
	
	public Object visit(VoidType vType, Object o);
	
	public Object visit (ErrorType eType, Object o);

	public Object visit(RecordType recordType, Object o);


	
	
	
	

}
