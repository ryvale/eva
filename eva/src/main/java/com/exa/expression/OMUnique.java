package com.exa.expression;

import com.exa.expression.eval.XPEvaluator;

public class OMUnique extends OM {
	protected XPOperator<?> opertaor;
	
	private int priority;
	private int nbOperand;
	
	private OMType type;
	private OMAssociativity associativity;
	private OMOperandType operandType;
	
	public OMUnique(XPOperator<?> operator, int priority,  int nbOperand, OMType type, OMOperandType operandType, OMAssociativity associativity) {
		super();
		this.opertaor = operator;
		this.priority = priority;
		this.nbOperand = nbOperand;
		this.type = type;
		this.associativity = associativity;
		this.operandType = operandType;
	}

	@Override
	public String symbol() {
		return opertaor.symbol();
	}

	@Override
	public XPOperator<?> operatorOf(XPEvaluator eval, int order, int nbOPerands) {
		return opertaor;
	}

	@Override
	public Integer priority() {	return priority; }

	@Override
	public int nbOperands() {
		return nbOperand;
	}

	@Override
	public OMAssociativity associativity() {
		return associativity;
	}

	@Override
	public OMType type() {
		return type;
	}

	@Override
	public OMOperandType operandType() {
		return operandType;
	}

}
