package ast.expresions;

import visitors.Visitor;

public class RealLiteral extends AbstractExpression implements Expression {
	
	private double value;
	
	public RealLiteral(int line, int col, double value){
		this.line=line;
		this.col=col;
		this.value=value;
	}

	@Override
	public String toString() {
		return "[RealLiteral \n\tvalue=" + value + ",\n\t line=" + line + ",\n\t col=" + col + "]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public double getValue() {
		return value;
	}
	
	

}
