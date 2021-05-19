package com.exa.expression.types;

import java.util.List;
import java.util.Vector;

import com.exa.eva.ComputedItem;
import com.exa.eva.OperatorManager.OMOperandType;
import com.exa.expression.OM;
import com.exa.expression.OMMethod;
import com.exa.expression.Type;
import com.exa.expression.XPOperand;
import com.exa.expression.XPression;
import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.XPEvaluator;
import com.exa.hardcore.Pointer;
import com.exa.utils.ManagedException;

public class TString extends Type<String> {
	
	
	private class MethodSubstring extends OMMethod.XPOrtMethod<String, String> {
		
		class ResultOperand extends XPMethodResult {

			public ResultOperand(XPOperand<String> object, List<XPOperand<?>> params) {
				super(object, params);
			}

			@Override
			public String value(XPEvaluator evaluator) throws ManagedException {
				
				XPOperand<Integer> xpStart = params.get(0).asOPInteger();
				if(xpStart == null) throw new ManagedException(String.format("Unexpected errer while executing the method %s", symbol));
				
				String str = object.value(evaluator);
				Integer start = xpStart.value(evaluator);
				if(params.size() > 1) {
					XPOperand<Integer> xpNb = params.get(1).asOPInteger();
					if(xpNb == null) throw new ManagedException(String.format("Unexpected errer while executing the method %s", symbol));
					
					Integer nb = xpNb.value(evaluator);
					
					return str.substring(start, start+nb);
					
				}
				return str.substring(start);
			}

			@Override
			public String toString() {
				XPOperand<?> start = null;
				XPOperand<?> nb = null;
				if(params.size() > 0) start = params.get(0);
				if(params.size() > 1)  nb = params.get(1);
				return (object == null ? "" : object.toString()) + ".substr(" + (start == null ? "null" : start.toString()) + ", " + (nb == null ? "null" : nb.toString()) + ")" ;
			}
			
		}

		public MethodSubstring(String symbol, int nbOperands) {
			super(symbol, nbOperands);
		}

		@Override
		public Type<?> type() {
			return ClassesMan.T_STRING;
		}

		@Override
		public boolean canManage(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
			if(eval.numberOfOperands() < nbOperands) return false;
			
			Pointer<Type<?>> tp = new Pointer<>();
			int nb = OM.operandAction(eval, 0, (xp) -> {
				tp.deref(xp.type());
				
				return true;
			});
			
			if(tp.deref()  != ClassesMan.T_INTEGER) return false;
			
			nb = OM.operandAction(eval, nb, (xp) -> {
				tp.deref(xp.type());
				
				return true;
			});
			
			if(tp.deref()  != ClassesMan.T_INTEGER) return false;
			
			nb = OM.operandAction(eval, nb, (xp) -> {
				tp.deref(xp.type());
				
				return true;
			});
			
			if(tp.deref()  != ClassesMan.T_STRING) return false;
			
			return true;
		}

		@Override
		protected XPMethodResult createResultOperand(XPOperand<String> object, Vector<XPOperand<?>> params) {
			return new ResultOperand(object, params);
		}
	}
	
	private class MethodIndexOf extends OMMethod.XPOrtMethod<String, Integer> {
		
		class ResultOperand extends XPMethodResult {

			public ResultOperand(XPOperand<String> object, List<XPOperand<?>> params) {
				super(object, params);
			}

			@Override
			public Integer value(XPEvaluator evaluator) throws ManagedException {
				
				XPOperand<String> xpSeek = params.get(0).asOPString();
				if(xpSeek == null) throw new ManagedException(String.format("Unexpected errer while executing the method %s", symbol));
				
				String str = object.value(evaluator);
				String seek = xpSeek.value(evaluator);
				
				return str.indexOf(seek);
			}
			
			@Override
			public String toString() {
				XPOperand<?> seek = null;
				if(params.size() > 0) seek = params.get(0);
				
				return (object == null ? "" : object.toString()) + ".indexOf(" + (seek == null ? "null" : seek.toString()) + ")" ;
			}
			
		}

		public MethodIndexOf(String symbol, int nbOperands) {
			super(symbol, nbOperands);
		}

		@Override
		public Type<?> type() {
			return ClassesMan.T_INTEGER;
		}

		@Override
		public boolean canManage(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
			if(eval.numberOfOperands() < nbOperands) return false;
			
			ComputedItem<XPression<?>, XPression<?>, OM> cxp = eval.stackOperand(nbOperands-1);
			XPression<?> xp = cxp.item();
			
			if(xp.type() != ClassesMan.T_STRING) return false;
			
			cxp = eval.stackOperand(nbOperands-2);
			
			if(cxp.item().type() != ClassesMan.T_STRING) return false;
			
			return true;
		}

		@Override
		protected XPMethodResult createResultOperand(XPOperand<String> object, Vector<XPOperand<?>> params) {
			return new ResultOperand(object, params);
		}
	}

	public TString() {
		super(String.class);
	}
	
	private class MethodTrim extends OMMethod.XPOrtMethod<String, String> {
		
		class ResultOperand extends XPMethodResult {

			public ResultOperand(XPOperand<String> object, List<XPOperand<?>> params) {
				super(object, params);
			}

			@Override
			public String value(XPEvaluator evaluator) throws ManagedException {
				
				String str = object.value(evaluator);
				
				
				return str.trim();
			}
			
			@Override
			public String toString() {
				
				return (object == null ? "" : object.toString()) + ".trim()" ;
			}
			
		}
		
		public MethodTrim(String symbol) {
			super(symbol, 1);
		}
		
		@Override
		public Type<?> type() {
			return ClassesMan.T_STRING;
		}
		
		@Override
		public boolean canManage(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
			if(eval.numberOfOperands() < nbOperands) return false;
			
			ComputedItem<XPression<?>, XPression<?>, OM> cxp = eval.stackOperand(nbOperands-1);
			XPression<?> xp = cxp.item();
			
			if(xp.type() != ClassesMan.T_STRING) return false;
			
			return true;
		}
		
		@Override
		protected XPMethodResult createResultOperand(XPOperand<String> object, Vector<XPOperand<?>> params) {
			return new ResultOperand(object, params);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type<String> specificType() {
		return this;
	}

	@Override
	public String typeName() {
		return "string";
	}

	@Override
	public void initialize() {
		properties.put("length", new Property<>("length", Integer.class, object -> object.length()));
		
		OMMethod<String> osm = new OMMethod<>("substr", 3, OMOperandType.POST_OPERAND);
		osm.addOperator(new MethodSubstring("substr", 3));
		methods.put("substr", new Method<>("substr", String.class, osm));
		
		OMMethod<Integer> osmInt = new OMMethod<>("indexOf", 2, OMOperandType.POST_OPERAND);
		osmInt.addOperator(new MethodIndexOf("indexOf", 2));
		methods.put("indexOf", new Method<>("indexOf", Integer.class, osmInt));
		
		OMMethod<String> osmTrim = new OMMethod<>("trim", 1, OMOperandType.POST_OPERAND);
		osmTrim.addOperator(new MethodTrim("trim"));
		methods.put("trim", new Method<>("trim", String.class, osmTrim));
	}

	@Override
	public String convert(Object o) throws ManagedException {
		if(o instanceof String) return o.toString();
		return super.convert(o);
	}
	
	
	
	
	
}
