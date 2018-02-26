package com.exa.eva;

public interface Item<T extends Item<T, _E>, _E extends StackEvaluator<T>> {
	Operand<T, _E> asOperand();
	Operator<T, _E> asOperator();
	
	T asSpecificItem();
}
