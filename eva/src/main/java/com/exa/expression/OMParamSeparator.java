package com.exa.expression;

import com.exa.expression.eval.XPEvaluator;

public class OMParamSeparator extends OM {

	private String symbol;
	
	public OMParamSeparator(String symbol) {
		super();
		this.symbol = symbol;
	}

	@Override
	public String symbol() {
		return symbol;
	}

	@Override
	public XPOperator<?> operatorOf(XPEvaluator eval, int order, int nbOperands) {
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
		return OMType.PARAMS_SEPARATOR;
	}

}
