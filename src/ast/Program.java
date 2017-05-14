package ast;

import java.util.List;
import ast.definitions.Definition;
import ast.others.AbstractASTNode;
import visitors.Visitor;

public class Program extends AbstractASTNode{
	private List<Definition> definitions;
	
	
	public Program(int line, int col, List<Definition> definitions){
		this.definitions=definitions;
		this.line=line;
		this.col=col;
	}
	
	@Override
	public String toString() {
		return "[Program: \n"
				+ "\t line= "+ line+"\n"
				+ "\t column= "+ col+"\n"
				+ "\t Definitions = "+ definitions+"]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public List<Definition> getDefinitions() {
		return definitions;
	}
	
	

}
