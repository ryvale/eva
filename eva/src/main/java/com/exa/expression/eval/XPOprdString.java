package com.exa.expression.eval;

import com.exa.expression.Type;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperandBase;

public abstract class XPOprdString  extends XPOperandBase<String> {

	
	@Override
	public Type<?> type() {
		return ClassesMan.T_STRING;
	}

	@Override
	public XPOperand<String> asOPString() {
		return this;
	}
	
	

}
