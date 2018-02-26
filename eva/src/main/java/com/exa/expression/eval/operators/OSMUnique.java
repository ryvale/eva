package com.exa.expression.eval.operators;

import com.exa.expression.XPOperator;
import com.exa.expression.eval.OperatorSymbMan;
import com.exa.expression.eval.XPEvaluator;

public class OSMUnique extends OperatorSymbMan {
	protected XPOperator<?> opertaor;
	
	private int priority;
	private int nbOperand;
	
	private OSMType type;
	private OSMAssociativity associativity;
	private OSMOperandType operandType;
	
	public OSMUnique(XPOperator<?> operator, int priority,  int nbOperand, OSMType type, OSMOperandType operandType, OSMAssociativity associativity) {
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
	public int nbOperand() {
		return nbOperand;
	}

	@Override
	public OSMAssociativity associativity() {
		return associativity;
	}

	@Override
	public OSMType type() {
		return type;
	}

	@Override
	public OSMOperandType operandType() {
		return operandType;
	}

}
