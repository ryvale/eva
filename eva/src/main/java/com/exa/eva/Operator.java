package com.exa.eva;

import com.exa.utils.ManagedException;

public interface Operator<
	_T extends Item<_T, _OPRD, _OPRT, _E, _OM>,
	_OPRD extends Operand<_T, _OPRD, _OPRT, _E, _OM>,
	_OPRT extends Operator<_T, _OPRD, _OPRT, _E, _OM>,
	_E extends StackEvaluator<_T, _OPRD, _OPRT, _E, _OM>,
	_OM extends OperatorManager<_T, _OPRD, _OPRT, _E, _OM>> extends Item<_T,_OPRD, _OPRT, _E, _OM> {

	void resolve(_E eval, int order, int nbOperands) throws ManagedException;
	
	boolean canManage(_E eval, int order, int nbOperands) throws ManagedException;

	int nbOperands();

	//Integer priority();

	String symbol();

	//boolean operandsAreCumulable();

	//boolean isNotUnary();

}