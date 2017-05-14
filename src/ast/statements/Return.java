package ast.statements;

import visitors.Visitor;
import ast.expresions.Expression;
import ast.others.AbstractASTNode;

public class Return extends AbstractASTNode implements Statement{
	
	private Expression expression;
	
	public Return(int line, int col, Expression expression){
		this.line=line;
		this.col=col;
		this.expression=expression;
	}

	@Override
	public String toString() {
		return "[Return \n\texpression=" + expression + ",\n\t line=" + line + ",\n\t col=" + col + "]\n";
	}
	
	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public Expression getExpression() {
		return expression;
	}
	
	

}
