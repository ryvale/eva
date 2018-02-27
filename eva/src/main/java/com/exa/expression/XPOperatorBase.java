package com.exa.expression;

import com.exa.eva.OperatorBase;
import com.exa.expression.eval.XPComputedItem;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public abstract class XPOperatorBase<T> extends OperatorBase<XPression<?>, XPEvaluator> implements XPOperator<T> {
	
	public XPOperatorBase(String symbol, Integer priority, int nbOperands) {
		super(symbol, priority, nbOperands);
	}
	
	@Override
	public XPOperator<T> asOperator() { return this;	}
	
	@Override
	public XPOperator<T> asSpecificItem() { return this; }
	
	@Override
	public XPOperand<T> asOperand() { return null; }
	
	protected XPOperand<?> resolveOperand(XPEvaluator eval, XPComputedItem<XPression<?>> ci) throws ManagedException {
		XPression<?> item = ci.item();
		XPOperand<?> oprd = item.asOperand();
		if(oprd == null) {
			item.asOperator().resolve(eval, ci.order(), ci.asComputedOperator().nbOperands());
			oprd = eval.popOutput().item().asOperand();
		}
		
		return oprd;
	}
	
	protected XPOperand<?> resolveOperand(XPEvaluator eval) throws ManagedException {
		XPComputedItem<XPression<?>> ci = eval.popOutput();
		return resolveOperand(eval, ci);
	}
	
	
}
