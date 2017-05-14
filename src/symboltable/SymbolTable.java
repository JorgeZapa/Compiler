package symboltable;

import java.util.*;

import ast.definitions.Definition;

public class SymbolTable {
	
	private int scope=0;
	private List<Map<String,Definition>> table;
	public SymbolTable()  {
		table = new ArrayList<Map<String,Definition>>();
		table.add(new HashMap<String,Definition>());
	}

	public void set() {
		table.add(new HashMap<String,Definition>());
		scope++;
		//Every time we create a new scope. scope ++
	}
	
	public void reset() {
		table.remove(scope);
		scope--;
		//Every time we end a scope and we must delete the current scope map. scope --
	}
	
	public boolean insert(Definition definition) {
		definition.setScope(scope);
		if(findInCurrentScope(definition.getName())==null){
			table.get(scope).put(definition.getName(), definition);
			return true;
		}
		else{
			return false;
		}
		//Set the scope of the definition passed.
		//If you are able to insert in the table in the current scope.
	}
	
	public Definition find(String id) {
		int currentScope=scope;
		while(currentScope>=0){
			Map<String,Definition> currentMap=table.get(currentScope);
			if(currentMap.containsKey(id)){
				return currentMap.get(id);
			}
			currentScope--;
				
		}
		
		return null;
		//Use this in the insert.
		
		//In all the maps starting from the deepest one.
	}

	public Definition findInCurrentScope(String id) {
		return table.get(scope).get(id);
	}
}
