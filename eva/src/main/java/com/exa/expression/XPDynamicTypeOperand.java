package com.exa.expression;

import java.util.Date;

import com.exa.expression.eval.ClassesMan;

public abstract class XPDynamicTypeOperand<T> extends XPOperandBase<T> {

	@Override
	public XPOperand<String> asOPString() {
		return ClassesMan.T_STRING.valueOrNull(this);
	}

	@Override
	public XPOperand<Date> asOPDate() {
		return ClassesMan.T_DATE.valueOrNull(this);
	}

	@Override
	public XPOperand<Integer> asOPInteger() {
		return ClassesMan.T_INTEGER.valueOrNull(this);
	}

	@Override
	public XPOperand<Boolean> asOPBoolean() {
		return ClassesMan.T_BOOLEAN.valueOrNull(this);
	}

	@Override
	public XPOperand<Double> asOPDouble() {
		return ClassesMan.T_DOUBLE.valueOrNull(this);
	}
	
	
	

	
}
