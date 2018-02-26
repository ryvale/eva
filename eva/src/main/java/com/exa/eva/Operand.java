package com.exa.eva;

public interface Operand<T extends Item<T, _E>, _E extends StackEvaluator<T>> extends Item<T,_E> {

	boolean isConstant();

}