package ast.expresions;

import ast.others.ASTNode;
import ast.types.Type;

public interface Expression extends ASTNode{

	public boolean getLValue();
	
	public void setType(Type type);
	
	public Type getType();
	
}
