package com.exa.expression.types;

import com.exa.expression.Type;

public class TBoolean extends Type<Boolean> {

	
	public TBoolean() {
		super(Boolean.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type<Boolean> specificType() {
		return this;
	}

	@Override
	public String typeName() {
		return "boolean";
	}
	
}
