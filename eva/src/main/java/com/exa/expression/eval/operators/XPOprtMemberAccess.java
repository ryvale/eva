package com.exa.expression.eval.operators;

import java.util.List;
import java.util.Vector;

import com.exa.eva.ComputedItem;
import com.exa.expression.OM;
import com.exa.expression.TypeMan;
import com.exa.expression.XPDynamicTypeOperand;
import com.exa.expression.XPIdentifier;
import com.exa.expression.XPOperand;
import com.exa.expression.XPression;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public class XPOprtMemberAccess<T> extends XPOprtCummulableBinary<T> {
	
	class XPProperty extends XPDynamicTypeOperand<T> {
		
		private List<XPOperand<?>> params;
		
		XPProperty(List<XPOperand<?>> params) {
			super();
			this.params = params;
		}

		@Override
		public T value() throws ManagedException {
			return getProperyValue(params);
		}

		@Override
		public TypeMan<?> type() {
			return XPOprtMemberAccess.this.type;
		}
		
	}
	
	private TypeMan<T> type;
	
	public XPOprtMemberAccess(String symbol, TypeMan<T> type) {
		super(symbol);
		this.type = type;
	}
	
	@Override
	public TypeMan<?> type() { return type; }

	@Override
	public boolean canManage(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
		if(!super.canManage(eval, order, nbOperands)) return false;
		
		
		ComputedItem<XPression<?>, XPression<?>, OM> coprd = eval.stackOperand(nbOperands-1);
		XPression<?> xp = coprd.item();
		
		TypeMan<?> typeMan = xp.type();
		
		if(eval.numberOfOperands() == 1) return typeMan == type();
		
		int i = nbOperands-2;
		while(i >= 0) {
			coprd = eval.stackOperand(i);
			xp = coprd.item();
			XPOperand<?> oprd = xp.asOperand();
			if(oprd == null) return false;
			
			XPIdentifier<?> xpIdentifier = oprd.asOPIdentifier();
			if(xpIdentifier == null) return false;
			
			typeMan = typeMan.propertyType(xpIdentifier.identifier().name());
			
			i--;
		}
		
		return typeMan == type();
	}

	@Override
	public XPOperand<T> createResultOperand(Vector<XPOperand<?>> params) throws ManagedException {
		return new XPProperty(params);
	}
	
	@SuppressWarnings("unchecked")
	public <V>V getProperyValue(List<XPOperand<?>> params) throws ManagedException {
		XPOperand<?> oprd = params.get(0);
		if(oprd == null) throw new ManagedException(String.format("Unexpected error near '.'"));
				
		Object object = oprd.value();
		TypeMan<?> typeMan = oprd.type();
		
		for(int i=1; i<params.size(); i++) {
			oprd =params.get(i);
			if(oprd == null) throw new ManagedException(String.format("Unexpected error near '.'"));
			XPIdentifier<?> xpIdentifier = oprd.asOPIdentifier();
			if(xpIdentifier == null) throw new ManagedException(String.format("Unexpected error near '.'"));
			
			String identifierName = xpIdentifier.identifier().name();
			object = typeMan.getProperty(object, identifierName);
			typeMan = typeMan.propertyType(identifierName);
		}

		return (V)typeMan.valueOrNull(object);
	}	

}
