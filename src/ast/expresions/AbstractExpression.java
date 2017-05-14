package ast.expresions;

import ast.others.AbstractASTNode;
import ast.types.Type;

public abstract class AbstractExpression extends AbstractASTNode implements Expression {

	//Para TypeCheckingVisitor
	public boolean lValue;
	
	//Para TypeCheckingVisitor2.0
	protected Type type;
	
	@Override
	public boolean getLValue(){
		return lValue;
	}
	
	@Override
	public void setType(Type type){
		this.type=type;
	}
	
	@Override
	public Type getType(){
		return this.type;
	}

}
