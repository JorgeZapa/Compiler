package errorhandler;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import ast.types.ErrorType;

public class EH {
	
	private static EH instance= new EH();
	private List<ErrorType> errors= new ArrayList<ErrorType>();
	private EH(){
		
	}
	
	public static EH getEH(){
		return instance;
	}
	
	public boolean anyError(){
		return !errors.isEmpty();
	}
	
	public void addError(ErrorType error){
		errors.add(error);
	}
	
	public void showErrors(PrintStream stream){
		for(ErrorType error: errors){
			stream.println(error.toString());
		}
	}
	
	

}
