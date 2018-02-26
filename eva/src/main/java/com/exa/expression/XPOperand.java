package com.exa.expression;

import java.util.Date;

import com.exa.eva.EvaException;
import com.exa.eva.Operand;
import com.exa.expression.eval.XPEvaluator;


public interface XPOperand<T> extends Operand<XPression<?>, XPEvaluator>, XPression<T> {
	
	T value() throws EvaException;

	XPOperand<T> asSpecificItem();

	XPOperand<String> asOPString();

	XPOperand<Date> asOPDate() ;
	
	XPOperand<String> asOPIdentifier();
	
	XPOperand<Integer> asOPInteger();
	
	XPOperand<Boolean> asOPBoolean();
	
	TypeMan<?> type();
	
}
