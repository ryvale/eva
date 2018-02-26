package com.exa.eva;

public class ComputedOperator<T extends Item<T, _E>, _E extends StackEvaluator<T>> extends ComputedItem<T, _E> {
	protected int nbOperand;
	protected int expectedNbOperand;

	public ComputedOperator(Operator<T, _E> item, int order, int nbOperand) {
		super(item.asSpecificItem(), order);
		this.nbOperand = nbOperand;
		expectedNbOperand = item.nbOperand();
	}
	
	public int incOperandNumber() { return ++nbOperand; }
	public int incExpectedOperandNumber() { return ++expectedNbOperand; }
	
	public int nbOperand() { return nbOperand;	}

	@Override
	public ComputedOperator<T, _E> asComputedOperator() { return this; }
	
	
	public boolean expectOperand() { return nbOperand < expectedNbOperand;}
	
	public boolean canManageNextOperand(Operand<T, _E> oprd) {
		return item.asOperator().canManage(oprd, nbOperand+1);
	}
	
	
}
