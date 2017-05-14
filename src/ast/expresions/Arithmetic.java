package ast.expresions;

import visitors.Visitor;


public class Arithmetic extends BinaryOperation implements Expression {

	private String operand;
	
	public Arithmetic(int line, int col, Expression firstExpression,
						String operand, Expression secondExpression){
		this.firstExpression=firstExpression;
		this.operand=operand;
		this.secondExpression=secondExpression;
		this.line=line;
		this.col=col;
	}
	
	@Override
	public String toString() {
		return "[Arithmetic: \n"
				+ "\t line= "+ line+"\n"
				+ "\t column= "+ col+"\n"
				+ "\t first Expression= "+ firstExpression+"\n"
				+ "\t operand= "+ operand+"\n"
				+ "\t seconf Expression= " + secondExpression+"]\n" ;
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public String getOperand() {
		return operand;
	}
	
	
}
