package com.exa.expression;

import java.util.Date;

import com.exa.eva.Operand;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;


public interface XPOperand<T> extends Operand<XPression<?>, XPOperand<?>, XPOperator<?>, XPEvaluator, OM>, XPression<T> {
	
	T value(XPEvaluator evaluator) throws ManagedException;

	//XPOperand<T> asSpecificItem();

	XPOperand<String> asOPString();

	XPOperand<Date> asOPDate() ;
	
	XPIdentifier<?> asOPIdentifier();
	
	XPOperand<Integer> asOPInteger();
	
	XPOperand<Boolean> asOPBoolean();
	
	XPOperand<Double> asOPDouble() ;
	
	Type<?> type();
	
}
