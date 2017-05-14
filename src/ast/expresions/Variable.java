package ast.expresions;

import ast.definitions.Definition;
import visitors.Visitor;

public class Variable extends AbstractExpression implements Expression {
	
	private String name;
	
	//Identification Visitor.
	private Definition reference;
	
	public Variable(int line, int col, String name){
		this.name=name;
		this.line=line;
		this.col=col;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "[Variable \n\tname=" + name + ",\n\t line=" + line + ",\n\t col=" + col + "]\n";
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
		Variable other = (Variable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setReference(Definition reference){
		this.reference=reference;
	}
	
	public Definition getReference(){
		return this.reference;
	}
	
	
	
	
	

}
