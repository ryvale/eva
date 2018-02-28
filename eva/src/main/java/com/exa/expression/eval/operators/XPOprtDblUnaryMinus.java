package com.exa.expression.eval.operators;

import com.exa.expression.TypeMan;
import com.exa.expression.eval.XPEvaluator;

public class XPOprtDblUnaryMinus extends XPOprtUnary<Double> {

	public XPOprtDblUnaryMinus(String symbol) {
		super(symbol);
	}
	
	@Override
	public TypeMan<?> type() {
		return TypeMan.DOUBLE;
	}

	@Override
	public boolean canManage(XPEvaluator eval, int order, int nbOperands) {
		
		return false;
	}

}
