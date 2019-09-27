package com.exa.expression.types;

import com.exa.expression.Type;

public class TNull extends Type<Object> {

	public TNull() {
		super(null);
	}

	@Override
	public String typeName() {	return "null"; }

	@Override
	public boolean isSubTypeOf(Type<?> type) {
		return true;
	}

	@Override
	public boolean canBeComputedBy(Type<?> type) {
		return true;
	}
	
	

}

