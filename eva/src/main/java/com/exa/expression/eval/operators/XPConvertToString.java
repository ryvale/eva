package com.exa.expression.eval.operators;

import com.exa.expression.Type;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperandBase;
import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public class XPConvertToString extends XPOperandBase<String> {
	private XPOperand<?> oprd;
	
	public XPConvertToString(XPOperand<?> oprd) {
		super();
		this.oprd = oprd;
	}

	@Override
	public String value(XPEvaluator evaluator) throws ManagedException {
		return oprd.value(evaluator).toString();
	}

	@Override
	public Type<?> type() {
		return ClassesMan.T_STRING;
	}

}
