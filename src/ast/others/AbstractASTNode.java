package ast.others;

public abstract class AbstractASTNode implements ASTNode {

	protected int line;
	protected int col;
	
	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getColumn() {
		return col;
	}

}
