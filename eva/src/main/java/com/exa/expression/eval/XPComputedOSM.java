package com.exa.expression.eval;

public class XPComputedOSM extends XPComputedItem<OperatorSymbMan> {
	protected int nbOperand;
	protected int expectedNbOperand;
	
	//private OperatorSymbMan osm;

	public XPComputedOSM(OperatorSymbMan osm, int order, int nbOperand) {
		super(osm, order);
		this.nbOperand = nbOperand;
		
		expectedNbOperand = osm.nbOperand();
	}
	
	public int incOperandNumber() { return ++nbOperand; }
	public int incExpectedOperandNumber() { return ++expectedNbOperand; }
	
	public int nbOperand() { return nbOperand;	}

	public boolean expectOperand() { return nbOperand < expectedNbOperand;}

	@Override
	public XPComputedOSM asComputedOSM() {
		return this;
	}
	
	
	
	
}
