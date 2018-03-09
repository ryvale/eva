package com.exa.expression.eval.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.exa.eva.ComputedItem;
import com.exa.eva.EvaException;
import com.exa.expression.OM;
import com.exa.expression.Type;
import com.exa.expression.XPOperand;
import com.exa.expression.XPression;
import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.XPEvaluator;
import com.exa.expression.eval.XPOprdString;
import com.exa.utils.ManagedException;

public class XPOprtConcatenation extends XPOprtCummulableBinary<String> {
	
	class XPResult extends XPOprdString {
		
		List<XPOperand<String>> oprds = new ArrayList<>();

		public void addOperand(XPOperand<String> oprd) {
			oprds.add(oprd);
		}

		@Override
		public String value(XPEvaluator evaluator) throws ManagedException {
			StringBuilder res = new StringBuilder();
			
			
			for(XPOperand<String> oprd : oprds) {
				res.append(oprd.value(evaluator));
			}
			return res.toString();
		}
		
	}

	public XPOprtConcatenation(String symbol) {
		super(symbol);
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
			ComputedItem<XPression<?>, XPression<?>, OM> coprd = eval.stackOperand(operandIndex);
			XPression<?> xp = coprd.item();
			
			if(xp.type() == ClassesMan.T_STRING) return true;
			
			operandIndex = OM.nextOperand(eval, operandIndex);
			
		}
		
		return false;
	}
	
	@Override
	public Type<?> type() {
		return ClassesMan.T_STRING;
	}

}
