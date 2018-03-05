package com.exa.expression.eval.functions;

import com.exa.eva.EvaException;
import com.exa.expression.Type;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperatorBase;
import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.XPEvaluator;
import com.exa.expression.eval.operators.XPSubstr;
import com.exa.utils.ManagedException;

public class XPFunctSubstr extends XPOperatorBase<String> {

	public XPFunctSubstr(String symbol, int nbOperands) {
		super(symbol, nbOperands);
	}
	
	@Override
	public Type<?> type() {
		return ClassesMan.T_STRING;
	}

	@Override
	public void resolve(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
		if(eval.numberOfOperands() < nbOperands) throw new EvaException(String.format("Error in the expression near %s . The number of argument expected %s is lower than the availabe %s", symbol, nbOperands, eval.numberOfOperands()));
		
		if(nbOperands > 2) {
			XPOperand<?> oprd = resolveOperand(eval);
			XPOperand<Integer> opNb = oprd.asOPInteger();
			if(opNb == null) throw new EvaException(String.format("Error in the expression near %s . The third argument should be integer", symbol));
			
			oprd = resolveOperand(eval);
			
			XPOperand<Integer> opStart = oprd.asOPInteger();
			if(opStart == null) throw new EvaException(String.format("Error in the expression near %s . The second argument should be integer", symbol));
			
			oprd = resolveOperand(eval);
			XPOperand<String> opStr = oprd.asOPString();
			if(opStr == null) throw new EvaException(String.format("Error in the expression near %s . The first argument should be string", symbol));
			
			eval.push(new XPSubstr(opStr, opStart, opNb));
			return;
		}
		
		XPOperand<?> oprd = resolveOperand(eval);
		XPOperand<Integer> opStart = oprd.asOPInteger();
		if(opStart == null) throw new EvaException(String.format("Error in the expression near %s . The second argument should be integer", symbol));

		oprd = resolveOperand(eval);
		XPOperand<String> opStr = oprd.asOPString();
		if(opStr == null) throw new EvaException(String.format("Error in the expression near %s . The first argument should be string", symbol));
		
		eval.push(new XPSubstr(opStr, opStart, null));
	}

	@Override
	public boolean canManage(XPEvaluator eval, int order, int nbOperands) {
		return true;
	}

}
