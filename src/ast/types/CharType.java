package ast.types;

import visitors.Visitor;

public class CharType extends AbstractType implements Type {
	
	private static CharType instance= new CharType();
	
	private CharType(){
		
	}
	
	public static CharType getInstance(){
		return instance;
	}

	@Override
	public String toString() {
		return "[CharType]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}
	
	@Override
	public Type arithmetic(Type type) {
		if(type instanceof RealType)
			return RealType.getInstance();
		if(type instanceof CharType)
			return IntType.getInstance();
		if(type instanceof CharType)
			return null;
		return null;
	}
	
	@Override
	public Type comparison(Type type) {
		if(type instanceof RealType)
			return IntType.getInstance();
		if(type instanceof CharType)
			return IntType.getInstance();
		if(type instanceof IntType)
			return IntType.getInstance();
		return null;
	}
	
	@Override
	public Type logical(Type type) {
		if(type instanceof RealType)
			return null;
		if(type instanceof CharType)
			return IntType.getInstance();
		if(type instanceof IntType)
			return IntType.getInstance();
		return null;
	}
	
	@Override
	public Type promotesTo(Type type) {
		if(type instanceof RealType)
			return null;
		if(type instanceof IntType)
			return IntType.getInstance();
		if(type instanceof CharType)
			return this;
		return null;
	}
	
	@Override
	public Type castTo(Type type) {
		if(type instanceof RealType)
			return RealType.getInstance(); //Preguntar Char-->Real
		if(type instanceof IntType)
			return IntType.getInstance();
		if(type instanceof CharType)
			return this;
		return null;
	}
	
	@Override
	public Type higherType(Type type){
		if(type instanceof CharType || type instanceof IntType
				|| type instanceof RealType)
			return type;
		return null;
	}
	
	@Override
	public String simpleToString() {
		return "char";
	}

	@Override
	public int getNumberOfBytes() {
		return 1;
	}

	@Override
	public String suffix() {
		return "b";
	}

	
}
