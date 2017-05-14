package ast.types;

import visitors.Visitor;

public class VoidType extends AbstractType implements Type {

	private static VoidType instance= new VoidType();
	
	private VoidType(){
		
	}
	
	public static VoidType getInstance(){
		return instance;
	}
	
	@Override
	public String toString() {
		return "[VoidType]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}
	
	@Override
	public String simpleToString() {
		return "void";
	}

	@Override
	public int getNumberOfBytes() {
		return 0;
	}

	@Override
	public String suffix() {
		return "wrong";
	}
	
	
}
