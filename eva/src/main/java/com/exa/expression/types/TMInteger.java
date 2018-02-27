package com.exa.expression.types;

import com.exa.expression.TypeMan;

public class TMInteger extends TypeMan<Integer> {

	@SuppressWarnings("unchecked")
	@Override
	public TypeMan<Integer> specificType() {
		return this;
	}
	
}
