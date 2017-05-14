package ast.types;

import java.util.List;

import ast.others.ASTNode;

public interface Type extends ASTNode{
	
	public boolean isLogical();
	public Type arithmetic(Type type);
	public Type arithmetic();
	public Type comparison(Type type);
	public Type logical(Type type);
	public Type promotesTo(Type type);
	public Type parentheris(List<Type> types);
	public Type squareBrackets(Type type);
	public Type dot(String right);
	public Type castTo(Type type);
	public Type higherType(Type type);
	
	
	public String simpleToString();
	public String suffix();
	
	public String codeInfo();
	
	public int getNumberOfBytes();
}
