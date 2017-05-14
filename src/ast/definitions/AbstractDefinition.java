package ast.definitions;

import ast.others.AbstractASTNode;
import ast.types.Type;

public abstract class AbstractDefinition extends AbstractASTNode implements Definition {
	
	protected String name;
	protected Type type;
	
	
	public String getName(){
		return name;
	}
	
	public Type getType(){
		return type;
	}
}
