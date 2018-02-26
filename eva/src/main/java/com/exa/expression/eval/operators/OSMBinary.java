package com.exa.expression.eval.operators;

public class OSMBinary extends OSMMutiple<XPCummulableBinaryOp<?>> {

	public OSMBinary(String symbol, int priority) {
		super(symbol, priority, 2);
	}


	@Override
	public OSMAssociativity associativity() {
		return OSMAssociativity.LEFT_TO_RIGHT;
	}

	@Override
	public OSMType type() {	return OSMType.REGULAR;	}


	@Override
	public OSMOperandType operandType() {
		return OSMOperandType.POST_OPERAND;
	}


	@Override
	public boolean canCumulateOperands() {
		return true;
	}
	
	

}
