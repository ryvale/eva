package com.exa.eva;

public interface StackEvaluator<T extends Item<T, ? extends StackEvaluator<T>>> {

	ComputedOperator<T, ? extends StackEvaluator<T>> topOperator();
	
	void pushOperator(T oprd);
	
	void pushOperand(T item);
	
	void push(T oprt);
	
	int numberOfOperands();
	
	ComputedItem<T, ? extends StackEvaluator<T>> getStackOperand(int indexFormTop);
	
	ComputedItem<T, ? extends StackEvaluator<T>> popOperand();
}
