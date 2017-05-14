package ast.types;

import java.util.List;

import ast.definitions.VarDefinition;
import visitors.Visitor;

public class FunctionType extends AbstractType implements Type {
	
	private List<VarDefinition> parameters;
	private Type ret;
	
	public FunctionType(List<VarDefinition> parameters, Type ret){
		this.parameters=parameters;
		this.ret=ret;
	}

	@Override
	public String toString() {
		return "[FunctionType \n\tparameters=" + parameters + ",\n\t ret=" + ret + "]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public List<VarDefinition> getParameters() {
		return parameters;
	}

	public Type getRet() {
		return ret;
	}
	
	public Type parentheris(List<Type> types) {
		if(this.parameters.size()!=types.size())
			return null;
		for(int i=0; i<this.parameters.size();i++){
			if(null==types.get(i).promotesTo(parameters.get(i).getType())){ //Comprobamos que los parametros puestos en la invocacionse puedan usar par invocar.
				return null;
			}
		}
		return this.ret;
	}
	
	@Override
	public String simpleToString() {
		return "FunctionType";
	}

	@Override
	public int getNumberOfBytes() {
		return ret.getNumberOfBytes();
	}

	@Override
	public String suffix() {
		return ret.suffix();
	}
	
	
	

}
