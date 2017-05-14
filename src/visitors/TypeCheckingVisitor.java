package visitors;

import java.util.ArrayList;
import java.util.List;

import ast.Program;
import ast.definitions.Definition;
import ast.definitions.FuncDefinition;
import ast.definitions.VarDefinition;
import ast.expresions.Arithmetic;
import ast.expresions.Cast;
import ast.expresions.CharLiteral;
import ast.expresions.Comparison;
import ast.expresions.Expression;
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
import ast.types.Type;
import ast.types.VoidType;

public class TypeCheckingVisitor implements Visitor {

	@Override
	public Object visit(Program p, Object o) {
		for(Definition definition: p.getDefinitions()){
			definition.accept(this, o);
		}
		return null;
	}

	@Override
	public Object visit(VarDefinition varDef, Object o) {
		return null;
	}

	@Override
	public Object visit(FuncDefinition funcDef, Object o) {
		Type retType=(Type) funcDef.getType().accept(this, o);
		
		for(Statement stmts: funcDef.getStatements()){
			stmts.accept(this, retType);
		}
		
		
		return null;
	}

	@Override
	public Object visit(Assignment assign, Object o) {
		//Primero debemos entrar y calcular los lValue de estos nodos.
		assign.getFirstExpression().accept(this, o);
		assign.getSecondExpression().accept(this, o);
		if(!assign.getFirstExpression().getLValue()){
			new ErrorType(assign, "In the assignment, the lValue is wrong, not well formed");
		}
		Type previousType = assign.getFirstExpression().getType();
		
		assign.getFirstExpression().setType(assign.getSecondExpression().getType().promotesTo(assign.getFirstExpression().getType()));
		if(assign.getFirstExpression().getType()==null){
			//assign.getFirstExpression().setType(new ErrorType(assign, "Can't prompote (natuarlly convert) an " + assign.getSecondExpression().getType()
			//												+ " to a " + assign.getSecondExpression().getType()));
			//Not really necessary
			new ErrorType(assign, "Can't prompote (natuarlly convert) an " + assign.getSecondExpression().getType().simpleToString()
																+ " to a " + previousType.simpleToString());
		}
		return null;
	}

	@Override
	public Object visit(IfStatement ifStmt, Object o) {
		ifStmt.getCondition().accept(this, o);
		if(!ifStmt.getCondition().getType().isLogical()){
			ifStmt.getCondition().setType(new ErrorType(ifStmt.getCondition(),"The type in the condition is not a correct one: " +ifStmt.getCondition().getType().simpleToString()));
		}
		for(Statement stmt: ifStmt.getIfBody()){
			stmt.accept(this, o);
		}
		for(Statement stmt: ifStmt.getElseBody()){
			stmt.accept(this, o);
		}
		return null;
	}

	@Override
	public Object visit(Invocation inv, Object o) {
		inv.getFunction().accept(this, o);
		//Invoke this function with its parameters
		List<Type> types = new ArrayList<>();
		for(Expression ex: inv.getArguments()){
			ex.accept(this, o); //Primero accept
			types.add(ex.getType()); //Then we add in order to process later
		}
		
		inv.setType(inv.getFunction().getType().parentheris(types));
		
		if(inv.getType() == null){
			inv.setType(new ErrorType(inv,"The function '" + inv.getFunction().getName()+ "' is not well invoked"));
		}
		
		return null;
	}

	@Override
	public Object visit(Return ret, Object o) {
		ret.getExpression().accept(this, o);


		if(ret.getExpression().getType().promotesTo((Type) o)==null){
			String returnType= o==null ? "VoidType" : ((Type) o).simpleToString();
			
			ret.getExpression().setType(new ErrorType(ret,"The return type " + ret.getExpression().getType().simpleToString() + 
					" is not which it must be: " + returnType));
																							
		}
		
		return null;
	}

	@Override
	public Object visit(WhileStatement whileStmt, Object o) {
		whileStmt.getCondition().accept(this, o);
		if(!whileStmt.getCondition().getType().isLogical()){
			whileStmt.getCondition().setType(new ErrorType(whileStmt.getCondition(), "The condition isn't a corect type: " + whileStmt.getCondition().getType().simpleToString()));
		}
		for(Statement stmt: whileStmt.getStatements()){
			stmt.accept(this, o);
		}
		return null;
	}

	@Override
	public Object visit(Write write, Object o) {
		write.getExpressions().stream().forEach(stmt-> stmt.accept(this, o));
		return null;
	}

	@Override
	public Object visit(Read read, Object o) {
		int i=0;
		for(Expression exp: read.getExpressions()){
			exp.accept(this, o);
			i++;
			if(!exp.getLValue()){
				new ErrorType(read,"The expression nº" +i+" It is not well formed as it has lValue and cannot be read");
			}
		}
		return null;
	}

