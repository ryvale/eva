package com.exa.expression.types;

import com.exa.expression.Type;
import com.exa.utils.ManagedException;

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

	@Override
	public Boolean convert(Object o) throws ManagedException {
		if(o == null) return null;
		
		if(o instanceof Boolean) return (Boolean)o;
		return super.convert(o);
	}
	
	
	
}
