package ast.expresions;

import visitors.Visitor;


public class Logical extends BinaryOperation implements Expression {
	
	private String operator;

	public Logical(int line, int col, Expression firstExpression, String operator, Expression secondExpression) {
		this.line = line;
		this.col = col;
		this.firstExpression = firstExpression;
		this.operator=operator;
		this.secondExpression = secondExpression;
	}
	
	@Override
	public String toString() {
		return "[Logical: \n"
				+ "\t line= "+ line+"\n"
				+ "\t column= "+ col+"\n"
				+ "\t first Expression= "+ firstExpression+"\n"
				+ "\t operand= "+ operator+"\n"
				+ "\t seconf Expression= " + secondExpression +"]\n" ;
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public String getOperator() {
		return operator;
	}
	
	
}
