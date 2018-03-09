package com.exa.expression;

import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public class OMOperandSeparator extends OM {
	private String symbol;
	
	public OMOperandSeparator(String symbol) {
		super();
		this.symbol = symbol;
	}

	@Override
	public String symbol() {
		return symbol;
	}

	@Override
	public XPOperator<?> operatorOf(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
		return null;
	}

	@Override
	public Integer priority() {
		return null;
	}

	@Override
	public int nbOperands() {
		return 0;
	}

	@Override
	public OMOperandType operandType() {
		return null;
	}

	@Override
	public OMAssociativity associativity() {
		return null;
	}

	@Override
	public OMType type() {
		return OMType.OPERAND_SEPARATOR;
	}

}
