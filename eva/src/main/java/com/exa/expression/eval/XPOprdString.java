package com.exa.expression.eval;

import com.exa.expression.TypeMan;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperandBase;

public abstract class XPOprdString  extends XPOperandBase<String> {

	
	@Override
	public TypeMan<?> type() {
		return TypeMan.STRING;
	}

	@Override
	public XPOperand<String> asOPString() {
		return this;
	}
	
	

}
