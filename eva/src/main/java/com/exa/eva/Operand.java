package com.exa.eva;

public interface Operand<
	_T extends Item<_T, _OPRD, _OPRT, _E, _OM>,
	_OPRD extends Operand<_T, _OPRD, _OPRT, _E, _OM>,
	_OPRT extends Operator<_T, _OPRD, _OPRT, _E, _OM>,
	_E extends StackEvaluator<_T, _OPRD, _OPRT, _E, _OM>,
	_OM extends OperatorManager<_T, _OPRD, _OPRT, _E, _OM>> extends Item<_T,_OPRD, _OPRT, _E, _OM> {

	boolean isConstant();

}