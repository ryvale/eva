package com.exa.expression;

import java.util.Date;

public abstract class XPDynamicTypeOperand<T> extends XPOperandBase<T> {

	@Override
	public XPOperand<String> asOPString() {
		return TypeMan.STRING.valueOrNull(this);
	}

	@Override
	public XPOperand<Date> asOPDate() {
		return TypeMan.DATE.valueOrNull(this);
	}

	@Override
	public XPOperand<Integer> asOPInteger() {
		return TypeMan.INTEGER.valueOrNull(this);
	}

	@Override
	public XPOperand<Boolean> asOPBoolean() {
		return TypeMan.BOOLEAN.valueOrNull(this);
	}

	@Override
	public XPOperand<Double> asOPDouble() {
		return TypeMan.DOUBLE.valueOrNull(this);
	}
	
	
	

	
}
