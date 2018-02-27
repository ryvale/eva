package com.exa.expression;

import com.exa.utils.ManagedException;

public interface VariableContext {
	
	<T>Variable<T> addVariable(String name, Class<?> valueClass, T defaultValue) throws ManagedException;
	
	VariableContext parent();
	
	Variable<?> getContextVariable(String name);
	
	Variable<?> getVariable(String name);
	
	
}
