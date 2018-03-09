package com.exa.expression;

import com.exa.eva.Operator;
import com.exa.expression.eval.XPEvaluator;

public interface XPOperator<T> extends Operator<XPression<?>, XPOperand<?>, XPOperator<?>, XPEvaluator, OM>, XPression<T> {
	
	
}
