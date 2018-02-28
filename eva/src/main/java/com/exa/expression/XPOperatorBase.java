package com.exa.expression;

import com.exa.eva.ComputedItem;
import com.exa.eva.OperatorBase;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public abstract class XPOperatorBase<T> extends OperatorBase<XPression<?>, XPOperand<?>, XPOperator<?>, XPEvaluator, OM> implements XPOperator<T> {
	
	public XPOperatorBase(String symbol, int nbOperands) {
		super(symbol, nbOperands);
	}
	
	@Override
	public XPOperator<T> asOperator() { return this; }
	
	@Override
	public XPOperand<T> asOperand() { return null; }
	
	protected XPOperand<?> resolveOperand(XPEvaluator eval, ComputedItem<XPression<?>, XPression<?>, ?> ci) throws ManagedException {
		XPression<?> item = ci.item();
		XPOperand<?> oprd = item.asOperand();
		if(oprd == null) {
			item.asOperator().resolve(eval, ci.order(), ci.asComputedOperator().nbOperands());
			oprd = eval.popOperand().item().asOperand();
		}
		
		return oprd;
	}
	
	protected XPOperand<?> resolveOperand(XPEvaluator eval) throws ManagedException {
		ComputedItem<XPression<?>, XPression<?>, ?> ci = eval.popOperand();
		return resolveOperand(eval, ci);
	}
	
	
}
