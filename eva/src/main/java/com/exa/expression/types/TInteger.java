package com.exa.expression.types;

import com.exa.expression.Type;

public class TInteger extends Type<Integer> {

	public TInteger() {
		super(Integer.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type<Integer> specificType() {
		return this;
	}

	@Override
	public String typeName() {
		return "int";
	}
	
}
