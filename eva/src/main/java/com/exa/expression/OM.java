package com.exa.expression;

import com.exa.eva.ComputedItem;
import com.exa.eva.ComputedOperator;
import com.exa.eva.OperatorManager;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public abstract class OM implements OperatorManager<XPression<?>, XPOperand<?>, XPOperator<?>, XPEvaluator, OM> {
	public interface OperandAction {
		boolean doAction(XPression<?> xp) throws ManagedException;
	}
	
	public static int nextOperand(XPEvaluator eval, int nb) {
		ComputedItem<XPression<?>, XPression<?>, ?> ci = eval.stackOperand(nb);
		
		nb++;
		XPOperator<?> oprt = ci.item().asOperator();
		if(oprt == null) return nb;
		
		ComputedOperator<XPression<?>, ?> cop = ci.asComputedOperator();
		for(int i=0;i<cop.nbOperands(); i++) {
			nb = nextOperand(eval, nb);
		}
		
		return nb;
		
	}
	
	public static int operandAction(XPEvaluator eval, int nb, OperandAction oa) throws ManagedException {
		ComputedItem<XPression<?>, XPression<?>, ?> ci = eval.stackOperand(nb);
		
		XPression<?> xp = ci.item();
		
		if(!oa.doAction(xp)) return -1;
		
		nb++;
		XPOperator<?> oprt = xp.asOperator();
		if(oprt == null) return nb;
		
		
		/*for(int i=0; i <oprt.nbOperands(); i++) {
			nb = nextOperand(eval, nb);
		}*/
		ComputedOperator<XPression<?>, ?> cop = ci.asComputedOperator();
		for(int i=0; i <cop.nbOperands(); i++) {
			nb = nextOperand(eval, nb);
		}
		
		return nb;
	}
	
	@Override
	public boolean canCumulateOperands() {
		
		return false;
	}
	
	
	public OMMutiple<?> asOMM() {return null; }
	

}
