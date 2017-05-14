package visitors;

import ast.Program;
import ast.definitions.Definition;
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
import ast.statements.Statement;
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
import symboltable.SymbolTable;

public class IdentificationVisitor implements Visitor {
	
	private SymbolTable st= new SymbolTable();

	@Override
	public Object visit(Program p, Object o) {
		for(Definition definition: p.getDefinitions()){
			definition.accept(this, o);
		}
		return null;
	}

	@Override
	public Object visit(VarDefinition varDef, Object o) {
		varDef.getType().accept(this, o);
		if(!st.insert(varDef))
			new ErrorType(varDef,"This variable already exists in this scope");
		return null;
	}

	@Override
	public Object visit(FuncDefinition funcDef, Object o) {
		if(!st.insert(funcDef)){
			new ErrorType(funcDef,"This function has been already declared");
		}
		st.set();
		funcDef.getType().accept(this, o);
		for(Statement stmt: funcDef.getStatements())
			stmt.accept(this, o);
		st.reset();
		return null;
	}

	@Override
	public Object visit(Assignment assign, Object o) {
		assign.getFirstExpression().accept(this, o);
		assign.getSecondExpression().accept(this, o);
		return null;
	}

	@Override
	public Object visit(IfStatement ifStmt, Object o) {
		ifStmt.getCondition().accept(this, o);
		ifStmt.getIfBody().stream().forEach(stmt -> stmt.accept(this, o));
		ifStmt.getElseBody().stream().forEach(stmt -> stmt.accept(this, o));
		return null;
	}

	@Override
	public Object visit(Invocation inv, Object o) {
		inv.getArguments().stream().forEach(arg -> arg.accept(this, o));
		inv.getFunction().accept(this, o);
		return null;
	}

	@Override
	public Object visit(Return ret, Object o) {
		ret.getExpression().accept(this, o);
		return null;
	}

	@Override
	public Object visit(WhileStatement whileStmt, Object o) {
		whileStmt.getCondition().accept(this, o);
		whileStmt.getStatements().stream().forEach(stmt -> stmt.accept(this, o));
		return null;
	}

	@Override
	public Object visit(Write write, Object o) {
		write.getExpressions().forEach(exp -> exp.accept(this, o));
		return null;
	}

	@Override
	public Object visit(Read read, Object o) {
		read.getExpressions().stream().forEach(exp -> exp.accept(this, o));
		return null;
	}

	@Override
	public Object visit(Arithmetic arith, Object o) {
		arith.getFirstExpression().accept(this, o);
		arith.getSecondExpression().accept(this, o);
		return null;
	}

	@Override
	public Object visit(Cast cast, Object o) {
		cast.getType().accept(this, o);
		cast.getExpression().accept(this, o);
		return null;
	}

	@Override
	public Object visit(CharLiteral charLit, Object o) {
		return null;
	}

	@Override
	public Object visit(Comparison comp, Object o) {
		comp.getFirstExpression().accept(this, o);
		comp.getSecondExpression().accept(this, o);
		return null;
	}

	@Override
	public Object visit(FieldAccess fa, Object o) {
		fa.getLeft().accept(this, o);
		return null;
	}

	@Override
	public Object visit(Indexing indexing, Object o) {
		indexing.getFirstExpression().accept(this, o);
		indexing.getSecondExpression().accept(this, o);
		return null;
	}

	@Override
	public Object visit(IntLiteral intLit, Object o) {
		return null;
	}

	@Override
	public Object visit(Logical log, Object o) {
		log.getFirstExpression().accept(this, o);
		log.getSecondExpression().accept(this, o);
		return null;
	}

	@Override
	public Object visit(RealLiteral realLit, Object o) {
		return null;
	}

	@Override
	public Object visit(UnaryMinus uMin, Object o) {
		uMin.getExpression().accept(this, o);
		return null;
	}

	@Override
	public Object visit(UnaryNot unaryNot, Object o) {
		unaryNot.getExpression().accept(this, o);
		return null;
	}

	@Override
	public Object visit(Variable var, Object o) {
		var.setReference(st.find(var.getName()));
		if(var.getReference()==null){
			//new ErrorType(var,"The variable to be referenced is already declared");
			var.setReference( new VarDefinition(0,0, var.getName(),new ErrorType(var,"The variable to be referenced is already declared"))) ;
		}
		return null;
	}

	@Override
	public Object visit(ArrayType aType, Object o) {
		aType.getOf().accept(this, o);
		return null;
	}

	@Override
	public Object visit(CharType charType, Object o) {
		return null;
	}

	@Override
	public Object visit(FunctionType funcType, Object o) {
		funcType.getParameters().stream().forEach(stmt -> stmt.accept(this, o)); //Necesario? Si
		return null;
	}

	@Override
	public Object visit(RecordField var, Object o) {
		var.getType().accept(this, o);
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
		recordType.getRecField().stream().forEach(stmt -> stmt.accept(this, o));
		return null;
	}

}
