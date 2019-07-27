package com.exa.expression;

import com.exa.expression.eval.MemoryVariable;
import com.exa.expression.eval.XPressionVariable;
import com.exa.utils.ManagedException;

public interface Variable<T> {

	T value() throws ManagedException;

	void value(T value);
	
	void valueToSet(Object value) throws ManagedException;

	String name();
	
	Class<?> valueClass();
	
	MemoryVariable<T> asMemoryVariable();
	
	XPressionVariable<T> asXPressionVariable();

}