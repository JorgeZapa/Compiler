package ast.types;

import visitors.Visitor;

public class ArrayType extends AbstractType implements Type {
	
	private int size;
	private Type of;
	
	public ArrayType(int line, int col, int size, Type of){
		this.line=line;
		this.col=col;
		this.size=size;
		this.of=of;
	}

	@Override
	public String toString() {
		return "[ArrayType \n\tsize=" + size + ",\n\t of=" + of + "]\n";
	}

	@Override
	public Object accept(Visitor v, Object o) {
		return v.visit(this, o);
	}
	
	public static ArrayType prepareArray(int line, int column, int size, Type of) {
		if (of instanceof ArrayType) {//Si el nuevo tipo a insertar tiene un arrayType dentro.
			ArrayType reference = (ArrayType) of; //Guardamos una referencia a lo TODO lo que apunta.
			while(reference.of instanceof ArrayType){ //Nos vamos moviendo en las referencias hasta llegar a la ultima.
				reference = (ArrayType) reference.of;
			}
			ArrayType newOne = new ArrayType(line, column, size, reference.of); //Creamos el nuevo arrayyType a definir
			//Swap them
			reference.of = newOne;
			return (ArrayType) reference; //Lo devolvemos listo
		} else {
			return new ArrayType(line, column, size, of); //Sino todo es normal y no necesitamos hacer operaciones.
		}
	}

	public int getSize() {
		return size;
	}

	public Type getOf() {
		return of;
	}
	
	@Override
	public Type squareBrackets(Type type){
		//Se tiene que apsar un integer
		if(type.promotesTo(IntType.getInstance())!=null){
			//Comrpobar el tamaño? integer < size?
			return of;
		}
		return null;
	}

	@Override
	public String simpleToString() {
		return "array";
	}

	@Override
	public int getNumberOfBytes() {
		return of.getNumberOfBytes()*size;
	}

	@Override
	public String suffix() {
		return of.suffix();
	}
	
	@Override
	public String codeInfo() {
		
		return "[" + size + "," + of.codeInfo() +"]";
	}
	
	
	
	

}
