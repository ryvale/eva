package com.exa.expression.eval.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.exa.eva.ComputedItem;
import com.exa.expression.OM;
import com.exa.expression.Type;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperandBase;
import com.exa.expression.XPression;
import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public class XPOprtDblMultiply extends XPOprtCummulableBinary<Double> {
	class ResultOperand extends XPOperandBase<Double> {
		
		List<XPOperand<Double>> oprds = new ArrayList<>();

		@Override
		public Double value(XPEvaluator evaluator) throws ManagedException {
			Double res = oprds.get(0).value(evaluator);
			
			if(res == null) throw new ManagedException(String.format("The oprator %s could not accept null operand", symbol));
			
			for(int i=1; i<oprds.size(); i++) {
				Double v = oprds.get(i).value(evaluator);
				if(v == null) throw new ManagedException(String.format("The oprator %s could not accept null operand", symbol));
				
				res *= v;
			}

			return res;
		}

		@Override
		public Type<?> type() { return ClassesMan.T_DOUBLE; }

		
		public void addOperand(XPOperand<Double> oprd) {
			oprds.add(oprd);
		}

		@Override
		public XPOperand<Double> asOPDouble() {
			return this;
		}
		
		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			
			for(XPOperand<?> oprd : oprds) {
				res.append(" * ").append(oprd == null ? "null" : oprd.toString());
			}
			
			return res.length() > 3 ? "(" + res.substring(3) + ")" : res.toString();
		}
	}


	public XPOprtDblMultiply(String symbol) {
		super(symbol);
	}

	@Override
	public Type<?> type() {
		return ClassesMan.T_DOUBLE;
	}
	
	@Override
	public boolean canManage(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
		if(!super.canManage(eval, order, nbOperands)) return false;
		
		int operandIndex = 0;
		for(int i = 0; i<nbOperands; i++) {
			ComputedItem<XPression<?>, XPression<?>, OM> coprd = eval.stackOperand(operandIndex);
			XPression<?> xp = coprd.item();
			
			if(xp.type() != ClassesMan.T_INTEGER && xp.type() != ClassesMan.T_DOUBLE) return false;
			
			operandIndex = OM.nextOperand(eval, operandIndex);
		}
		/*int operandIndex = 0;
		for(int i = 0; i<nbOperands; i++) {
			ComputedItem<XPression<?>, XPression<?>, OM> coprd = eval.stackOperand(operandIndex);
			XPression<?> xp = coprd.item();
			XPOperator<?> oprt = xp.asOperator();
			
			if(oprt == null) {
				if(xp.asOperand().type() != ClassesMan.T_INTEGER && xp.asOperand().type() != ClassesMan.T_DOUBLE) return false;
				operandIndex++;
				continue;
			}
			
			ComputedOperator<XPression<?>, OM> coprt = coprd.asComputedOperator();
			Type<?> type = coprt.item().type();
			
			if(type != ClassesMan.T_INTEGER && type != ClassesMan.T_DOUBLE) return false;
			operandIndex += coprt.nbOperands()+1;
			
		}*/
				
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
				if(opInt == null) throw new ManagedException(String.format("The '%s' should operate only on integer or double.", symbol));
				opSpecific = new XPConvertToDouble(opInt);
			}
			res.addOperand(opSpecific);
		}
		
		return res;
	}
	
}
