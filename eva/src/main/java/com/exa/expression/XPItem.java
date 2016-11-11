package com.exa.expression;

public interface XPItem<T extends XPItem<T>> {
	public Operand<T> asOperand();
	public Operator<T> asOperator();
	
	public T asSpecificItem();
}
