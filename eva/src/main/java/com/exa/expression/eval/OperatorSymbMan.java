package com.exa.expression.eval;

import com.exa.expression.XPOperator;

public abstract class OperatorSymbMan {
	public static enum OSMType { REGULAR, OPEN_PARENTHESIS, CLOSED_PARENTHESIS, FUNCTION, PARAMS_SEPARATOR}
	
	public static enum OSMAssociativity { LEFT_TO_RIGHT, RIGHT_TO_LEFT, NONE}
	
	public static enum OSMOperandType { PRE_OPERAND, POST_OPERAND, OTHER }
	
	public abstract String symbol();
	
	public abstract XPOperator<?> operatorOf(XPEvaluator eval, int order, int nbOperands);
	
	public abstract Integer priority();
	
	public abstract int nbOperand();
	
	public boolean canCumulateOperands() { return false; }
	
	public abstract OSMOperandType operandType();
	
	public abstract OSMAssociativity associativity();
	
	public abstract OSMType type();
	
}
