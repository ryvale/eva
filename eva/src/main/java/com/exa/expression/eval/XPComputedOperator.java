package com.exa.expression.eval;

import com.exa.expression.XPOperator;
import com.exa.expression.XPression;

public class XPComputedOperator extends XPComputedItem<XPression<?>> {
	protected int nbOperands;
	
	public XPComputedOperator(XPOperator<?> item, int order, int nbOperands) {
		super(item, order);
		this.nbOperands = nbOperands;
	}

	@Override
	public XPComputedOperator asComputedOperator() {
		return this;
	}

	public int nbOperands() { return nbOperands; }
	
	
	
}
