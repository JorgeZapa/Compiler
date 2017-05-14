import parser.Parser;
import java.io.FileReader;
import java.io.IOException;

import ast.Program;
import codegenerator.CodeGenerator;
import errorhandler.EH;
import scanner.Scanner;
import visitors.IdentificationVisitor;
import visitors.OffsetVisitor;
import visitors.TypeCheckingVisitor;
import visitors.Visitor;
import visitors.codegeneration.ExecuteCGVisitor;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorTree;

public class Main {

	public static void main(String args[]) throws IOException {
	    if (args.length<1) {
	        System.err.println("Pass me the name of the input file.");
	        return;
	    }
	        
		FileReader fr=null;
		try {
			fr=new FileReader(args[0]);
		} catch(IOException io) {
			System.err.println("The file "+args[0]+" could not be opened.");
			return;
		}
		
		Scanner scanner = new Scanner(fr);
		Parser parser = new Parser(scanner);
		parser.run();	
		
		CodeGenerator.sourceFile=args[0];
		
		Visitor tCheckVisitor = new TypeCheckingVisitor();
		IdentificationVisitor iVisitor = new IdentificationVisitor();
		OffsetVisitor oVisitor = new OffsetVisitor();
		ExecuteCGVisitor ecgVisitor = new ExecuteCGVisitor("mi.output.txt");
		
		Program program=(Program) parser.getAST();
		
		iVisitor.visit(program, null);
		tCheckVisitor.visit(program, null);
		oVisitor.visit(program, null);
		
		
		if(EH.getEH().anyError()){			
			EH.getEH().showErrors(System.err);
		}
		else{						
			ecgVisitor.visit(program, null);
			IntrospectorModel model=new IntrospectorModel("Program",parser.getAST());
			new IntrospectorTree("Introspector", model);		
		}	
	}
}