package com.exa.expression.eval.operators;

import com.exa.expression.TypeMan;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperandBase;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public class XPConvertToDouble extends XPOperandBase<Double>{
	private XPOperand<? extends Number> oprd;
	
	public XPConvertToDouble(XPOperand<? extends Number> oprd) {
		super();
		this.oprd = oprd;
	}

	@Override
	public Double value(XPEvaluator evaluator) throws ManagedException {
		Number v = oprd.value(evaluator);
		if(v == null) return null;
		return v.doubleValue();
	}

	@Override
	public TypeMan<?> type() {
		return TypeMan.DOUBLE;
	}

	@Override
	public XPOperand<Double> asOPDouble() {
		return this;
	}
	
	

}
