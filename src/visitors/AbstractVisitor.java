package visitors;

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

public abstract class AbstractVisitor implements Visitor {

	//Creo que no funciona por que coge el tipo AbstractVisitor como el parametro. Preguntar
	@Override
	public Object visit(Program p, Object o) {
		p.getDefinitions().stream().forEach(stmt -> stmt.accept(this, null));
		return null;
	}

	@Override
	public Object visit(VarDefinition varDef, Object o) {
		varDef.getType().accept(this, null);
		return null;
	}

	@Override
	public Object visit(FuncDefinition funcDef, Object o) {
		funcDef.getType().accept(this, null);
		funcDef.getStatements().stream().forEach(stmt -> stmt.accept(this, null));
		return null;
	}

	@Override
	public Object visit(Assignment assign, Object o) {
		assign.getFirstExpression().accept(this, null);
		assign.getSecondExpression().accept(this, null);
		return null;
	}

	@Override
	public Object visit(IfStatement ifStmt, Object o) {
		ifStmt.getCondition().accept(this, null);
		ifStmt.getIfBody().stream().forEach(stmt -> stmt.accept(this, null));
		ifStmt.getElseBody().stream().forEach(stmt -> stmt.accept(this, null));
		return null;
	}

	@Override
	public Object visit(Invocation inv, Object o) {
		return null;
	}

	@Override
	public Object visit(Return ret, Object o) {
		ret.getExpression().accept(this, null);
		return null;
	}

	@Override
	public Object visit(WhileStatement whileStmt, Object o) {
		whileStmt.getCondition().accept(this, null);
		whileStmt.getStatements().stream().forEach(stmt -> stmt.accept(this, null));
		return null;
	}

	@Override
	public Object visit(Write write, Object o) {
		write.getExpressions().forEach(exp -> exp.accept(this, null));
		return null;
	}

	@Override
	public Object visit(Read read, Object o) {
		read.getExpressions().stream().forEach(exp -> exp.accept(this, null));
		return null;
	}

	@Override
	public Object visit(Arithmetic arith, Object o) {
		arith.getFirstExpression().accept(this, null);
		arith.getSecondExpression().accept(this, null);
		return null;
	}

	@Override
	public Object visit(Cast cast, Object o) {
		cast.getType().accept(this, null);
		cast.getExpression().accept(this, null);
		return null;
	}

	@Override
	public Object visit(CharLiteral charLit, Object o) {
		return null;
	}

	@Override
	public Object visit(Comparison comp, Object o) {
		comp.getFirstExpression().accept(this, null);
		comp.getSecondExpression().accept(this, null);
		return null;
	}

	@Override
	public Object visit(FieldAccess fa, Object o) {
		return null;
	}

	@Override
	public Object visit(Indexing indexing, Object o) {
		indexing.getFirstExpression().accept(this, null);
		indexing.getSecondExpression().accept(this, null);
		return null;
	}

	@Override
	public Object visit(IntLiteral intLit, Object o) {
		return null;
	}

	@Override
	public Object visit(Logical log, Object o) {
		log.getFirstExpression().accept(this, null);
		log.getSecondExpression().accept(this, null);
		return null;
	}

	@Override
	public Object visit(RealLiteral realLit, Object o) {
		return null;
	}

	@Override
	public Object visit(UnaryMinus uMin, Object o) {
		uMin.getExpression().accept(this, null);
		return null;
	}

	@Override
	public Object visit(UnaryNot unaryNot, Object o) {
		unaryNot.getExpression().accept(this, null);
		return null;
	}

	@Override
	public Object visit(Variable var, Object o) {
		return null;
	}

	@Override
	public Object visit(ArrayType aType, Object o) {
		aType.getOf().accept(this, null);
		return null;
	}

	@Override
	public Object visit(CharType charType, Object o) {
		return null;
	}

	@Override
	public Object visit(FunctionType funcType, Object o) {
		funcType.getParameters().stream().forEach(stmt -> stmt.accept(this, null)); //Necesario?
		return null;
	}

	@Override
	public Object visit(RecordField var, Object o) {
		return null;
	}

	@Override
	public Object visit(IntType intType, Object o) {
		return null;
	}

	@Override
	public Object visit(RealType realType, Object o) {
		return null;
	}

	@Override
	public Object visit(VoidType vType, Object o) {
		return null;
	}

	@Override
	public Object visit(ErrorType eType, Object o) {
		return null;
	}

	@Override
	public Object visit(RecordType recordType, Object o) {
		recordType.getRecField().stream().forEach(stmt -> stmt.accept(this, null));;
		return null;
	}

}
