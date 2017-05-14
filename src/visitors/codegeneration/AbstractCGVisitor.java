package visitors.codegeneration;

import ast.Program;
import ast.definitions.FuncDefinition;
import ast.definitions.VarDefinition;
import ast.expresions.Arithmetic;
import ast.expresions.Cast;
import ast.expresions.CharLiteral;
import ast.expresions.Comparison;
import ast.expresions.FieldAccess;
import ast.expresions.Indexing;
import ast.expresions.IntLiteral;
import ast.expresions.Logical;
import ast.expresions.RealLiteral;
import ast.expresions.UnaryMinus;
import ast.expresions.UnaryNot;
import ast.expresions.Variable;
import ast.statements.Assignment;
import ast.statements.IfStatement;
import ast.statements.Invocation;
import ast.statements.Read;
import ast.statements.Return;
import ast.statements.WhileStatement;
import ast.statements.Write;
import ast.types.ArrayType;
import ast.types.CharType;
import ast.types.ErrorType;
import ast.types.FunctionType;
import ast.types.IntType;
import ast.types.RealType;
import ast.types.RecordField;
import ast.types.RecordType;
import ast.types.VoidType;
import visitors.Visitor;

public abstract class AbstractCGVisitor implements Visitor {

	@Override
	public Object visit(Program p, Object o) {
		throw new IllegalStateException("This function can't be applied to Program");
	}

	@Override
	public Object visit(VarDefinition varDef, Object o) {
		throw new IllegalStateException("This function can't be applied to varDefinition");
	}

	@Override
	public Object visit(FuncDefinition funcDef, Object o) {
		throw new IllegalStateException("This function can't be applied to FuncDefinition");
	}

	@Override
	public Object visit(Assignment assign, Object o) {
		throw new IllegalStateException("This function can't be applied to Assignment");
	}

	@Override
	public Object visit(IfStatement ifStmt, Object o) {
		throw new IllegalStateException("This function can't be applied to IfStatement");
	}

	@Override
	public Object visit(Invocation inv, Object o) {
		throw new IllegalStateException("This function can't be applied to Invocation");
	}

	@Override
	public Object visit(Return ret, Object o) {
		throw new IllegalStateException("This function can't be applied to Return");
	}

	@Override
	public Object visit(WhileStatement whileStmt, Object o) {
		throw new IllegalStateException("This function can't be applied to WhileStatement");
	}

	@Override
	public Object visit(Write write, Object o) {
		throw new IllegalStateException("This function can't be applied to Write");
	}

	@Override
	public Object visit(Read read, Object o) {
		throw new IllegalStateException("This function can't be applied to Read");
	}

	@Override
	public Object visit(Arithmetic arith, Object o) {
		throw new IllegalStateException("This function can't be applied to Arithmetic");
	}

	@Override
	public Object visit(Cast cast, Object o) {
		throw new IllegalStateException("This function can't be applied to Cast");
	}

	@Override
	public Object visit(CharLiteral charLit, Object o) {
		throw new IllegalStateException("This function can't be applied to CharLiteral");
	}

	@Override
	public Object visit(Comparison comp, Object o) {
		throw new IllegalStateException("This function can't be applied to Comparison");
	}

	@Override
	public Object visit(FieldAccess fa, Object o) {
		throw new IllegalStateException("This function can't be applied to FieldAccess");
	}

	@Override
	public Object visit(Indexing indexing, Object o) {
		throw new IllegalStateException("This function can't be applied to Indexing");
	}

	@Override
	public Object visit(IntLiteral intLit, Object o) {
		throw new IllegalStateException("This function can't be applied to IntLiteral");
	}

	@Override
	public Object visit(Logical log, Object o) {
		throw new IllegalStateException("This function can't be applied to Logical");
	}

	@Override
	public Object visit(RealLiteral realLit, Object o) {
		throw new IllegalStateException("This function can't be applied to RealLiteral");
	}

	@Override
	public Object visit(UnaryMinus uMin, Object o) {
		throw new IllegalStateException("This function can't be applied to UnaryMinus");
	}

	@Override
	public Object visit(UnaryNot unaryNot, Object o) {
		throw new IllegalStateException("This function can't be applied to UnaryNot");
	}

	@Override
	public Object visit(Variable var, Object o) {
		throw new IllegalStateException("This function can't be applied to Variable");
	}

	@Override
	public Object visit(ArrayType aType, Object o) {
		throw new IllegalStateException("This function can't be applied to ArrayType");
	}

	@Override
	public Object visit(CharType charType, Object o) {
		throw new IllegalStateException("This function can't be applied to CharType");
	}

	@Override
	public Object visit(FunctionType funcType, Object o) {
		throw new IllegalStateException("This function can't be applied to FunctionType");
	}

	@Override
	public Object visit(RecordField var, Object o) {
		throw new IllegalStateException("This function can't be applied to RecordField");
	}

	@Override
	public Object visit(IntType intType, Object o) {
		throw new IllegalStateException("This function can't be applied to IntType");
	}

	@Override
	public Object visit(RealType realType, Object o) {
		throw new IllegalStateException("This function can't be applied to RealType");
	}

	@Override
	public Object visit(VoidType vType, Object o) {
		throw new IllegalStateException("This function can't be applied to VoidType");
	}

	@Override
	public Object visit(ErrorType eType, Object o) {
		throw new IllegalStateException("This function can't be applied to ErrorType");
	}

	@Override
	public Object visit(RecordType recordType, Object o) {
		throw new IllegalStateException("This function can't be applied to RecordType");
	}

}
