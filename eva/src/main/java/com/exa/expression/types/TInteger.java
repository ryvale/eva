package com.exa.expression.types;

import com.exa.expression.Type;
import com.exa.expression.eval.ClassesMan;
import com.exa.utils.ManagedException;

public class TInteger extends Type<Integer> {

	public TInteger() {
		super(Integer.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type<Integer> specificType() {
		return this;
	}

	@Override
	public String typeName() {
		return "int";
	}

	@Override 
	public boolean canBeComputedBy(Type<?> type) {
		return type == ClassesMan.T_DOUBLE || type == this;
	}

	@Override
	public Integer convert(Object o) throws ManagedException {
		if(o == null) return null;
		
		if(o instanceof Integer) return (Integer)o;
		
		return super.convert(o);
	}

	
	
	
	
}
