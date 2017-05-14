package ast.expresions;

import ast.types.Type;
import visitors.Visitor;

public class Cast extends AbstractExpression implements Expression {

	private Type type;
	private Expression expression;
	
	public Cast(int line, int col, Type type, Expression expression){
		this.line=line;
		this.col=col;
		this.type=type;
		this.expression=expression;
	}
	
	@Override
	public String toString() {
		return "[Cast: \n"
				+ "\t line= "+ line+"\n"
				+ "\t column= "+ col+"\n"
				+ "\t first Type= "+ type +"\n"
				+ "\t operand= "+ expression +"]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public Type getType() {
		return type;
	}
	
	public void setType(Type type){
		this.type=type;
	}

	public Expression getExpression() {
		return expression;
	}
	
	
}
