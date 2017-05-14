package ast.types;

import java.util.List;

import visitors.Visitor;

public class RecordType extends AbstractType implements Type {
	
	private List<RecordField> recField;
	
	public RecordType(List<RecordField> fields){
		this.recField=fields;
	}

	@Override
	public String toString() {
		return "[RecordType \n\trecField=" + recField + "]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}

	public List<RecordField> getRecField() {
		return recField;
	}
	
	public RecordField getField(String id){
		RecordField requestedRf = null;
		for(RecordField rf : recField){
			if(rf.getName().equals(id))
				requestedRf=rf;
		}
		return requestedRf;
	}
	
	@Override
	public Type dot(String right) {
		RecordField requestedRf = getField(right);
		if(requestedRf!=null)
			return requestedRf.getType();
		return null;
	}
	
	@Override
	public String simpleToString() {
		return "record";
	}

	@Override
	public int getNumberOfBytes() {
		int sum=0;
		for(RecordField rf : recField){
			sum+=rf.getNumberOfBytes();
		}
		return sum;
	}

	@Override
	public String suffix() {
		return "wrong";
	}

	@Override
	public String codeInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.codeInfo());
		sb.append("(");
		for(RecordField rf : getRecField()){
			sb.append("("+rf.getName()+" x "+rf.getType().codeInfo()+")");
			sb.append("x");
		}
		sb.deleteCharAt(sb.length() -1);//Quitamos la última x
		sb.append(")");
		return sb.toString();
	}
	
	
	
	
	
}
