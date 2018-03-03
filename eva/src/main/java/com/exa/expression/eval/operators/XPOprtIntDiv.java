package com.exa.expression.eval.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.exa.eva.ComputedItem;
import com.exa.eva.ComputedOperator;
import com.exa.expression.OM;
import com.exa.expression.TypeMan;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperandBase;
import com.exa.expression.XPOperator;
import com.exa.expression.XPression;
import com.exa.expression.eval.XPEvaluator;

import com.exa.utils.ManagedException;

public class XPOprtIntDiv extends XPOprtCummulableBinary<Integer>  {
	
	class ResultOperand extends XPOperandBase<Integer> {
		
		List<XPOperand<Integer>> oprds = new ArrayList<>();

		@Override
		public Integer value(XPEvaluator evaluator) throws ManagedException {
			Integer res = oprds.get(0).value(evaluator);
			
			if(res == null) throw new ManagedException(String.format("The oprator %s could not accept null operand", symbol));
			
			for(int i=1; i<oprds.size(); i++) {
				Integer v = oprds.get(i).value(evaluator);
				if(v == null) throw new ManagedException(String.format("The oprator %s could not accept null operand", symbol));
				
				res /= v;
			}

			return res;
		}

		@Override
		public TypeMan<?> type() {
			return TypeMan.INTEGER;
		}

		@Override
		public XPOperand<Integer> asOPInteger() {
			return this;
		}
		
		
		public void addOperand(XPOperand<Integer> oprd) {
			oprds.add(oprd);
		}
	}


	public XPOprtIntDiv(String symbol) {
		super(symbol);
	}

	@Override
	public TypeMan<?> type() {
		return TypeMan.INTEGER;
	}

	@Override
	public boolean canManage(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
		if(!super.canManage(eval, order, nbOperands)) return false;
		
		int operandIndex = 0;
		for(int i = 0; i<nbOperands; i++) {
			ComputedItem<XPression<?>, XPression<?>, OM> coprd = eval.stackOperand(operandIndex);
			XPression<?> xp = coprd.item();
			XPOperator<?> oprt = xp.asOperator();
			
			if(oprt == null) {
				if(xp.asOperand().type() != TypeMan.INTEGER) return false;
				operandIndex++;
				continue;
			}
			
			ComputedOperator<XPression<?>, OM> coprt = coprd.asComputedOperator();
			
			TypeMan<?> type = oprt.type();
			if(type != TypeMan.INTEGER) return false;
			operandIndex += coprt.nbOperands()+1;
			
		}
				
		return true;
	}

	@Override
	public XPOperand<Integer> createResultOperand(Vector<XPOperand<?>> params) throws ManagedException {
		ResultOperand res = new ResultOperand();
		
		int nb = params.size();
		for(int i=0; i<nb; i++) {
			XPOperand<?> oprd = params.get(i);
			XPOperand<Integer> opSpecific = oprd.asOPInteger();
			if(opSpecific == null) throw new ManagedException(String.format("The % should operate only on integer.", symbol));
			res.addOperand(opSpecific);
		}
		
		return res;
	}

}
