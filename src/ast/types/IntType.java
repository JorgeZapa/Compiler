package ast.types;

import visitors.Visitor;

public class IntType extends AbstractType implements Type {
	
	private static IntType instance= new IntType();
	
	private IntType(){
		
	}
	
	public static IntType getInstance(){
		return instance;
	}

	@Override
	public String toString() {
		return "[IntType]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}
	
	//It can be used in a condition
	@Override
	public boolean isLogical() {
		return true;
	}
	
	@Override
	public Type arithmetic(Type type) {
		if(type instanceof RealType)
			return RealType.getInstance();
		if(type instanceof CharType)
			return this;
		if(type instanceof IntType)
			return this;
		return null;
	}
	
	@Override
	public Type arithmetic() {
		return this;
	}
	
	@Override
	public Type logical(Type type) {
		if(type instanceof RealType)
			return null;
		if(type instanceof CharType)
			return this;
		if(type instanceof IntType)
			return this;
		return null;
	}
	
	@Override
	public Type comparison(Type type) {
		if(type instanceof RealType)
			return this;
		if(type instanceof CharType)
			return this;
		if(type instanceof IntType)
			return this;
		return null;
	}
	
	@Override
	public Type promotesTo(Type type) {
		if(type instanceof RealType)
			return RealType.getInstance();
		if(type instanceof IntType)
			return this;
		if(type instanceof CharType)
			return CharType.getInstance();
		return null;
	}
	
	@Override
	public Type castTo(Type type) {
		if(type instanceof RealType)
			return RealType.getInstance();
		if(type instanceof IntType)
			return this;
		if(type instanceof CharType)
			return CharType.getInstance();
		return null;
	}
	
	@Override
	public Type higherType(Type type){
		if(type instanceof CharType || type instanceof IntType)
			return type;
		if(type instanceof RealType)
			return RealType.getInstance();
		return null;
	}
	
	@Override
	public String simpleToString() {
		return "int";
	}

	@Override
	public int getNumberOfBytes() {
		return 2;
	}

	@Override
	public String suffix() {
		return "i";
	}

}
