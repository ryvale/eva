package com.exa.expression;

import java.util.List;
import java.util.Vector;

import com.exa.eva.EvaException;
import com.exa.eva.Operand;
import com.exa.expression.eval.XPEvaluator;
import com.exa.expression.eval.operators.OSMFunction;
import com.exa.utils.ManagedException;

public class OSMMethod<T> extends OSMFunction<OSMMethod.XPOrtMethod<T>> {
	
	public abstract static class XPOrtMethod<T> extends XPOperatorBase<T> {
		
		public abstract class XPMethodResult<T> extends XPDynamicTypeOperand<T> {
			protected XPOperand<T> object;
			protected List<XPOperand<?>> params;
			
			public XPMethodResult(XPOperand<T> object, List<XPOperand<?>> params) {
				super();
				this.object = object;
				this.params = params;
			}

			@Override
			public TypeMan<?> type() {
				return XPOrtMethod.this.type();
			}
			
		}
		
		public XPOrtMethod(String symbol, int nbOperands) {
			super(symbol, null, nbOperands);
		}

		@Override
		public void resolve(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
			if(eval.numberOfOperands() < nbOperands) throw new EvaException(String.format("Error in the expression near %s . The number of argument expected %s is is lower than the availabe %s", symbol, nbOperands, eval.numberOfOperands()));
			
			Vector<XPOperand<?>> params = new Vector<>();
			
			//TypeMan<?> type = getType(eval, order, nbOperands);
			
			TypeMan<?> type = type();
			
			for(int i=0; i < nbOperands - 1; i++) {
				XPOperand<?> oprd = resolveOperand(eval);
				
				params.insertElementAt(oprd, 0);
			}
			XPOperand<?> oprd = resolveOperand(eval);
			
			if(type != oprd.type()) throw new ManagedException(String.format("Unexpected error of object type while computing method %s", symbol));
				
			TypeMan<T> tmSpecific = type.specificType();
			
			XPMethodResult<T> res = createResultOperand(tmSpecific.valueOrNull(oprd),  params);
			
			eval.push(res);

		}
		
		@Override
		public boolean canManage(Operand<XPression<?>, XPEvaluator> oprd, int order) {
			
			return true;
		}

		protected abstract XPMethodResult<T> createResultOperand(XPOperand<T> object, Vector<XPOperand<?>> params);

		
	}

	public OSMMethod(String symbol, int nbOperand) {
		super(symbol, nbOperand);
	}

	@Override
	public OSMOperandType operandType() {
		return OSMOperandType.POST_OPERAND;
	}
	
	
	
}
