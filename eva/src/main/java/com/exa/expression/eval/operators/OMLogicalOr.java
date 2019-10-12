package com.exa.expression.eval.operators;

import com.exa.expression.OMBinary;
import com.exa.expression.Type;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperandBase;
import com.exa.expression.XPOperator;
import com.exa.expression.XPOperatorBase;
import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public class OMLogicalOr  extends OMBinary<XPOperator<?>>  {
	
	class ResultOperand extends XPOperandBase<Boolean> {
		private XPOperand<?> xp1;
		private XPOperand<?> xp2;
		
		ResultOperand(XPOperand<?> xp1, XPOperand<?> xp2) {
			super();
			this.xp1 = xp1;
			this.xp2 = xp2;
		}

		@Override
		public Boolean value(XPEvaluator evaluator) throws ManagedException {
			XPOperand<Boolean> xpV1 = xp1.asOPBoolean(); 
			
			Boolean v1 = xpV1.value(evaluator);
			
			if(v1 == null) return null;
			
			if(v1.booleanValue() == true) return Boolean.TRUE;
			
			XPOperand<Boolean> xpV2 = xp2.asOPBoolean(); 
			
			Boolean v2 = xpV2.value(evaluator);
			
			if(v2 == null) return null;
			
			if(v2.booleanValue() == true) return Boolean.TRUE;
			
			
			return Boolean.FALSE;
		}

		@Override
		public Type<?> type() {
			return ClassesMan.T_BOOLEAN;
		}

		@Override
		public XPOperand<Boolean> asOPBoolean() {
			return this;
		}
		
		@Override
		public String toString() {
			return (xp1 == null ? "null" : xp1.toString()) + " || " + (xp2 == null ? "null" : xp2.toString());
		}
		
		
	}

	public OMLogicalOr(String symbol, Integer priority) {
		super(symbol, priority);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public XPOperator<?> operatorOf(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
		if(eval.numberOfOperands() < nbOperands) throw new ManagedException(String.format("Insufficient number of parameters for the operator %s", symbol));
		
		return new XPOperatorWithResolver<>(symbol, 2, ClassesMan.T_BOOLEAN, (e, o, n) -> {
			XPOperand<?> xp2 = XPOperatorBase.resolveOperand(e);
			
			if(xp2.type() != ClassesMan.T_BOOLEAN) throw new ManagedException(String.format("The operand should be boolean for the operator %s", symbol));
			
			XPOperand<?> xp1 = XPOperatorBase.resolveOperand(e);
			
			if(xp1.type() != ClassesMan.T_BOOLEAN) throw new ManagedException(String.format("The operand should be boolean for the operator %s", symbol));
			
			return new ResultOperand(xp1, xp2);
		});
	}

}
