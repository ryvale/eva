package com.exa.expression.types;

import java.util.Date;

import com.exa.expression.Type;
import com.exa.utils.ManagedException;

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

	@Override
	public Date convert(Object o) throws ManagedException {
		if(o == null) return null;
		
		if(o instanceof Date) return (Date)o;
		
		return super.convert(o);
	}
	
	
	
}
