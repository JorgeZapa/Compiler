package ast.statements;

import visitors.Visitor;
import ast.expresions.Expression;
import ast.others.AbstractASTNode;

public class Assignment extends AbstractASTNode implements Statement {
	
	private Expression firstExpression;
	private Expression secondExpression;
	
	public boolean lValue;
	
	public Assignment(int line, int col,Expression firstExpression, Expression secondExpression){
		this.firstExpression = firstExpression;
		this.secondExpression = secondExpression;
		this.line=line;
		this.col=col;
	}

	@Override
	public String toString() {
		return "[Assignment \n\tfirstExpression=" + firstExpression + ",\n\t secondExpression=" + secondExpression
				+ ",\n\t line=" + line + ",\n\t col=" + col + "]\n";
	}
	
	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public Expression getFirstExpression() {
		return firstExpression;
	}

	public Expression getSecondExpression() {
		return secondExpression;
	}
	
	
	

}
