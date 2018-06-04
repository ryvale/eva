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

	<T>void assignContextVariable(String name, T value);
	<T>void assignContextVariable(String name, T value, Class<?> valueClass);
	
	<T>void assignVariable(String name, T value) throws ManagedException;
	
	<T>void assignOrDeclareVariable(String name, Class<?> valueClass, T value);
	
	
}
