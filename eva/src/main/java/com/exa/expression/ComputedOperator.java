package com.exa.expression;

public class ComputedOperator<T extends XPItem<T>> extends ComputedItem<T> {
	protected int nbOperand;
	protected int expectedNbOperand;

	public ComputedOperator(Operator<T> item, int order, int nbOperand) {
		super(item.asSpecificItem(), order);
		this.nbOperand = nbOperand;
		expectedNbOperand = item.nbOperand();
	}
	
	public int incOperandNumber() { return ++nbOperand; }
	public int incExpectedOperandNumber() { return ++expectedNbOperand; }
	
	public int nbOperand() { return nbOperand;	}

	@Override
	public ComputedOperator<T> asComputedOperator() { return this; }
	
	
	public boolean expectOperand() { return nbOperand < expectedNbOperand;}
	
	public boolean canManageNextOperand(Operand<T> oprd) {
		return item.asOperator().canManage(oprd, nbOperand+1);
	}
	
	
}
