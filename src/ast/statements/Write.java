package ast.statements;

import java.util.List;

import visitors.Visitor;
import ast.expresions.Expression;
import ast.others.AbstractASTNode;

public class Write extends AbstractASTNode implements Statement{

	
	private List<Expression> expressions;
	
	public Write(int line, int col,List<Expression> expressions){
		this.expressions=expressions;
		this.line=line;
		this.col=col;
	}

	@Override
	public String toString() {
		return "[Write \n\texpressions=" + expressions + ",\n\t line=" + line + ",\n\t col=" + col + "]\n";
	}
	
	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public List<Expression> getExpressions() {
		return expressions;
	}
	
	
}
