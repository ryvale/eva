package com.exa.expression;

import com.exa.utils.ManagedException;

public class VariableIdentifier extends Identifier {
	private Class<?> valueClass;
	
	private VariableContext variablesContext;

	public VariableIdentifier(String name, Class<?> valueClass, VariableContext variablesContext) {
		super(name, IDType.VARIABLE);
		this.name = name;
		this.valueClass = valueClass;
		this.variablesContext = variablesContext;
	}
	
	public Class<?> valueClass() {
		return valueClass;
	}
	
	public Object value() throws ManagedException {
		Variable<?> res = variablesContext.getVariable(name);
		
		if(res == null) throw new ManagedException(String.format("Unknow variable or property %s", name));
		
		return res.value();
	}

	@Override
	public VariableIdentifier asVariableIdentifier() {
		return this;
	}
	
	
	
}
