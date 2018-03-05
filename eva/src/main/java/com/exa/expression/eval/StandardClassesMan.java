package com.exa.expression.eval;

import java.util.Date;

public class StandardClassesMan extends ClassesMan {

	public StandardClassesMan() {
		super();
		
		types.put(String.class, T_STRING);
		types.put(Integer.class, T_INTEGER);
		types.put(Double.class, T_DOUBLE);
		types.put(Boolean.class, T_BOOLEAN);
		types.put(Date.class, T_DATE);
	}
	
}
