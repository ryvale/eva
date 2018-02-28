package com.exa.eva;

public abstract class OperandBase<
	_T extends Item<_T, _OPRD, _OPRT, _E, _OM>,
	_OPRD extends Operand<_T, _OPRD, _OPRT, _E, _OM>,
	_OPRT extends Operator<_T, _OPRD, _OPRT, _E, _OM>,
	_E extends StackEvaluator<_T, _OPRD, _OPRT, _E, _OM>,
	_OM extends OperatorManager<_T, _OPRD, _OPRT, _E, _OM>> implements Operand<_T, _OPRD, _OPRT,  _E, _OM> {

	@Override
	public _OPRT asOperator() {
		return null;
	}

	@Override
	public boolean isConstant() {
		return false;
	}

		

}
