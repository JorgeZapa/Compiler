package codegenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import ast.definitions.VarDefinition;
import ast.types.CharType;
import ast.types.IntType;
import ast.types.RealType;
import ast.types.Type;

public class CodeGenerator {
	
	public static String sourceFile;

	private PrintWriter out;
	
	private static int labels = 0;

	public CodeGenerator(String outputFile) throws FileNotFoundException {
		File file = new File(outputFile);
		out = new PrintWriter(file);
		this.indicateSourceFile();
	}
	
	private void indicateSourceFile() {
		out.println();
		out.println("#source \""+sourceFile+"\"");
		
	}

	public int getLables(int amount){
		int previousAmount = labels;
		labels +=amount;
		return previousAmount;
	}

	public void pushBP() {
		this.generateCode("push bp");
	}

	public void callMain() {
		out.println("call main");
		out.println("halt");
		out.flush();
	}

	public void generateComment(String info) {
		out.println("\t ' * " + info);
		out.flush();
	}

	public void generateLabel(String label) {
		out.println();
		out.println(" " + label.toLowerCase() + ":");
		out.flush();
	}
	public void generateLabel(int labelNumber){
		out.println();
		out.println(" label" +labelNumber + ":");
		out.flush();
	}

	public void generateLine(int line) {
		out.println();
		out.println("#line\t" + line);
		out.flush();
	}

	public void convertTo(Type first, Type second) {
		String firstType = first.suffix();
		String secondType = second.suffix();
		switch (secondType) { // TODO: preguntar si es mejor con instance ya que
								// el suffix puede cambiar

		case ("i"):
			if (firstType.equals("i"))
				return;
			else
				this.generateCode(firstType + "2" + secondType);
			break;
		case ("b"):
			if (firstType.equals("b"))
				return;
			else if (firstType.equals("i"))
				this.generateCode(firstType + "2" + secondType);
			else {
				this.generateCode(firstType + "2i");
				this.generateCode("i2" + secondType);
			}
			break;
		case ("f"):
			if (firstType.equals("f"))
				return;
			else if (firstType.equals("i"))
				this.generateCode(firstType + "2" + secondType);
			else {
				this.generateCode(firstType + "2i");
				this.generateCode("i2" + secondType);
			}
			break;
		}

	}

	public void arithmetic(String operand, Type type) {
		switch (operand) {
		case "+":
			this.add(type.suffix());
			break;

		case "-":
			this.sub(type.suffix());
			break;

		case "*":
			this.mult(type.suffix());
			break;

		case "/":
			this.div(type.suffix());
			break;
		case "%":
			this.mod(type.suffix());
			break;
		}
	}

	public void comp(String operand, Type type) {
		switch (operand) {
		case "<":
			this.lt(type.suffix());
			break;
		case ">":
			this.gt(type.suffix());
			break;
		case "<=":
			this.ge(type.suffix());
			break;
		case ">=":
			this.le(type.suffix());
			break;
		case "==":
			this.eq(type.suffix());
			break;
		case "!=":
			this.ne(type.suffix());
			break;
		}
	}

	public void logic(String operand, Type type) {
		switch (operand) {
		case "&&":
			this.and();
			break;
		case "||":
			this.or();
			break;
		}
	}

	// CODE PART

	private void generateCode(String code) {
		out.println("\t " + code);
		out.flush();
	}

	public void push(String suffix, int value) {
		this.generateCode("push" + suffix + " " + value);
	}

	public void push(String suffix, double value) {
		this.generateCode("push" + suffix + " " + value);
	}

	public void pusha(VarDefinition vd) {
		if (vd.getScope() == 0) {
			this.generateCode("pusha " + vd.getOffset());
		} else {
			// Relative position
			this.pushBP();
			this.push("i", vd.getOffset());
			this.add("i");
		}
	}

	public void load(String suffix) {
		this.generateCode("load" + suffix);
	}

	public void store(String suffix) {
		this.generateCode("store" + suffix);
	}

	public void pop(String suffix) {
		this.generateCode("pop" + suffix);
	}

	public void dup(String suffix) {
		this.generateCode("dup" + suffix);
	}

	public void add(String suffix) {
		this.generateCode("add" + suffix);
	}

	public void sub(String suffix) {
		this.generateCode("sub" + suffix);
	}

	public void mult(String suffix) {
		this.generateCode("mul" + suffix);
	}

	public void div(String suffix) {
		this.generateCode("div" + suffix);
	}

	public void mod(String suffix) {
		this.generateCode("mod" + suffix);
	}

	public void and() {
		this.generateCode("and");
	}

	public void or() {
		this.generateCode("or");
	}

	public void not() {
		this.generateCode("not");
	}

	public void gt(String suffix) {
		this.generateCode("gt" + suffix);
	}

	public void lt(String suffix) {
		this.generateCode("lt" + suffix);
	}

	public void ge(String suffix) {
		this.generateCode("ge" + suffix);
	}

	public void le(String suffix) {
		this.generateCode("le" + suffix);
	}

	public void eq(String suffix) {
		this.generateCode("eq" + suffix);
	}

	public void ne(String suffix) {
		this.generateCode("ne" + suffix);
	}

	public void in(String suffix) {
		this.generateCode("in" + suffix);
	}

	public void out(String suffix) {
		this.generateCode("out" + suffix);
	}

	public void jmp(String label) {
		this.generateCode("jmp " + label);
	}
	
	public void jmp(int labelNumber){
		this.generateCode("jmp label" + labelNumber );
	}

	public void jz(String label) {
		this.generateCode("jz " + label);
	}
	
	public void jz(int labelNumber){
		this.generateCode("jz label" + labelNumber);
	}

	public void jnz(String label) {
		this.generateCode("jnz " + label);
	}
	
	public void jnz(int labelNumber) {
		this.generateCode("jnz label" + labelNumber);
	}

	public void call(String label) {
		this.generateCode("call " + label);
	}

	public void ret(int returnValueSize, int localVariablesSize, int parametersSize) {
		this.generateCode("ret " + returnValueSize + ", " + localVariablesSize + ", " + parametersSize);
	}

	public void enter(int value) {
		this.generateCode("enter " + value);
	}

	public void nop() {
		this.generateCode("nop");
	}

	

}
