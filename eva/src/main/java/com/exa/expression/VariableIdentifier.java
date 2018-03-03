package com.exa.expression;

import com.exa.utils.ManagedException;

public class VariableIdentifier extends Identifier {
	private Class<?> valueClass;

	public VariableIdentifier(String name, Class<?> valueClass) {
		super(name, IDType.VARIABLE);
		this.name = name;
		this.valueClass = valueClass;
	}
	
	public Class<?> valueClass() {
		return valueClass;
	}


	@Override
	public VariableIdentifier asVariableIdentifier() {
		return this;
	}

}
