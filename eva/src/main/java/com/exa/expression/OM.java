package com.exa.expression;

import com.exa.eva.ComputedItem;
import com.exa.eva.OperatorManager;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public abstract class OM implements OperatorManager<XPression<?>, XPOperand<?>, XPOperator<?>, XPEvaluator, OM> {
	public interface OperandAction {
		boolean doAction(XPression<?> xp) throws ManagedException;
	}
	
	protected int operandAction(XPEvaluator eval, int nb, OperandAction oa) throws ManagedException {
		ComputedItem<XPression<?>, XPression<?>, ?> ci = eval.stackOperand(nb);
		
		XPression<?> xp = ci.item();
		
		if(!oa.doAction(xp)) return -1;
		
		XPOperator<?> oprt = xp.asOperator();
		if(oprt == null) return nb + 1;
		
		return nb+oprt.nbOperands();
	}
	
	@Override
	public boolean canCumulateOperands() {
		return false;
	}
	
	
	public OMMutiple<?> asOMM() {return null; }
	

}
