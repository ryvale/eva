package com.exa.eva;

import com.exa.utils.ManagedException;

public abstract interface OperatorManager<
	_T extends Item<_T, _OPRD, _OPRT, _E, _OM>,
	_OPRD extends Operand<_T, _OPRD, _OPRT, _E, _OM>,
	_OPRT extends Operator<_T, _OPRD, _OPRT, _E, _OM>,
	_E extends StackEvaluator<_T, _OPRD, _OPRT, _E, _OM>,
	_OM extends OperatorManager<_T, _OPRD, _OPRT, _E, _OM>> {
		
	public static enum OMType { REGULAR, PARAMS_START, PARAMS_END, FUNCTION, PARAMS_SEPARATOR, REGULAR_OPERAND_START, OPERAND_SEPARATOR}
	
	public static enum OMAssociativity { LEFT_TO_RIGHT, RIGHT_TO_LEFT, NONE}
	
	public static enum OMOperandType { PRE_OPERAND, POST_OPERAND, OTHER }
	
	public abstract String symbol();
	
	public abstract _OPRT operatorOf(_E eval, int order, int nbOperands) throws ManagedException;
	
	public abstract Integer priority();
	
	public abstract int nbOperands();
	
	public boolean canCumulateOperands(); // { return false; }
	
	public abstract OMOperandType operandType();
	
	public abstract OMAssociativity associativity();
	
	public abstract OMType type();
	
}
