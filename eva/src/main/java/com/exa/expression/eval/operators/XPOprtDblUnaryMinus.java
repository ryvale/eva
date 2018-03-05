package com.exa.expression.eval.operators;

import com.exa.expression.Type;
import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.XPEvaluator;

public class XPOprtDblUnaryMinus extends XPOprtUnary<Double> {

	public XPOprtDblUnaryMinus(String symbol) {
		super(symbol);
	}
	
	@Override
	public Type<?> type() {
		return ClassesMan.T_DOUBLE;
	}

	@Override
	public boolean canManage(XPEvaluator eval, int order, int nbOperands) {
		
		return false;
	}

}
