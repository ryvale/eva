package com.exa.expression.types;

import com.exa.expression.TypeMan;

public class TMBoolean extends TypeMan<Boolean> {

	
	@SuppressWarnings("unchecked")
	@Override
	public TypeMan<Boolean> specificType() {
		return this;
	}
	
}
