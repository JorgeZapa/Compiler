package ast.types;

import java.util.List;

import ast.others.AbstractASTNode;

public abstract class AbstractType extends AbstractASTNode implements Type {

	@Override
	public boolean isLogical() {
		return false;
	}

	@Override
	public Type arithmetic(Type type) {
		//POR DEFECTO NO DEBERÍA FUNCIONAR -> ERROR O CHARTYPE
		if(type instanceof  ErrorType)
			return type;
		return null;
	}

	@Override
	public Type arithmetic() {
		//POR DEFECTO NO DEBERÍA FUNCIONAR -> ERROR O CHARTYPE
		return null;
	}

	@Override
	public Type comparison(Type type) {
		// El resultado siempre es un int
		if(type instanceof  ErrorType)
			return type;
		return null;
	}

	@Override
	public Type logical(Type type) {
		// El resultado siempre es un int solo entre char e int
		if(type instanceof  ErrorType)
			return type;
		return null;
	}

	@Override
	public Type promotesTo(Type type) {
		if(type instanceof  ErrorType)
			return type;
		return null;
	}

	@Override
	public Type parentheris(List<Type> types) {
		//Null en todos menos en funcType ya que es para comprobar la "invocacion" del método
		return null;
	}
	
	@Override
	public Type squareBrackets(Type type){
		//Solo ok en el arrayType
		if(type instanceof  ErrorType)
			return type;
		return null;
	}

	@Override
	public Type dot(String left) {
		// Solo ok en RecordType
		return null;
	}
	
	@Override
	public Type castTo(Type type) {
		//Devolviendo un booleano mejor. no?
		if(type instanceof  ErrorType)
			return type;
		return null;
	}
	
	@Override
	public String codeInfo(){
		return simpleToString();
	}
	
	@Override 
	public Type higherType(Type type){
		return null;
	}

}
