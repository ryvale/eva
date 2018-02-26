package com.exa.expression;

import java.util.Date;

import com.exa.eva.EvaException;

public class XPConstant<T> extends XPOperandBase<T> {
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


	@Override
	public XPOperand<String> asOPString() {
		return TypeMan.STRING.valueOrNull(this);
	}

	@Override
	public XPOperand<Date> asOPDate() {
		return TypeMan.DATE.valueOrNull(this);
	}

	@Override
	public XPOperand<String> asOPIdentifier() {
		
		return super.asOPIdentifier();
	}

	@Override
	public XPOperand<Integer> asOPInteger() {
		return TypeMan.INTEGER.valueOrNull(this);
	}

	@Override
	public XPOperand<Boolean> asOPBoolean() {
		return TypeMan.BOOLEAN.valueOrNull(this);
	}
	
	

}
