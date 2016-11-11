package com.exa.expression;

import java.util.Map;

public abstract class SubExpressionFactory<T extends XPItem<T>> {
	
	public abstract Map<String, SubExpOperandMan<T>> subExpManagers(StackEvaluator<T> evaluator);
	
	
}