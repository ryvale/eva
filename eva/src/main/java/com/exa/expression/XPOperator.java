package com.exa.expression;

import com.exa.eva.Operator;
import com.exa.expression.eval.XPEvaluator;

public interface XPOperator<T> extends Operator<XPression<?>, XPEvaluator>, XPression<T> {
	TypeMan<?> getType(XPEvaluator eval, int order, int nbOperands);
}
