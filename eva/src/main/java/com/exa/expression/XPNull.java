package com.exa.expression;

import java.util.Date;

import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public class XPNull<T> extends XPDynamicTypeOperand<T> {
	private Type<?> type;

	public XPNull() {
		this(ClassesMan.T_NULL);
	}

	public XPNull(Type<?> type) {
		this.type  = type;
	}

	@Override
	public T value(XPEvaluator evaluator) throws ManagedException {
		return null;
	}

	@Override
	public Type<?> type() {
		return type;
	}

	@Override
	public XPOperand<String> asOPString() {
		if(type == ClassesMan.T_NULL) return new XPNull<>(ClassesMan.T_STRING);
		return super.asOPString();
	}

	@Override
	public XPOperand<Date> asOPDate() {
		if(type == ClassesMan.T_NULL) return new  XPNull<>(ClassesMan.T_DATE);
		return super.asOPDate();
	}

	@Override
	public XPOperand<Integer> asOPInteger() {
		if(type == ClassesMan.T_NULL) return new  XPNull<>(ClassesMan.T_INTEGER);
		return super.asOPInteger();
	}

	@Override
	public XPOperand<Boolean> asOPBoolean() {
		if(type == ClassesMan.T_NULL) return new  XPNull<>(ClassesMan.T_BOOLEAN);
		return super.asOPBoolean();
	}

	@Override
	public XPOperand<Double> asOPDouble() {
		if(type == ClassesMan.T_NULL) return new  XPNull<>(ClassesMan.T_DOUBLE);
		return super.asOPDouble();
	}

}
