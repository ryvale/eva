package com.exa.expression;

import com.exa.utils.ManagedException;

public interface VariableContext {
	
	<T>Variable<T> addVariable(String name, Class<?> valueClass, T defaultValue) throws ManagedException;
	
	void releaseVariable(String name);
	
	VariableContext getParent();
	
	void setParent(VariableContext vc);
	
	Variable<?> getContextVariable(String name);
	
	Variable<?> getVariable(String name);
	
	void clearVariable();
	
	
}
