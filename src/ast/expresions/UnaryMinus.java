package ast.expresions;

import visitors.Visitor;

public class UnaryMinus extends AbstractExpression implements Expression {
	
	public Expression expression;
	
	public UnaryMinus(int line, int col,Expression expression){
		this.expression=expression;
		this.line=line;
		this.col=col;
	}

	@Override
	public String toString() {
		return "[UnaryMinus \n\texpression=" + expression + ",\n\t line=" + line + ",\n\t col=" + col + "]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public Expression getExpression() {
		return expression;
	}
	
	

}
