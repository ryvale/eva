package com.exa.expression;

import com.exa.eva.Item;
import com.exa.expression.eval.XPEvaluator;

public interface XPression<T> extends Item<XPression<?>, XPEvaluator>  {
	
	public final static XPOperand<Boolean> TRUE = new XPConstant<Boolean>(Boolean.TRUE);
	
	public final static XPOperand<Boolean> FALSE = new XPConstant<Boolean>(Boolean.FALSE);
	
	@Override
	XPOperand<T> asOperand();
	
	@Override
	XPOperator<T> asOperator();
	
}
