package ast.definitions;

import java.util.List;

import visitors.Visitor;
import ast.statements.Statement;
import ast.types.Type;

public class FuncDefinition extends AbstractDefinition implements Definition {

	
	public int totalLocalVariableSize;
	public  int totalParameterSize;
	
	private List<Statement> statements;
	private int scope;
	
	public FuncDefinition(int line, int col, String name, Type type, List<Statement> statements){
		this.line=line;
		this.col=col;
		this.name=name;
		this.type=type;
		this.statements=statements;
	}

	@Override
	public String toString() {
		return "[FuncDefinition: \n"
				+ "\t line= "+ line+"\n"
				+ "\t column= "+ col+"\n"
				+ "\t type= "+ type+"\n"
				+ "\t statements= " + statements+ "]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public List<Statement> getStatements() {
		return statements;
	}

	@Override
	public int getScope() {
		return scope;
	}

	@Override
	public void setScope(int scope) {
		this.scope=scope;
		
	}
	
	
	
	
}
