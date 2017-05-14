package ast.expresions;

import visitors.Visitor;

public class IntLiteral extends AbstractExpression implements Expression{
	
	private int value;
	
	public IntLiteral(int line, int col, int value){
		this.value=value;
		this.line=line;
		this.col=col;
	}

	@Override
	public String toString() {
		return "[IntLiteral \n\tvalue=" + value + ",\n\t line=" + line + ",\n\t col=" + col + "]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public int getValue() {
		return value;
	}

	

	
	
}
