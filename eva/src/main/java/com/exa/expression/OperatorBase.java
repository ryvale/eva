package com.exa.expression;

public abstract class OperatorBase<T extends XPItem<T>> implements Operator<T> {
	protected Integer priority;
	protected int nbOperands;

	protected String symbol;
	
	public OperatorBase(String symbol, int priority, int nbOperands) {
		this.priority = priority;
		this.symbol = symbol;
		this.nbOperands = nbOperands;
	}
	
	@Override
	public abstract void resolve(StackEvaluator<T> eval, int order, int nbOperands) throws XPressionException;
	
	@Override
	public int nbOperand() { return nbOperands; }
	
	@Override
	public Integer priority() { return priority; }
	
	@Override
	public String symbol() { return symbol; }
	
	@Override
	public abstract boolean isPostUnary();
	
	@Override
	public abstract boolean isPreUnary();
	
	@Override
	public boolean operandsAreCumulable() { return false; }
	
	@Override
	public boolean isNotUnary() { return !isPostUnary() && !isPreUnary(); }

	@Override
	public Operand<T> asOperand() { return null; }
	
}
