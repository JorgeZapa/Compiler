package ast.expresions;

import visitors.Visitor;

public class FieldAccess extends AbstractExpression implements Expression {
	
	private Expression left;
	private String name;
	
	public FieldAccess(int line, int col, String name, Expression left){
		this.line=line;
		this.col=col;
		this.name=name;
		this.left=left;
	}
	
	@Override
	public String toString() {
		return "[FieldAccess: \n"
				+ "\t line= "+ line+"\n"
				+ "\t column= "+ col+"\n"
				+ "\t name= "+ name+"\n"
				+ "\t left Expression= "+ left+"]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public Expression getLeft() {
		return left;
	}

	public String getName() {
		return name;
	}
	
	

}
