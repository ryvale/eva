package com.exa.expression.eval;

import com.exa.expression.Variable;
import com.exa.expression.VariableContext;

public class MemoryVariable<T> implements Variable<T> {
	private String name;
	private T value;
	
	private VariableContext variableContrext;
	private Class<?> valueClass;
	
	public MemoryVariable(VariableContext variableContrext, String name, Class<?> valueClass, T value) {
		super();
		this.name = name;
		this.value = value;
		this.variableContrext = variableContrext;
		this.valueClass = valueClass;
	}
	
	public MemoryVariable(VariableContext variableContrext, String name, Class<?> valueClass) {
		this(variableContrext, name, valueClass, null);
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
	public VariableContext variableContext() {
		return variableContrext;
	}

	@Override
	public Class<?> valueClass() {
		
		return valueClass;
	}
	
	
	
	
}
