package com.exa.expression.eval.operators;

import com.exa.eva.EvaException;

import com.exa.expression.XPOperatorBase;
import com.exa.expression.eval.XPEvaluator;

public abstract class XPOprtUnary<T> extends XPOperatorBase<T> {
	
	public XPOprtUnary(String symbol, int priority) {
		super(symbol, priority, 1);
	}

	@Override
	public void resolve(XPEvaluator eval, int order, int nbOperands) throws EvaException {
		
	}
	
}