	@Override
	public Object visit(Arithmetic arith, Object o) {
		arith.getFirstExpression().accept(this, o);
		arith.getSecondExpression().accept(this, o);
		arith.lValue=false;
		
		arith.setType(arith.getFirstExpression().getType().arithmetic(arith.getSecondExpression().getType()));
		if(arith.getType()==null){
			arith.setType(new ErrorType(arith, "Arithmetic operation between " + arith.getFirstExpression().getType().simpleToString() + " and "
											+ arith.getSecondExpression().getType().simpleToString() +" CAN'T be preformed"));
		}
		return null;
	}

	@Override
	public Object visit(Cast cast, Object o) {
		cast.getExpression().accept(this, o);
		cast.getType().accept(this, o);
		cast.lValue=false;
		
		Type previousType= cast.getType();
		
		//No haría falta, en realdiad con compararlos o devolver un boolean en el metodo valdría
		cast.setType(cast.getExpression().getType().castTo(cast.getType()));
		
		if(cast.getType()==null){
			cast.setType(new ErrorType(cast, "Can't explicity cast from " + cast.getExpression().getType().simpleToString() +
					"to " + previousType.simpleToString()));
		}
		
		return null;
	}

	@Override
	public Object visit(CharLiteral charLit, Object o) {
		charLit.lValue=false;
		charLit.setType(CharType.getInstance());
		return null;
	}

	@Override
	public Object visit(Comparison comp, Object o) {
		comp.getFirstExpression().accept(this, o);
		comp.getSecondExpression().accept(this, o);
		comp.lValue=false;
		
		comp.setType(comp.getFirstExpression().getType().comparison(comp.getSecondExpression().getType()));
		
		if(comp.getType()==null){
			comp.setType(new ErrorType(comp,"Comparation between "+ comp.getFirstExpression().getType().simpleToString() + " and " 
												+ comp.getSecondExpression().getType().simpleToString()+ " CAN'T be preformed"));
		}
		
		return null;
	}

	@Override
	public Object visit(FieldAccess fa, Object o) {
		fa.getLeft().accept(this, o);
		fa.lValue=true;
		fa.setType(fa.getLeft().getType().dot(fa.getName()));
		
		if(fa.getType()==null){
			fa.setType(new ErrorType(fa,"Impossible to access the specified field: " + fa.getName()));
		}
		
		return null;
	}

	@Override
	public Object visit(Indexing indexing, Object o) {
		indexing.getFirstExpression().accept(this, o);
		indexing.getSecondExpression().accept(this, o);
		indexing.lValue=true;
		
		//TODO: Explicar mejor el error como? int[0] =assada
		
		indexing.setType(indexing.getFirstExpression().getType().squareBrackets(indexing.getSecondExpression().getType()));
		if(indexing.getType()==null){
			indexing.setType(new ErrorType(indexing,"Can't access the element of the array using an "+ indexing.getSecondExpression().getType().simpleToString()));
		}
		return null;
	}

	@Override
	public Object visit(IntLiteral intLit, Object o) {
		intLit.lValue=false;
		intLit.setType(IntType.getInstance());
		return null;
	}

	@Override
	public Object visit(Logical log, Object o) {
		log.getFirstExpression().accept(this, o);
		log.getSecondExpression().accept(this, o);
		log.lValue=false;
		
		log.setType(log.getFirstExpression().getType().logical(log.getSecondExpression().getType()));
		
		if(log.getType()==null){
			log.setType(new ErrorType(log,"Logical operation between " + log.getFirstExpression().getType().simpleToString() + " and " 
		+ log.getSecondExpression().getType().simpleToString()+ " CAN'T be preformed"));
		}
		
		return null;
	}

	@Override
	public Object visit(RealLiteral realLit, Object o) {
		realLit.lValue=false;
		realLit.setType(RealType.getInstance());
		return null;
	}

	@Override
	public Object visit(UnaryMinus uMin, Object o) {
		uMin.getExpression().accept(this, o);
		uMin.lValue=false;
		
		//-int -double -char
		uMin.setType(uMin.getExpression().getType().arithmetic());
		
		if(uMin.getType()==null){
			uMin.setType(new ErrorType(uMin, "Can't negate (-) value to " + uMin.getExpression().getType().simpleToString()));
		}
		return null;
	}
	
	@Override
	public Object visit(UnaryNot unaryNot, Object o) {
		unaryNot.getExpression().accept(this, o);
		unaryNot.lValue=false;
		
		unaryNot.setType(unaryNot.getExpression().getType());
		
		if(!unaryNot.getType().isLogical()){
			unaryNot.setType(new ErrorType(unaryNot, "Can't preform the Not operation of an " + unaryNot.getExpression().getType().simpleToString()));
		}
		
		return null;
	}

	@Override
	public Object visit(Variable var, Object o) {
		var.lValue=true;
		var.setType(var.getReference().getType());	
		return null;
	}

	@Override
	public Object visit(ArrayType aType, Object o) {
		return null;
	}

	@Override
	public Object visit(CharType charType, Object o) {
		return null;
	}

	@Override
	public Object visit(FunctionType funcType, Object o) {
		return funcType.getRet();
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
		return null;
	}



}
