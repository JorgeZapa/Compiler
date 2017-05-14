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

public class OffsetVisitor implements Visitor {
	
	private int localOffsetCount;
	private int globalOffsetCount;


	@Override
	public Object visit(Program p, Object o) {
		p.getDefinitions().stream().forEach(stmt -> stmt.accept(this, o));
		return null;
	}

	@Override
	public Object visit(VarDefinition varDef, Object o) {
		//Hacer aqui. Aqui se diferencia locales de gloables por el scope y se usa un atributo 
		// para el offset que se lleva en ambos y se resetea la de la local variable cada vez que se sale de la funcion
		varDef.getType().accept(this, o);
		if(varDef.getScope()==0){ //Es global
			varDef.setOffset(globalOffsetCount);
			globalOffsetCount+=varDef.getType().getNumberOfBytes();
		}
		if(varDef.getScope()>0){ //Es local //TODO preguntar como diferenciar de parameters y local variables aqui 
			localOffsetCount-=varDef.getType().getNumberOfBytes();
			varDef.setOffset(localOffsetCount);
		}
		return null;
	}

	@Override
	
	public Object visit(FuncDefinition funcDef, Object o) {
		funcDef.totalParameterSize=(int) funcDef.getType().accept(this, o);
		localOffsetCount=0; //TODO: preguntar si asi bien paraq diferenciar entre local y parameters
		funcDef.getStatements().stream().forEach(stmt -> stmt.accept(this, o));
		
		//Se resetea el contador.
		funcDef.totalLocalVariableSize=localOffsetCount*-1;
		localOffsetCount=0;
		
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
		funcType.getParameters().stream().forEach(stmt -> stmt.accept(this, o));
		int sum=4;
		for(int i=funcType.getParameters().size()-1;i>=0;i--){
			VarDefinition param = funcType.getParameters().get(i);
			param.setOffset(sum);
			sum+=param.getType().getNumberOfBytes();
		}
		
		return sum-4;
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
		recordType.getRecField().stream().forEach(stmt -> stmt.accept(this, o));
			int sum=0;
			for(RecordField rf: recordType.getRecField()){
				rf.setOffset(sum);
				sum+=rf.getNumberOfBytes();
			}
		return null;
	}

}
