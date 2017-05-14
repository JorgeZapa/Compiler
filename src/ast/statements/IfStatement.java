package ast.statements;

import java.util.List;

import visitors.Visitor;
import ast.expresions.Expression;
import ast.others.AbstractASTNode;

public class IfStatement extends AbstractASTNode implements Statement {
	
	private Expression condition;
	private List<Statement> ifBody;
	private List<Statement> elseBody;
	
	public IfStatement(int line, int col, Expression condition,
			List<Statement> ifBody, List<Statement> elseBody){
		this.line=line;
		this.col=col;
		this.condition=condition;
		this.ifBody=ifBody;
		this.elseBody=elseBody;
	}
	
	public void setElse(List<Statement> statements){
		this.elseBody=statements;
	}

	@Override
	public String toString() {
		return "[IfStatement \n\tcondition=" + condition + ",\n\t ifBody=" + ifBody + ",\n\t elseBody=" + elseBody
				+ ",\n\t line=" + line + ",\n\t col=" + col + "]\n";
	}
	
	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public Expression getCondition() {
		return condition;
	}

	public List<Statement> getIfBody() {
		return ifBody;
	}

	public List<Statement> getElseBody() {
		return elseBody;
	}
	
	

}
