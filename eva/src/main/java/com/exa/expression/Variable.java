package com.exa.expression;

public interface Variable<T> {

	T value();

	void value(T value);

	String name();
	
	VariableContext variableContext();
	
	Class<?> valueClass();

}