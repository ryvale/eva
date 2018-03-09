package com.exa.expression;

import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;


public class OMOpenParenthesis extends OM {
	private String symbol;
	private Integer priority;
	
	public OMOpenParenthesis(String symbol, Integer priority) {
		super();
		this.symbol = symbol;
		this.priority = priority;
	}

	@Override
	public String symbol() {
		return symbol;
	}

	@Override
	public Integer priority() {
		return priority;
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
		return OMType.PARAMS_START;
	}

	@Override
	public XPOperator<?> operatorOf(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
		return null;
	}

}
