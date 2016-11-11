package com.exa.expression;

public abstract class OperandBase<T extends XPItem<T>> implements Operand<T> {

	@Override
	public Operator<T> asOperator() { return null; }

	@Override
	public boolean isConstant() { return false; }

}
