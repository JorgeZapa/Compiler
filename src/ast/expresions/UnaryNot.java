package ast.expresions;

import visitors.Visitor;

public class UnaryNot extends AbstractExpression implements Expression {

	private Expression expression;
	
	public UnaryNot(int line, int col, Expression expression){
		this.line=line;
		this.col=col;
		this.expression=expression;
	}

	@Override
	public String toString() {
		return "[UnaryNot \n\texpression=" + expression + ",\n\t line=" + line + ",\n\t col=" + col + "]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public Expression getExpression() {
		return expression;
	}
	
	
	
	
}
