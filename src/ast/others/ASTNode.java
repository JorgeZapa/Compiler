package ast.others;

import visitors.Visitor;

public interface ASTNode {
	
	int getLine();
	
	int getColumn();

	Object accept(Visitor v, Object o);
	
	String toString();

}
