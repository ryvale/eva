package com.exa.expression.eval.operators;

import java.util.Stack;
import com.exa.eva.EvaException;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperatorBase;
import com.exa.expression.XPression;
import com.exa.expression.eval.XPComputedItem;
import com.exa.expression.eval.XPEvaluator;

public abstract class XPCummulableBinaryOp<T> extends XPOperatorBase<T> {

	public XPCummulableBinaryOp(String symbol, int priority) {
		super(symbol, priority, 2);
	}

	@Override
	public void resolve(XPEvaluator eval, int order, int nbOperands) throws EvaException {
		if(eval.numberOfOperands() < nbOperands) throw new EvaException(String.format("Error in the expression near %s . The number of argument expected %s is is lower than the availabe %s", symbol, nbOperands, eval.numberOfOperands()));
		
		Stack<XPOperand<?>> params = new Stack<>();
		
		for(int i=0; i < nbOperands; i++) {
			XPComputedItem<XPression<?>> ci = eval.popOutput();
			XPression<?> item = ci.item();
			XPOperand<?> oprd = item.asOperand();
			if(oprd == null) {
				item.asOperator().resolve(eval, ci.order(), ci.asComputedOperator().nbOperands());
				oprd = eval.popOutput().item().asOperand();
			}
			
			params.push(oprd);
		}
		
				
		XPOperand<T> res = compute(params);
		
		eval.push(res);
	}
	
	public abstract XPOperand<T> compute(Stack<XPOperand<?>> params) throws EvaException;
	
	

	@Override
	public boolean canManage(XPEvaluator eval, int order, int nbOperands) {
		if(eval.numberOfOperands() < nbOperands) return false;
		
		return true;
	}

	/*@Override
	public boolean isPostUnary() {
		return false;
	}

	@Override
	public boolean isPreUnary() {
		return false;
	}*/

}
