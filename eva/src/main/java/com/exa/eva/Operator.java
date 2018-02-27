package com.exa.eva;

import com.exa.utils.ManagedException;

public interface Operator<T extends Item<T, _E>, _E extends StackEvaluator<T>> extends Item<T, _E> {

	void resolve(_E eval, int order, int nbOperands) throws ManagedException;
	
	boolean canManage(Operand<T, _E> oprd, int order);
	
	boolean canManage(_E eval, int order, int nbOperands) throws ManagedException;

	int nbOperand();

	Integer priority();

	String symbol();

	//boolean isPostUnary();

	//boolean isPreUnary();

	boolean operandsAreCumulable();

	//boolean isNotUnary();

}