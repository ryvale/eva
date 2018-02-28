package com.exa.eva;

import com.exa.utils.ManagedException;

public interface StackEvaluator<
	_T extends Item<_T, _OPRD, _OPRT, _E, _OM>,
	_OPRD extends Operand<_T, _OPRD, _OPRT, _E, _OM>,
	_OPRT extends Operator<_T, _OPRD, _OPRT, _E, _OM>,
	_E extends StackEvaluator<_T, _OPRD, _OPRT, _E, _OM>,
	_OM extends OperatorManager<_T, _OPRD, _OPRT, _E, _OM>> {

	ComputedOperatorManager<_OM, _T> topOperatorManager();
	
	void push(_OM om) throws ManagedException;
	
	void push(_OPRD oprd);
	
	int numberOfOperands();
	
	ComputedItem<_T, _T, _OM> stackOperand(int indexFormTop);
	
	ComputedItem<_T, _T, _OM> popOperand();
	
	boolean popOperatorInOperandStack() throws ManagedException;
}
