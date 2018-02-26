package com.exa.expression.eval.operators;

import com.exa.expression.XPOperator;

public class OSMFunction extends OSMMutiple<XPOperator<?>> {

	public OSMFunction(String symbol, int nbOperand) {
		super(symbol, null, nbOperand);
	}

	@Override
	public OSMOperandType operandType() {
		return OSMOperandType.PRE_OPERAND;
	}

	@Override
	public OSMAssociativity associativity() {
		return OSMAssociativity.LEFT_TO_RIGHT;
	}

	@Override
	public OSMType type() {
		return OSMType.FUNCTION;
	}

}
