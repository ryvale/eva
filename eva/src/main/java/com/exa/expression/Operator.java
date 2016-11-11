package com.exa.expression;

public interface Operator<T extends XPItem<T>> extends XPItem<T> {

	void resolve(StackEvaluator<T> eval, int order, int nbOperands) throws XPressionException;

	int nbOperand();

	Integer priority();

	String symbol();

	boolean isPostUnary();

	boolean isPreUnary();

	boolean operandsAreCumulable();

	boolean isNotUnary();

}