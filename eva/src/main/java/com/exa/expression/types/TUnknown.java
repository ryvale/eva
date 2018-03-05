package com.exa.expression.types;

import com.exa.expression.Type;

public class TUnknown extends Type<Object> {

	public TUnknown() {
		super(null);
	}

	@Override
	public String typeName() {
		return "unknown";
	}

}
