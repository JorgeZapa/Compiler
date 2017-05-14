package ast.expresions;

import visitors.Visitor;

public class Indexing extends BinaryOperation implements Expression {

	public Indexing(int line, int col, Expression firstExpression,
						Expression secondExpression){
		this.col=col;
		this.line=line;
		this.firstExpression=firstExpression;
		this.secondExpression=secondExpression;
	}
	
	@Override
	public String toString() {
		return "[Indexing: \n"
				+ "\t line= "+ line+"\n"
				+ "\t column= "+ col+"\n"
				+ "\t first Expression= "+ firstExpression+"\n"
				+ "\t second Expression= " + secondExpression+ "]\n" ;
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}
	
	
	
}
