package ast.types;

import visitors.Visitor;

import java.util.List;

import ast.others.ASTNode;
import errorhandler.EH;

public class ErrorType extends AbstractType implements Type {
	
	private String message;
	
	public ErrorType(int line, int col, String message){
		this.line=line;
		this.col=col;
		this.message=message;
		EH.getEH().addError(this);
	}
	
	public ErrorType(ASTNode node, String message){
		this.line=node.getLine();
		this.col=node.getColumn();
		this.message=message;
		EH.getEH().addError(this);
	}
	
	@Override
	public String toString(){
		return "Error found at line: ["+line+"] and column: ["+col+"], the error was: "+message+".";
	}
	
	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}
	
	@Override
	public String simpleToString() {
		return "ErrorType";
	}

	@Override
	public int getNumberOfBytes() {
		//TODO: here?
		return 0;
	}

	@Override
	public String suffix() {
		return "wrong";
	}
	
	@Override
	public boolean isLogical() {
		return false;
	}

	@Override
	public Type arithmetic(Type type) {
		return this;
	}

	@Override
	public Type arithmetic() {
		return this;
	}

	@Override
	public Type comparison(Type type) {
		return this;
	}

	@Override
	public Type logical(Type type) {
		return this;
	}

	@Override
	public Type promotesTo(Type type) {
		return this;
	}

	@Override
	public Type parentheris(List<Type> types) {
		return this;
	}
	
	@Override
	public Type squareBrackets(Type type){
		return this;
	}

	@Override
	public Type dot(String left) {
		return this;
	}
	
	@Override
	public Type castTo(Type type) {
		return this;
	}

}
