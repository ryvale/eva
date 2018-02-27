package com.exa.expression.eval.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.exa.eva.EvaException;
import com.exa.eva.Operand;
import com.exa.expression.TypeMan;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperator;
import com.exa.expression.XPression;
import com.exa.expression.eval.XPComputedItem;
import com.exa.expression.eval.XPComputedOperator;
import com.exa.expression.eval.XPEvaluator;
import com.exa.expression.eval.XPOprdString;
import com.exa.utils.ManagedException;

public class XPOprtConcatenation extends XPOprtCummulableBinary<String> {
	
	class XPResult extends XPOprdString {
		
		List<XPOperand<String>> oprds = new ArrayList<>();

		public void addOperand(XPOperand<String> oprd) {
			oprds.add(oprd);
		}

		/*
		public void setOprds(List<XPOperand<String>> oprds) {
			this.oprds = oprds;
		}*/

		@Override
		public String value() throws ManagedException {
			StringBuilder res = new StringBuilder();
			
			
			for(XPOperand<String> oprd : oprds) {
				res.append(oprd.value());
			}
			return res.toString();
		}
		
	}

	public XPOprtConcatenation(int priority) {
		super("+", priority);
	}

	@Override
	public XPOperand<String> createResultOperand(Vector<XPOperand<?>> params) throws EvaException {
		XPResult res = new XPResult();
		
		int nb = params.size();
		for(int i=0; i<nb; i++) {
			XPOperand<?> oprd = params.get(i);
			XPOperand<String> opStr = oprd.asOPString();
			if(opStr == null) opStr = new XPConvertToString(oprd);
			res.addOperand(opStr);
		}
		
		return res;
	}

	@Override
	public boolean canManage(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
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
			
			XPComputedOperator coprt = coprd.asComputedOperator();
			//TypeMan<?> type = oprt.getType(eval, coprd.order(), coprt.nbOperands());
			TypeMan<?> type = oprt.type();
			if(type == TypeMan.STRING) return true;
			operandIndex += coprt.nbOperands()+1;
			
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

	/*@Override
	public TypeMan<?> getType(XPEvaluator eval, int order, int nbOperands) {
		return TypeMan.STRING;
	}*/
	
	@Override
	public TypeMan<?> type() {
		return TypeMan.STRING;
	}

	@Override
	public boolean canManage(Operand<XPression<?>, XPEvaluator> oprd, int order) {
		
		return true;
	}

}
