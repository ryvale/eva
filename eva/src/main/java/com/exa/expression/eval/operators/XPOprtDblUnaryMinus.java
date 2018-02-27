package com.exa.expression.eval.operators;

import com.exa.eva.Operand;
import com.exa.expression.TypeMan;
import com.exa.expression.XPression;
import com.exa.expression.eval.XPEvaluator;

public class XPOprtDblUnaryMinus extends XPOprtUnary<Double> {

	public XPOprtDblUnaryMinus(int priority) {
		super("-", priority);
	}

	/*@Override
	public boolean isPostUnary() {
		return false;
	}

	@Override
	public boolean isPreUnary() {
		return true;
	}*/

	/*@Override
	public TypeMan<?> getType(XPEvaluator eval, int order, int nbOperands) {
		return TypeMan.DOUBLE;
	}*/
	
	@Override
	public TypeMan<?> type() {
		return TypeMan.DOUBLE;
	}

	@Override
	public boolean canManage(Operand<XPression<?>, XPEvaluator> oprd, int order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canManage(XPEvaluator eval, int order, int nbOperands) {
		
		return false;
	}

}
