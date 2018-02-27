package com.exa.expression.eval;

import java.util.HashMap;
import java.util.Map;

import com.exa.expression.Variable;
import com.exa.expression.VariableContext;
import com.exa.utils.ManagedException;

public class MapVariableContext implements VariableContext {
	
	private Map<String, MemoryVariable<?>> variables = new HashMap<>();
	
	private VariableContext parent;
	
	public MapVariableContext(VariableContext parent) {
		super();
		this.parent = parent;
	}
	
	public MapVariableContext() { this(null); }

	@Override
	public <T>Variable<T> addVariable(String name, Class<?> valueClass, T defaultValue) throws ManagedException {
		if(variables.containsKey(name)) throw new ManagedException(String.format("The variable %s alredy exists", name));
		
		MemoryVariable<T> res = new MemoryVariable<T>(this, name, valueClass, defaultValue);
		variables.put(name, res);
		
		return res;
	}

	@Override
	public VariableContext parent() {
		return parent;
	}

	@Override
	public MemoryVariable<?> getContextVariable(String name) {
		return variables.get(name);
	}

	@Override
	public Variable<?> getVariable(String name) {
		VariableContext vc = this;
		Variable<?> res = null;
		do {
			res = vc.getContextVariable(name);
			if(res != null) break;
			
			vc = vc.parent();
			
		} while(vc != null);
		
		return res;
	}
	
	

}
