package com.exa.expression.eval.operators;

import com.exa.expression.OMBinary;

public class OMCumulableBinary extends OMBinary<XPOprtCummulableBinary<?>> {

	public OMCumulableBinary(String symbol, int priority) {
		super(symbol, priority);
	}

	@Override
	public boolean canCumulateOperands() {
		return true;
	}

}
