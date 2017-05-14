package visitors.codegeneration;

import ast.definitions.VarDefinition;
import ast.expresions.FieldAccess;
import ast.expresions.Indexing;
import ast.expresions.Variable;
import ast.types.IntType;
import ast.types.RecordField;
import ast.types.RecordType;
import codegenerator.CodeGenerator;

public class AddessCGVisitor extends AbstractCGVisitor {

	private CodeGenerator cg;
	private ValueCGVisitor vv;

	public AddessCGVisitor(CodeGenerator cg) {
		this.cg = cg;
	}
	
	

	public void setVv(ValueCGVisitor vv) {
		this.vv = vv;
	}



	@Override
	public Object visit(Variable var, Object o){ //TODO: preguntar esto: para variables que guardan una funcion????
		if(var.getReference() instanceof VarDefinition){
			VarDefinition vd =(VarDefinition) var.getReference();
			cg.pusha(vd); //Aqui esta todo encapsulado tanto offset ==0 y offset !=1
		}
		return null;
	}
	
	@Override
	public Object visit(FieldAccess fa, Object o){
		fa.getLeft().accept(this, o);
		cg.push("i", ((RecordField)((RecordType)fa.getLeft().getType()).getField(fa.getName())).getOffset());
		cg.add("i");
		return null;
	}
	
	@Override
	public Object visit(Indexing ind, Object o){
		ind.getFirstExpression().accept(this, o);
		ind.getSecondExpression().accept(vv, o);
		cg.convertTo(ind.getSecondExpression().getType(), IntType.getInstance());
		cg.push("i", ind.getType().getNumberOfBytes());
		cg.mult("i");
		cg.add("i");
		
		return null;
		
	}

}
