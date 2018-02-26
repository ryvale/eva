package com.exa.eva;

public abstract class OperatorBase<T extends Item<T, _E>, _E extends StackEvaluator<T>> implements Operator<T, _E> {
	protected Integer priority;
	protected int nbOperands;

	protected String symbol;
	
	public OperatorBase(String symbol, Integer priority, int nbOperands) {
		this.priority = priority;
		this.symbol = symbol;
		this.nbOperands = nbOperands;
	}
	
	/*@Override
	public abstract void resolve(StackEvaluator<T> eval, int order, int nbOperands) throws EvaException;*/
	
	@Override
	public int nbOperand() { return nbOperands; }
	
	@Override
	public Integer priority() { return priority; }
	
	@Override
	public String symbol() { return symbol; }
	
	/*@Override
	public abstract boolean isPostUnary();
	
	@Override
	public abstract boolean isPreUnary();*/
	
	@Override
	public boolean operandsAreCumulable() { return false; }
	
	/*@Override
	public boolean isNotUnary() { return !isPostUnary() && !isPreUnary(); }*/

	@Override
	public Operand<T, _E> asOperand() { return null; }

	/*@Override
	public boolean canManage(Operand<T, _E> oprd, int order) {	return true; }*/
	
	
	
}
