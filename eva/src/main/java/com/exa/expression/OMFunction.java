package com.exa.expression;

public class OMFunction<T extends XPOperator<?>> extends OMMutiple<T> {

	public OMFunction(String symbol, int nbOperand) {
		super(symbol, null, nbOperand);
	}

	@Override
	public OMOperandType operandType() {
		return OMOperandType.PRE_OPERAND;
	}

	@Override
	public OMAssociativity associativity() {
		return OMAssociativity.LEFT_TO_RIGHT;
	}

	@Override
	public OMType type() {
		return OMType.FUNCTION;
	}

}
