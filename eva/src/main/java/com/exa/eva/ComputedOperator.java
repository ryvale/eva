package com.exa.eva;

public class ComputedOperator<_I extends Item<_I, ?, ?, ?, _OM>, _OM extends OperatorManager<_I, ?, ?, ?, _OM>> extends ComputedItem<_I, _I, _OM> {
	protected int nbOperands;
	protected int expectedNbOperand;

	public ComputedOperator(_I item, int order, int nbOperand) {
		super(item, order);
		this.nbOperands = nbOperand;
		expectedNbOperand = item.asOperator().nbOperands();
	}
	
	public int incExpectedOperandNumber() { return ++expectedNbOperand; }
	
	public int nbOperands() { return nbOperands;	}

	@Override
	public ComputedOperator<_I, _OM> asComputedOperator() {
		return this;
	}
	
	
}
