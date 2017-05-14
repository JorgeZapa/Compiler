package ast.definitions;

import visitors.Visitor;
import ast.statements.Statement;
import ast.types.Type;

public class VarDefinition extends AbstractDefinition implements Definition, Statement  {
	
	private int scope;
	private int offset;
	

	public VarDefinition(int line, int col, String name, Type type) {
		this.line=line;
		this.col=col;
		this.name = name;
		this.type = type;
	}


	@Override
	public String toString() {
		return "[VarDefinition: \n"
				+ "\t line= "+ line+"\n"
				+ "\t column= "+ col+"\n"
				+ "\t type= "+ type+"\n"
				+ "\t scope= " + scope + "\n"
				+ "\t offset= "+ offset + "]\n";
	}


	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}


	public int getScope() {
		return scope;
	}

	
	public void setOffset(int offset){
		this.offset=offset;
	}

	public int getOffset() {
		return offset;
	}


	@Override
	public void setScope(int scope) {
		this.scope=scope;
		
	}
	
	
	
	

}
