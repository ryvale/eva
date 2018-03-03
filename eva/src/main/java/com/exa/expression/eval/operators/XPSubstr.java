package com.exa.expression.eval.operators;

import com.exa.expression.XPOperand;
import com.exa.expression.eval.XPEvaluator;
import com.exa.expression.eval.XPOprdString;
import com.exa.utils.ManagedException;

public class XPSubstr extends XPOprdString {
	
	protected XPOperand<String> oprdStr;
	protected XPOperand<Integer> oprdStart;
	protected XPOperand<Integer> oprdNb;
	
	public XPSubstr(XPOperand<String> oprdStr, XPOperand<Integer> oprdStart, XPOperand<Integer> oprdNb) {
		super();
		this.oprdStr = oprdStr;
		this.oprdStart = oprdStart;
		this.oprdNb = oprdNb;
	}

	@Override
	public String value(XPEvaluator evaluator) throws ManagedException {
		String str = oprdStr.value(evaluator);
		Integer start = oprdStart.value(evaluator);
		
		if(oprdNb == null) return str.substring(start);
		
		Integer nb = oprdNb.value(evaluator);
		
		return str.substring(start, start+nb);
	}

	
	

}
