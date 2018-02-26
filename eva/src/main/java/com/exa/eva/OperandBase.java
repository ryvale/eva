package com.exa.eva;

public abstract class OperandBase<T extends Item<T, _E>, _E extends StackEvaluator<T>> implements Operand<T, _E> {

	@Override
	public Operator<T, _E> asOperator() { return null; }


	@Override
	public boolean isConstant() { return false;	}

}
