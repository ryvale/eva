package com.exa.eva;

public abstract class OperatorBase<
	_T extends Item<_T, _OPRD, _OPRT, _E, _OM>,
	_OPRD extends Operand<_T, _OPRD, _OPRT, _E, _OM>,
	_OPRT extends Operator<_T, _OPRD, _OPRT, _E, _OM>,
	_E extends StackEvaluator<_T, _OPRD, _OPRT, _E, _OM>,
	_OM extends OperatorManager<_T, _OPRD, _OPRT, _E, _OM>> implements Operator<_T,_OPRD, _OPRT, _E, _OM> {
	
	protected int nbOperands;

	protected String symbol;
	
	public OperatorBase(String symbol, int nbOperands) {
		this.symbol = symbol;
		this.nbOperands = nbOperands;
	}
	
	@Override
	public int nbOperands() { return nbOperands; }
	
	/*@Override
	public Integer priority() { return priority; }*/
	
	@Override
	public String symbol() { return symbol; }
	
	/*@Override
	public abstract boolean isPostUnary();
	
	@Override
	public abstract boolean isPreUnary();*/
	
	/*@Override
	public boolean operandsAreCumulable() { return false; }*/
	
	/*@Override
	public boolean isNotUnary() { return !isPostUnary() && !isPreUnary(); }*/

	/*@Override
	public boolean canManage(Operand<T, _E> oprd, int order) {	return true; }*/
	
	
	
}
