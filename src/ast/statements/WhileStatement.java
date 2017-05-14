package ast.statements;

import java.util.List;

import visitors.Visitor;
import ast.expresions.Expression;
import ast.others.AbstractASTNode;

public class WhileStatement extends AbstractASTNode implements Statement {

	private Expression condition;
	private List<Statement> statements;
	
	public WhileStatement(int line, int col, Expression condition, List<Statement> statements){
		this.line=line;
		this.col=col;
		this.condition=condition;
		this.statements=statements;
	}

	@Override
	public String toString() {
		return "[WhileStatement \n\tcondition=" + condition + ",\n\t statements=" + statements + ",\n\t line=" + line
				+ ",\n\t col=" + col + "]\n";
	}
	
	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public Expression getCondition() {
		return condition;
	}

	public List<Statement> getStatements() {
		return statements;
	}
	
	

}
