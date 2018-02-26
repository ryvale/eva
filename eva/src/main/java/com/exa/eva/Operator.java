package com.exa.eva;

public interface Operator<T extends Item<T, _E>, _E extends StackEvaluator<T>> extends Item<T, _E> {

	void resolve(_E eval, int order, int nbOperands) throws EvaException;
	
	boolean canManage(Operand<T, _E> oprd, int order);
	
	boolean canManage(_E eval, int order, int nbOperands);

	int nbOperand();

	Integer priority();

	String symbol();

	//boolean isPostUnary();

	//boolean isPreUnary();

	boolean operandsAreCumulable();

	//boolean isNotUnary();

}