package com.exa.expression.types;

import com.exa.expression.TypeMan;

public class TMDouble extends TypeMan<Double> {

	@SuppressWarnings("unchecked")
	@Override
	public TypeMan<Double> specificType() {
		return this;
	}
	
}
