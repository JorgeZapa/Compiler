package ast.statements;

import java.util.List;

import visitors.Visitor;
import ast.expresions.Expression;
import ast.expresions.Variable;
import ast.others.AbstractASTNode;
import ast.types.Type;

public class Invocation extends AbstractASTNode implements Statement, Expression {

	private List<Expression> arguments;
	private Variable function;
	
	public boolean lValue;
	
	//TypeCheckingVisitor2.0
	private Type type;
	
	public Invocation(int line, int col, List<Expression> arguments, Variable function){
		this.line=line;
		this.col=col;
		this.arguments=arguments;
		this.function=function;
	}

	@Override
	public String toString() {
		return "[Invocation \n\targuments=" + arguments + ",\n\t function=" + function + ",\n\t line=" + line
				+ ",\n\t col=" + col + "]\n";
	}
	
	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public List<Expression> getArguments() {
		return arguments;
	}

	public Variable getFunction() {
		return function;
	}

	@Override
	public boolean getLValue() {
		return lValue;
	}

	@Override
	public void setType(Type type) {
		this.type=type;
		
	}

	@Override
	public Type getType() {
		return this.type;
	}
	
	

}
