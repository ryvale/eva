package com.exa.expression;


public abstract class OMBinary<T extends XPOperator<?>> extends OMMutiple<T> {

	public OMBinary(String symbol, Integer priority) {
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

}