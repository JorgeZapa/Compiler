package visitors.codegeneration;

import java.io.FileNotFoundException;

import ast.Program;
import ast.definitions.Definition;
import ast.definitions.FuncDefinition;
import ast.definitions.VarDefinition;
import ast.expresions.Expression;
import ast.statements.Assignment;
import ast.statements.IfStatement;
import ast.statements.Invocation;
import ast.statements.Read;
import ast.statements.Return;
import ast.statements.Statement;
import ast.statements.WhileStatement;
import ast.statements.Write;
import ast.types.FunctionType;
import ast.types.IntType;
import codegenerator.CodeGenerator;

public class ExecuteCGVisitor extends AbstractCGVisitor {

	private CodeGenerator cg;
	private AddessCGVisitor av;
	private ValueCGVisitor vv;

	public ExecuteCGVisitor(String outputFile) throws FileNotFoundException {
		cg = new CodeGenerator(outputFile);
		av = new AddessCGVisitor(cg);
		vv = new ValueCGVisitor(cg);
		this.av.setVv(vv);
		this.vv.setAv(av);
	}

	@Override
	public Object visit(Program prog, Object o) {
		// Commenting the GLOBAL definitions
		for (Definition d : prog.getDefinitions()) {
			if (d instanceof VarDefinition)
				d.accept(this, o);
		}
		// Real Code starts here
		cg.callMain();
		for (Definition d : prog.getDefinitions()) {
			if (d instanceof FuncDefinition)
				d.accept(this, o);
		}

		return null;
	}

	@Override
	public Object visit(VarDefinition vd, Object o) {
		generateVariableInfo(vd);
		return null;
	}

	@Override
	public Object visit(FuncDefinition fd, Object o) {
		// Label
		cg.generateLabel(fd.getName());
		// Comments
		cg.generateComment("Parameters");
		
		fd.getType().accept(this, o);

		cg.generateComment("Local variables");
		for (Statement stmt : fd.getStatements()) {
			if (stmt instanceof VarDefinition) {
				// localVariableSize += ((VarDefinition)
				// stmt).getType().getNumberOfBytes();
				stmt.accept(this, o);
			}
		}
		// Real Code
		cg.enter(fd.totalLocalVariableSize);

		for (Statement stmt : fd.getStatements()) {
			if (!(stmt instanceof VarDefinition)) {
				stmt.accept(this, fd);
			}
		}
		// TODO: Instanceof o simplestring o numberofbytes QUITAR ESTO
		if (fd.getType().getNumberOfBytes() == 0) {
			cg.ret(0, fd.totalLocalVariableSize, fd.totalParameterSize);
		}

		return null;
	}

	@Override
	public Object visit(Assignment assign, Object o) {
		cg.generateLine(assign.getLine());
		cg.generateComment("Assignment");
		assign.getFirstExpression().accept(av, o);
		assign.getSecondExpression().accept(vv, o);
		cg.convertTo(assign.getSecondExpression().getType(), assign
				.getFirstExpression().getType());
		cg.store(assign.getFirstExpression().getType().suffix());
		return null;
	}
	
	@Override
	public Object visit(FunctionType type, Object o){
		for(VarDefinition vd:type.getParameters()){
			generateVariableInfo(vd);
		}
		return null;
	}

	@Override
	public Object visit(Return ret, Object o) {
		cg.generateLine(ret.getLine());
		cg.generateComment("Return");
		FuncDefinition fd = (FuncDefinition) o;
		ret.getExpression().accept(vv, o);
		cg.convertTo(ret.getExpression().getType(), ((FunctionType)fd.getType()).getRet());
		cg.ret(ret.getExpression().getType().getNumberOfBytes(),
				fd.totalLocalVariableSize, fd.totalParameterSize);
		return null;
	}

	@Override
	public Object visit(Invocation inv, Object o) {
		cg.generateLine(inv.getLine());
		cg.generateComment("Invocation");
		inv.accept(vv, o);
		if (!(inv.getType().getNumberOfBytes()==0)) {
			cg.pop(inv.getFunction().getType().suffix()); // TODO, mirar aqui si
															// es void?
		}
		return null;
	}

	@Override
	public Object visit(Write write, Object o) {
		cg.generateLine(write.getLine());
		int count = 0;
		for (Expression exp : write.getExpressions()) {
			cg.generateComment("Write part " + count);
			exp.accept(vv, o);
			cg.out(exp.getType().suffix());
			count++;
		}
		return null;
	}

	@Override
	public Object visit(Read read, Object o) {
		cg.generateLine(read.getLine());
		int count = 0;
		for (Expression exp : read.getExpressions()) {
			cg.generateComment("Read part " + count);
			exp.accept(av, o);
			cg.in(exp.getType().suffix());
			cg.store(exp.getType().suffix());
			count++;
		}
		return null;
	}

	@Override
	public Object visit(WhileStatement wStmt, Object o) {
		cg.generateLine(wStmt.getCondition().getLine());
		cg.generateComment("While");
		int labelNumber = cg.getLables(2);
		cg.generateLabel(labelNumber);
		wStmt.getCondition().accept(vv, o);
		cg.convertTo(wStmt.getCondition().getType(), IntType.getInstance());
		cg.jz(labelNumber + 1);
		cg.generateComment("Body of the while statement");
		for (Statement stmt : wStmt.getStatements()) {
			stmt.accept(this, o);
		}
		cg.jmp(labelNumber);
		cg.generateLabel(labelNumber + 1);

		return null;
	}

	@Override
	public Object visit(IfStatement ifStmt, Object o) {
		cg.generateLine(ifStmt.getCondition().getLine());
		cg.generateComment("IF");
		int labelNumber = cg.getLables(2);
		ifStmt.getCondition().accept(vv, o);
		cg.convertTo(ifStmt.getCondition().getType(), IntType.getInstance());
		cg.jz(labelNumber);
		cg.generateComment("Body of the if statement");
		for (Statement stmt : ifStmt.getIfBody()) {
			stmt.accept(this, o);
		}
		cg.jmp(labelNumber + 1);
		cg.generateLabel(labelNumber);
		cg.generateComment("Body of the else statement");
		for (Statement stmt : ifStmt.getElseBody()) {
			stmt.accept(this, o);
		}
		cg.generateLabel(labelNumber + 1);

		return null;

	}

	
	private void generateVariableInfo(VarDefinition vd){
		StringBuilder sb = new StringBuilder();
		sb.append(vd.getType().codeInfo());
		sb.append(" " + vd.getName());
		sb.append(" (offset " + vd.getOffset() + ")");
		cg.generateComment(sb.toString());
	}
}
