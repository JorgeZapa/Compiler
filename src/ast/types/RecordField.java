package ast.types;

import visitors.Visitor;

public class RecordField extends AbstractType implements Type{

	private int offset;
	private String name;
	
	private Type type;
	public RecordField(String name ,Type type){
		this.name=name;
		this.type=type;
	}

	@Override
	public String toString() {
		return "[RecordField \n\toffser=" + offset + ",\n\t name=" + name + "]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}
	
	public void setOffset(int offset) {
		this.offset=offset;
	}

	public int getOffset() {
		return offset;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public String simpleToString() {
		return "field";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecordField other = (RecordField) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int getNumberOfBytes() {
		return type.getNumberOfBytes();
	}

	@Override
	public String suffix() {
		return this.type.suffix();
	}
	
	
	
	
}
