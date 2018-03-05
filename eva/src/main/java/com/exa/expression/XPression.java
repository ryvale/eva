package com.exa.expression;

import com.exa.eva.Item;
import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.XPEvaluator;

public interface XPression<T> extends Item<XPression<?>, XPOperand<?>, XPOperator<?>, XPEvaluator, OM>  {
	
	public final static XPOperand<Boolean> TRUE = new XPConstant<>(Boolean.TRUE, ClassesMan.T_BOOLEAN);
	
	public final static XPOperand<Boolean> FALSE = new XPConstant<>(Boolean.FALSE, ClassesMan.T_BOOLEAN);
	
	@Override
	XPOperand<T> asOperand();
	
	@Override
	XPOperator<T> asOperator();
	
	Type<?> type();
	
}
