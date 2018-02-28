package com.exa.expression;

import com.exa.expression.eval.XPEvaluator;

public class OMClosedParenthesis extends OM {

	private String symbol;
	
	public OMClosedParenthesis(String symbol) {
		super();
		this.symbol = symbol;
	}

	@Override
	public String symbol() {
		return symbol;
	}

	@Override
	public XPOperator<?> operatorOf(XPEvaluator eval, int order, int nbOperands) {
		// TODO Auto-generated method stub
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
		return OMAssociativity.LEFT_TO_RIGHT;
	}

	@Override
	public OMType type() {
		return OMType.CLOSED_PARENTHESIS;
	}

}
