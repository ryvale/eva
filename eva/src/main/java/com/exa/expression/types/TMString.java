package com.exa.expression.types;

import java.util.List;
import java.util.Vector;

import com.exa.eva.ComputedItem;
import com.exa.expression.OM;
import com.exa.expression.OMMethod;
import com.exa.expression.TypeMan;
import com.exa.expression.XPOperand;
import com.exa.expression.XPression;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public class TMString extends TypeMan<String> {
	
	
	private class MethodSubstring extends OMMethod.XPOrtMethod<String> {
		
		class ResultOperand extends XPMethodResult {

			public ResultOperand(XPOperand<String> object, List<XPOperand<?>> params) {
				super(object, params);
			}

			@Override
			public String value() throws ManagedException {
				/*XPOperand<String> xpStr = params.get(0).asOPString();
				if(xpStr == null) throw new ManagedException(String.format("Unexpected errer while executing the method %s", symbol));*/
				
				XPOperand<Integer> xpStart = params.get(0).asOPInteger();
				if(xpStart == null) throw new ManagedException(String.format("Unexpected errer while executing the method %s", symbol));
				
				String str = object.value();
				Integer start = xpStart.value();
				if(params.size() > 1) {
					XPOperand<Integer> xpNb = params.get(1).asOPInteger();
					if(xpNb == null) throw new ManagedException(String.format("Unexpected errer while executing the method %s", symbol));
					
					Integer nb = xpNb.value();
					
					return str.substring(start, start+nb);
					
				}
				return str.substring(start);
			}
			
		}

		public MethodSubstring(String symbol, int nbOperands) {
			super(symbol, nbOperands);
		}

		@Override
		public TypeMan<?> type() {
			return TypeMan.STRING;
		}

		@Override
		public boolean canManage(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
			if(eval.numberOfOperands() < nbOperands) return false;
			
			ComputedItem<XPression<?>, XPression<?>, OM> cxp = eval.stackOperand(nbOperands-1);
			XPression<?> xp = cxp.item();
			
			if(xp.type() != TypeMan.STRING) return false;
			
			cxp = eval.stackOperand(nbOperands-2);
			
			if(cxp.item().type() != TypeMan.INTEGER) return false;
			
			if(nbOperands > 2) {
				cxp = eval.stackOperand(nbOperands-3);
				
				if(cxp.item().type() != TypeMan.INTEGER) return false;
			}
			
			return true;
		}

		@Override
		protected XPMethodResult createResultOperand(XPOperand<String> object, Vector<XPOperand<?>> params) {
			return new ResultOperand(object, params);
		}
	}

	public TMString() {
		properties.put("length", new Property<>("length", Integer.class, object -> object.length()));
		
		OMMethod<String> osm = new OMMethod<>("substr", 3);
		osm.addOperator(new MethodSubstring("substr", 2));
		osm.addOperator(new MethodSubstring("substr", 3));
		
		methods.put("substr", new Method<>("substr", String.class, osm));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public TypeMan<String> specificType() {
		return this;
	}
	
	
	
}
