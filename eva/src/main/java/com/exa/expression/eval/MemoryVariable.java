package com.exa.expression.eval;

import com.exa.expression.Variable;
import com.exa.utils.ManagedException;

public class MemoryVariable<T> implements Variable<T> {
	private String name;
	private T value;
	
	private Class<?> valueClass;
	
	public MemoryVariable(String name, Class<?> valueClass, T value) {
		super();
		this.name = name;
		this.value = value;
		this.valueClass = valueClass;
		
		
	}
	
	public MemoryVariable(String name, Class<?> valueClass) {
		this(name, valueClass, null);
	}

	/* (non-Javadoc)
	 * @see com.exa.expression.eval.Variable#value()
	 */
	@Override
	public T value() {
		return value;
	}

	/* (non-Javadoc)
	 * @see com.exa.expression.eval.Variable#value(T)
	 */
	@Override
	public void value(T value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see com.exa.expression.eval.Variable#name()
	 */
	@Override
	public String name() {
		return name;
	}

	@Override
	public Class<?> valueClass() {
		return valueClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void valueToSet(Object value) throws ManagedException {
		if(valueClass.isInstance(value)) this.value = (T)value;
		
		throw new ManagedException(String.format("The variable %s canot be assigned by %", name, value));
	}

	@Override
	public MemoryVariable<T> asMemoryVariable() {
		return this;
	}

	@Override
	public XPressionVariable<T> asXPressionVariable() {
		return null;
	}

	@Override
	public String toString() {
		return value == null ? (valueClass == null ? "null" : String.format("(%s)null", valueClass)) : value.toString();
	}
	
	
	
	
}
