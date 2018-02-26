package com.exa.expression.eval.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.exa.eva.EvaException;
import com.exa.eva.Operand;
import com.exa.expression.TypeMan;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperator;
import com.exa.expression.XPression;
import com.exa.expression.eval.XPComputedItem;
import com.exa.expression.eval.XPComputedOSM;
import com.exa.expression.eval.XPEvaluator;
import com.exa.expression.eval.XPOprdString;

public class XPConcatenation extends XPCummulableBinaryOp<String> {
	
	class OperatorResult extends XPOprdString {
		
		List<XPOperand<String>> oprds = new ArrayList<>();
		
		public void addOperand(XPOperand<String> oprd) {
			oprds.add(oprd);
		}

		public void setOprds(List<XPOperand<String>> oprds) {
			this.oprds = oprds;
		}

		@Override
		public String value() throws EvaException {
			StringBuilder res = new StringBuilder();
			
			
			for(XPOperand<String> oprd : oprds) {
				res.append(oprd.value());
			}
			return res.toString();
		}
		
	}

	public XPConcatenation(int priority) {
		super("+", priority);
	}

	@Override
	public XPOperand<String> compute(Stack<XPOperand<?>> params) throws EvaException {
		OperatorResult res = new OperatorResult();
		
		int nb = params.size();
		for(int i=nb-1; i>=0; i--) {
			XPOperand<?> oprd = params.get(i);
			XPOperand<String> opStr = oprd.asOPString();
			if(opStr == null) opStr = new XPConvertToString(oprd);
			res.addOperand(opStr);
		}
		
		return res;
	}

	@Override
	public boolean canManage(XPEvaluator eval, int order, int nbOperands) {
		if(!super.canManage(eval, order, nbOperands)) return false;
		
		int operandIndex = 0;
		for(int i = 0; i<nbOperands; i++) {
			XPComputedItem<XPression<?>> coprd = eval.stackOperand(operandIndex);
			XPression<?> xp = coprd.item();
			XPOperator<?> oprt = xp.asOperator();
			
			if(oprt == null) {
				if(xp.asOperand().type() == TypeMan.STRING) return true;
				operandIndex++;
				continue;
			}
			
			XPComputedOSM coprt = coprd.asComputedOSM();
			TypeMan<?> type = oprt.getType(eval, coprd.order(), coprt.nbOperand());
			if(type == TypeMan.STRING) return true;
			operandIndex += coprt.nbOperand()+1;
			
		}
		
		/*XPComputedItem<XPression<?>> coprd = eval.stackOperand(0);
		
		XPression<?> xp = coprd.item();
		
		XPOperator<?> oprt = xp.asOperator();
		
		if(oprt == null) {
			if(xp.asOperand().type() == TypeMan.STRING) return true;
			return false;
		}
		
		TypeMan<?> type = oprt.getType(eval, coprd.order(), coprd.asComputedOperator().nbOperand());*/
		
		return false;
	}

	@Override
	public TypeMan<?> getType(XPEvaluator eval, int order, int nbOperands) {
		return TypeMan.STRING;
	}

	@Override
	public boolean canManage(Operand<XPression<?>, XPEvaluator> oprd, int order) {
		// TODO Auto-generated method stub
		return false;
	}

}
