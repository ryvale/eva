package com.exa.expression.types;

import java.util.Date;

import com.exa.expression.TypeMan;

public class TMDate extends TypeMan<Date> {

	@SuppressWarnings("unchecked")
	@Override
	public TypeMan<Date> specificType() {
		return this;
	}
	
}
