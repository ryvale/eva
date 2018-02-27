package com.exa.expression.eval.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.exa.expression.TypeMan;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperandBase;
import com.exa.expression.XPOperator;
import com.exa.expression.XPression;
import com.exa.expression.eval.XPComputedItem;
import com.exa.expression.eval.XPComputedOperator;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public class XPOprtDblAdd extends XPOprtCummulableBinary<Double> {
	class ResultOperand extends XPOperandBase<Double> {
		
		List<XPOperand<Double>> oprds = new ArrayList<>();

		@Override
		public Double value() throws ManagedException {
			Double res = oprds.get(0).value();
			
			if(res == null) throw new ManagedException(String.format("The oprator %s could not accept null operand", symbol));
			
			for(int i=1; i<oprds.size(); i++) {
				Double v = oprds.get(i).value();
				if(v == null) throw new ManagedException(String.format("The oprator %s could not accept null operand", symbol));
				
				res += v;
			}

			return res;
		}

		@Override
		public TypeMan<?> type() {
			return TypeMan.DOUBLE;
		}

		
		public void addOperand(XPOperand<Double> oprd) {
			oprds.add(oprd);
		}

		@Override
		public XPOperand<Double> asOPDouble() {
			return this;
		}
	}


	public XPOprtDblAdd(String symbol, int priority) {
		super(symbol, priority);
	}

	@Override
	public TypeMan<?> type() {
		return TypeMan.DOUBLE;
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
				if(xp.asOperand().type() != TypeMan.INTEGER && xp.asOperand().type() != TypeMan.DOUBLE) return false;
				operandIndex++;
				continue;
			}
			
			XPComputedOperator coprt = coprd.asComputedOperator();
			TypeMan<?> type = coprt.item().type();
			
			if(type != TypeMan.INTEGER && type != TypeMan.DOUBLE) return false;
			operandIndex += coprt.nbOperands()+1;
			
		}
				
		return true;
	}

	@Override
	public XPOperand<Double> createResultOperand(Vector<XPOperand<?>> params) throws ManagedException {
		ResultOperand res = new ResultOperand();
		
		int nb = params.size();
		for(int i=0; i<nb; i++) {
			XPOperand<?> oprd = params.get(i);
			XPOperand<Double> opSpecific = oprd.asOPDouble();
			if(opSpecific == null) {
				XPOperand<Integer> opInt = oprd.asOPInteger();
				if(opInt == null) throw new ManagedException(String.format("The % should operate only on integer or double.", symbol));
				opSpecific = new XPConvertToDouble(opInt);
			}
			res.addOperand(opSpecific);
		}
		
		return res;
	}
	
}
