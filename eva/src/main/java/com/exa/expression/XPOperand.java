package com.exa.expression;

import java.util.Date;

import com.exa.eva.Operand;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;


public interface XPOperand<T> extends Operand<XPression<?>, XPEvaluator>, XPression<T> {
	
	T value() throws ManagedException;

	XPOperand<T> asSpecificItem();

	XPOperand<String> asOPString();

	XPOperand<Date> asOPDate() ;
	
	XPIdentifier<?> asOPIdentifier();
	
	XPOperand<Integer> asOPInteger();
	
	XPOperand<Boolean> asOPBoolean();
	
	XPOperand<Double> asOPDouble() ;
	
	TypeMan<?> type();
	
}
