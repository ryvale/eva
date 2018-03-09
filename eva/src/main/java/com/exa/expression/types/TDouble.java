package com.exa.expression.types;

import com.exa.expression.Type;
import com.exa.utils.ManagedException;

public class TDouble extends Type<Double> {

	public TDouble() {
		super(Double.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type<Double> specificType() {
		return this;
	}

	@Override
	public String typeName() {
		return "float";
	}

	@Override
	public Double convert(Object o) throws ManagedException {
		if(o == null) return null;
		
		if(o instanceof Number) {
			Number nb = (Number)o;
			
			return nb.doubleValue();
		}
		
		
		return super.convert(o);
	}
	
}
