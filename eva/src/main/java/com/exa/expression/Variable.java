package com.exa.expression;

import com.exa.utils.ManagedException;

public interface Variable<T> {

	T value() throws ManagedException;

	void value(T value);
	
	void valueToSet(Object value) throws ManagedException;

	String name();
	
	Class<?> valueClass();

}