package com.exa.expression;

import com.exa.eva.EvaException;

public class XPConstant<T> extends XPDynamicTypeOperand<T> {
	private T v;
	private TypeMan<?> type;

	public XPConstant(T v) {
		super();
		this.v = v;
		
		type = TypeMan.getType(v);
	}

	@Override
	public boolean isConstant() { return true; }

	@Override
	public T value() throws EvaException {
		return v;
	}

	@Override
	public TypeMan<?> type() {
		return type;
	}
	
}
