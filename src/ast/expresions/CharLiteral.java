package ast.expresions;

import visitors.Visitor;

public class CharLiteral extends AbstractExpression implements Expression {
	
	private char value;
	
	public CharLiteral(int line, int col, char value){
		this.line=line;
		this.col=col;
		this.value=value;
	}
	
	@Override
	public String toString() {
		return "[CharLiteral: \n"
				+ "\t line= "+ line+"\n"
				+ "\t column= "+ col+"\n"
				+ "\t first value= "+ value +"]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public char getValue() {
		return value;
	}
	
	

}
