package ast.expresions;

public abstract class BinaryOperation extends AbstractExpression implements Expression {

	protected Expression firstExpression;
	protected Expression secondExpression;
	public Expression getFirstExpression() {
		return firstExpression;
	}
	public Expression getSecondExpression() {
		return secondExpression;
	}
	
	
	
}
