package com.exa.eva;

public class ComputedOperatorManager<_OM extends OperatorManager<_OPRT, ?, ?, ?, _OM>, _OPRT extends Item<_OPRT, ?, ?, ?, _OM>> extends ComputedItem<_OM, _OPRT, _OM> {
	protected int nbOperands;
	protected int expectedNbOperands;
	
	public ComputedOperatorManager(_OM item, int order, int nbOperands) {
		super(item, order);
		
		this.nbOperands = nbOperands;
		this.expectedNbOperands = item.nbOperands();
	}
	
	public int incExpectedOperandNumber() { return ++expectedNbOperands; }
	
	public int nbOperands() { return nbOperands;	}

	@Override
	public ComputedOperatorManager<_OM, _OPRT> asComputedOperatorManager() {
		return this;
	}

	public int nbExpectedNbOperands() {	return expectedNbOperands; }
	
	

	

}
