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

public class OMLesserThan extends OMBinary<XPOperator<?>> {
	
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
			Object v1 = xp1.value(evaluator); 
			Object v2 = xp2.value(evaluator);
			
			if(v1 == null || v2 == null) return null;
			
			Number nb1 = (Number)v1;
			
			Number nb2 = (Number)v2;
			
			return orEqual ? (nb1.doubleValue() <= nb2.doubleValue()) : (nb1.doubleValue() < nb2.doubleValue());
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
			return (xp1 == null ? "null" : xp1.toString()) + (orEqual ?  " <= " : " < ") + (xp2 == null ? "null" : xp2.toString());
		}
	}

	protected boolean orEqual;
	
	public OMLesserThan(String symbol, Integer priority, boolean orEqual) {
		super(symbol, priority);
		
		this.orEqual = orEqual;
	}
	
	@Override
	public XPOperator<?> operatorOf(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
		if(eval.numberOfOperands() < nbOperands) throw new ManagedException(String.format("Insufficient number of parameters for the operator %s", symbol));
		
		return new XPOperatorWithResolver<>(symbol, 2, ClassesMan.T_BOOLEAN, (e, o, n) -> {
			XPOperand<?> xp2 = XPOperatorBase.resolveOperand(e);
			
			if(!Number.class.isAssignableFrom(xp2.type().valueClass())) throw new ManagedException(String.format("The operand should be numeric for the operator %s", symbol));
			
			XPOperand<?> xp1 = XPOperatorBase.resolveOperand(e);
			
			if(!Number.class.isAssignableFrom(xp1.type().valueClass())) throw new ManagedException(String.format("The operand should be numeric for the operator %s", symbol));
			
			return new ResultOperand(xp1, xp2);
		});
	}
}
