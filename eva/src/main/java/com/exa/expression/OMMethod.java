package com.exa.expression;

import java.util.List;
import java.util.Vector;

import com.exa.eva.EvaException;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public class OMMethod<T> extends OMFunction<OMMethod.XPOprtMethodBase<T>> {
	
	public abstract static class XPOprtMethodBase<T> extends XPOperatorBase<T> {

		public XPOprtMethodBase(String symbol, int nbOperands) {
			super(symbol, nbOperands);

		}
	}
	
	public abstract static class XPOrtMethod<_C, T> extends XPOprtMethodBase<T> {
		
		public abstract class XPMethodResult extends XPDynamicTypeOperand<T> {
			protected XPOperand<_C> object;
			protected List<XPOperand<?>> params;
			
			public XPMethodResult(XPOperand<_C> object, List<XPOperand<?>> params) {
				super();
				this.object = object;
				this.params = params;
			}

			@Override
			public Type<?> type() {
				return XPOrtMethod.this.type();
			}
			
		}
		
		public XPOrtMethod(String symbol, int nbOperands) {
			super(symbol, nbOperands);
		}

		@Override
		public void resolve(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
			if(eval.numberOfOperands() < nbOperands) throw new EvaException(String.format("Error in the expression near %s . The number of argument expected %s is is lower than the availabe %s", symbol, nbOperands, eval.numberOfOperands()));
			
			Vector<XPOperand<?>> params = new Vector<>();
			
			//TypeMan<?> type = getType(eval, order, nbOperands);
			
			Type<?> type = type();
			
			for(int i=0; i < nbOperands - 1; i++) {
				XPOperand<?> oprd = resolveOperand(eval);
				
				params.insertElementAt(oprd, 0);
			}
			XPOperand<?> oprd = resolveOperand(eval);
			
			if(type != oprd.type()) throw new ManagedException(String.format("Unexpected error of object type while computing method %s", symbol));
				
			Type<_C> tmSpecific = type.specificType();
			
			XPMethodResult res = createResultOperand(tmSpecific.valueOrNull(oprd),  params);
			
			eval.push(res);

		}
		
		/*@Override
		public boolean canManage(Operand<XPression<?>, XPEvaluator> oprd, int order) {
			
			return true;
		}*/

		protected abstract XPMethodResult createResultOperand(XPOperand<_C> object, Vector<XPOperand<?>> params);

		
	}

	public abstract static class XPOrtStaticMethod<T> extends XPOprtMethodBase<T> {
		
		public abstract class XPMethodResult extends XPDynamicTypeOperand<T> {

			protected List<XPOperand<?>> params;
			
			public XPMethodResult(List<XPOperand<?>> params) {
				super();
				this.params = params;
			}

			@Override
			public Type<?> type() {
				return XPOrtStaticMethod.this.type();
			}
			
		}
		
		public XPOrtStaticMethod(String symbol, int nbOperands) {
			super(symbol, nbOperands);
		}

		@Override
		public void resolve(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
			if(eval.numberOfOperands() < nbOperands) throw new EvaException(String.format("Error in the expression near %s . The number of argument expected %s is is lower than the availabe %s", symbol, nbOperands, eval.numberOfOperands()));
			
			Vector<XPOperand<?>> params = new Vector<>();
			
			//Type<?> type = type();
			
			for(int i=0; i < nbOperands; i++) {
				XPOperand<?> oprd = resolveOperand(eval);
				
				params.insertElementAt(oprd, 0);
			}
			//XPOperand<?> oprd = resolveOperand(eval);
			
			//XPIdentifier<?> xpI = oprd.asOPIdentifier();
			
			//Type<?> idCls = eval.classesMan().getType(xpI.identifier().name());
			
			//if(type != oprd.type()) throw new ManagedException(String.format("Unexpected error of object type while computing method %s", symbol));
				
			//Type<T> tmSpecific = type.specificType();
			
			XPMethodResult res = createResultOperand(params);
			
			eval.push(res);

		}
		
		/*@Override
		public boolean canManage(Operand<XPression<?>, XPEvaluator> oprd, int order) {
			
			return true;
		}*/

		protected abstract XPMethodResult createResultOperand(Vector<XPOperand<?>> params);

		
	}
	
	
	private OMOperandType operandType; 
	
	public OMMethod(String symbol, int nbOperand, OMOperandType operandType) {
		super(symbol, nbOperand);
		
		this.operandType = operandType;
	}

	@Override
	public OMOperandType operandType() {
		return operandType;
	}
	
	
	
}
