package com.exa.expression.eval.operators;

import com.exa.expression.TypeMan;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperandBase;
import com.exa.utils.ManagedException;

public class XPConvertToString extends XPOperandBase<String> {
	private XPOperand<?> oprd;
	
	public XPConvertToString(XPOperand<?> oprd) {
		super();
		this.oprd = oprd;
	}

	@Override
	public String value() throws ManagedException {
		
		return oprd.value().toString();
	}

	@Override
	public TypeMan<?> type() {
		return TypeMan.STRING;
	}

}
