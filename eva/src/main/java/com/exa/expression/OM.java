package com.exa.expression;

import com.exa.eva.OperatorManager;
import com.exa.expression.eval.XPEvaluator;

public abstract class OM implements OperatorManager<XPression<?>, XPOperand<?>, XPOperator<?>, XPEvaluator, OM> {

	@Override
	public boolean canCumulateOperands() {
		return false;
	}
	
	
	public OMMutiple<?> asOMM() {return null; }
	

}
