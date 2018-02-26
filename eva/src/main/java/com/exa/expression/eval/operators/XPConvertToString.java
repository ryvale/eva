package com.exa.expression.eval.operators;

import com.exa.eva.EvaException;
import com.exa.expression.TypeMan;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperandBase;

public class XPConvertToString extends XPOperandBase<String> {
	private XPOperand<?> oprd;
	
	public XPConvertToString(XPOperand<?> oprd) {
		super();
		this.oprd = oprd;
	}

	@Override
	public String value() throws EvaException {
		
		return oprd.value().toString();
	}

	@Override
	public TypeMan<?> type() {
		return TypeMan.STRING;
	}

}
