package visitors.codegeneration;

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
import ast.statements.Invocation;
import ast.types.FunctionType;
import ast.types.Type;
import codegenerator.CodeGenerator;

public class ValueCGVisitor extends AbstractCGVisitor {

	private CodeGenerator cg;
	private AddessCGVisitor av;

	public ValueCGVisitor(CodeGenerator cg) {;
		this.cg = cg;
	}
	
	public void setAv(AddessCGVisitor av){
		this.av=av;
	}
	
	@Override
	public Object visit(Variable var, Object o){
		var.accept(av, o);
		cg.load(var.getType().suffix());
		return null;
	}
	
	@Override
	public Object visit(IntLiteral intLit, Object o){
		cg.push("i", intLit.getValue());
		return null;
	}
	
	@Override
	public Object visit(CharLiteral charLit, Object o){
		cg.push("b", charLit.getValue());
		return null;
	}
	
	@Override
	public Object visit(RealLiteral realLit, Object o){
		cg.push("f", realLit.getValue());
		return null;
	}
	
	@Override
	public Object visit(Indexing ind, Object o){
		ind.accept(av, o);
		cg.load(ind.getType().suffix());
		
		return null;
	}
	
	@Override
	public Object visit(FieldAccess fa, Object o){
		fa.accept(av, o);
		cg.load(fa.getType().suffix());
		
		return null;
	}
	
	@Override
	public Object visit(Cast cast, Object o){
		cast.getExpression().accept(this, o);
		cg.convertTo(cast.getExpression().getType(), cast.getType());
		
		return null;
	}
	
	@Override
	public Object visit(Arithmetic arith, Object o){
		arith.getFirstExpression().accept(this, o);
		cg.convertTo(arith.getFirstExpression().getType(), arith.getType());
		arith.getSecondExpression().accept(this, o);
		cg.convertTo(arith.getSecondExpression().getType(), arith.getType());
		cg.arithmetic(arith.getOperand(), arith.getType());
		return null;
	}
	
	@Override
	public Object visit(Comparison comp, Object o){
		Type higherType = comp.getFirstExpression().getType().higherType(comp.getSecondExpression().getType());
		comp.getFirstExpression().accept(this, o);
		cg.convertTo(comp.getFirstExpression().getType(),higherType);
		comp.getSecondExpression().accept(this, o);
		cg.convertTo(comp.getSecondExpression().getType(), higherType);
		cg.comp(comp.getOperator(), comp.getType());
		return null;
	}
	
	@Override
	public Object visit(Invocation inv, Object o){
		int i=0;
		for(Expression exp : inv.getArguments()){
			exp.accept(this,o);
			cg.convertTo(exp.getType(), ((FunctionType)inv.getFunction().getType()).getParameters().get(i).getType());
			i++;
		}
		
		cg.call(inv.getFunction().getName());
		
		return null;
	}
	
	@Override
	public Object visit(Logical log, Object o){
		log.getFirstExpression().accept(this, o);
		cg.convertTo(log.getFirstExpression().getType(), log.getType());
		log.getSecondExpression().accept(this, o);
		cg.convertTo(log.getSecondExpression().getType(), log.getType());
		cg.logic(log.getOperator(), log.getType());
		
		return null;
	}
	

	@Override
	public Object visit(UnaryMinus uMin, Object o){
		cg.push(uMin.expression.getType().suffix(), 0);
		uMin.accept(this, o);
		cg.sub(uMin.getType().suffix());
		
		return null;
	}
	
	@Override
	public Object visit(UnaryNot uNot, Object o){
		uNot.getExpression().accept(this, o);
		cg.not();
		
		return null;
	}
}
