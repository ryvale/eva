package com.exa.expression;

import java.util.Date;

import com.exa.eva.OperandBase;
import com.exa.expression.eval.XPEvaluator;

public abstract class XPOperandBase<T> extends OperandBase<XPression<?>, XPOperand<?>, XPOperator<?>, XPEvaluator, OM> implements XPOperand<T> {
	@Override
	public XPOperand<T> asOperand() { return this; }
	
	@Override
	public XPOperator<T> asOperator() { return null; }
	
	@Override
	public XPOperand<String> asOPString() { return null; }
	
	@Override
	public XPOperand<Date> asOPDate() { return null; }
	
	@Override
	public XPIdentifier<?> asOPIdentifier() { return null; }

	@Override
	public XPOperand<Integer> asOPInteger() {	return null; }
	
	@Override
	public XPOperand<Boolean> asOPBoolean() { return null; }
	
	@Override
	public XPOperand<Double> asOPDouble() { return null; }

	@Override
	public boolean isNull() { return false;	}
	
	
	
}


