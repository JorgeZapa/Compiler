package ast.types;

import visitors.Visitor;

public class RealType extends AbstractType implements Type {
	
	private static RealType instance= new RealType();
	
	private RealType(){
		
	}
	
	public static RealType getInstance(){
		return instance;
	}
	
	@Override
	public String toString() {
		return "[RealType]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	
	@Override
	public Type arithmetic(Type type) {
		if(type instanceof RealType || type instanceof CharType 
				|| type instanceof IntType)
			return this;
		return null;
	}
	
	@Override
	public Type arithmetic() {
		return this;
	}
	
	
	@Override
	public Type promotesTo(Type type) {
		if(type instanceof RealType)
			return this;
		if(type instanceof IntType)
			return null;
		if(type instanceof CharType)
			return null;
		return null;
	}
	
	@Override
	public Type castTo(Type type) {
		if(type instanceof RealType)
			return this;
		if(type instanceof IntType)
			return IntType.getInstance();
		if(type instanceof CharType)
			return CharType.getInstance(); //Preguntar Real-->char
		return null;
	}
	
	@Override
	public Type higherType(Type type){
		if(type instanceof CharType || type instanceof IntType
				|| type instanceof RealType)
			return this;
		return null;
	}
	
	@Override
	public String simpleToString() {
		return "real";
	}

	@Override
	public int getNumberOfBytes() {
		return 4;
	}

	@Override
	public String suffix() {
		return "f";
	}
}
