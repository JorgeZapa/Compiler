package ast.definitions;

import ast.others.ASTNode;
import ast.types.Type;

public interface Definition extends ASTNode{

	public String getName();
	public Type getType();
	public int getScope();
	public void setScope(int scope);
}
