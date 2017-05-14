package ast.others;

import java.lang.reflect.*;

public class Formatter {
	
	public static <T> String showInfo(Class<T> className, String... elements){
			StringBuilder sb= new StringBuilder();
			Field[] attributes = className.getDeclaredFields();
			sb.append(className.getName()+":\n");
			for(Field att: attributes)
				sb.append("\t "+att.getName()+"\n");
			
			return sb.toString();
	}

}
