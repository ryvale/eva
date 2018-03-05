package com.exa.expression.types;

import com.exa.expression.Type;

public class TDouble extends Type<Double> {

	public TDouble() {
		super(Double.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type<Double> specificType() {
		return this;
	}

	@Override
	public String typeName() {
		return "float";
	}
	
}
