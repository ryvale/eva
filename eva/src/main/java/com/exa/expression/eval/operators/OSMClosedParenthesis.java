package com.exa.expression.eval.operators;

import com.exa.expression.XPOperator;
import com.exa.expression.eval.OperatorSymbMan;
import com.exa.expression.eval.XPEvaluator;

public class OSMClosedParenthesis extends OperatorSymbMan {

	private String symbol;
	
	public OSMClosedParenthesis(String symbol) {
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
	public int nbOperand() {
		return 0;
	}

	@Override
	public OSMOperandType operandType() {
		return null;
	}

	@Override
	public OSMAssociativity associativity() {
		return OSMAssociativity.LEFT_TO_RIGHT;
	}

	@Override
	public OSMType type() {
		return OSMType.CLOSED_PARENTHESIS;
	}

}
