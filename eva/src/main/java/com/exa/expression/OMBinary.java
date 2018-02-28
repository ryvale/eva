package com.exa.expression;

import com.exa.expression.eval.operators.XPOprtCummulableBinary;

public class OMBinary extends OMMutiple<XPOprtCummulableBinary<?>> {

	public OMBinary(String symbol, int priority) {
		super(symbol, priority, 2);
	}


	@Override
	public OMAssociativity associativity() {
		return OMAssociativity.LEFT_TO_RIGHT;
	}

	@Override
	public OMType type() {	return OMType.REGULAR;	}


	@Override
	public OMOperandType operandType() {
		return OMOperandType.POST_OPERAND;
	}


	@Override
	public boolean canCumulateOperands() {
		return true;
	}
	
	

}
