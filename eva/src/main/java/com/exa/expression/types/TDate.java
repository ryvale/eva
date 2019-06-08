package com.exa.expression.types;

import java.util.Date;

import com.exa.expression.Type;

public class TDate extends Type<Date> {

	public TDate() {
		super(Date.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type<Date> specificType() {
		return this;
	}

	@Override
	public String typeName() {
		return "date";
	}
	
}
