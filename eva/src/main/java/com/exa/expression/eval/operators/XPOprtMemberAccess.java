package com.exa.expression.eval.operators;

import java.util.List;
import java.util.Vector;

import com.exa.eva.ComputedItem;
import com.exa.expression.OM;
import com.exa.expression.Type;
import com.exa.expression.XPDynamicTypeOperand;
import com.exa.expression.XPIdentifier;
import com.exa.expression.XPOperand;
import com.exa.expression.XPression;
import com.exa.expression.eval.ClassesMan;
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
		public T value(XPEvaluator evaluator) throws ManagedException {
			return getProperyValue(params, evaluator);
		}

		@Override
		public Type<?> type() {
			return XPOprtMemberAccess.this.type;
		}
		
	}
	
	private Type<T> type;
	
	public XPOprtMemberAccess(String symbol, Type<T> type) {
		super(symbol);
		this.type = type;
	}
	
	@Override
	public Type<?> type() { return type; }

	@Override
	public boolean canManage(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
		if(!super.canManage(eval, order, nbOperands)) return false;
		
		ClassesMan classesMan = eval.classesMan();
		
		ComputedItem<XPression<?>, XPression<?>, OM> coprd = eval.stackOperand(nbOperands-1);
		XPression<?> xp = coprd.item();
		
		Type<?> typeMan = xp.type();
		
		if(eval.numberOfOperands() == 1) return typeMan == type();
		
		int i = nbOperands-2;
		while(i >= 0) {
			coprd = eval.stackOperand(i);
			xp = coprd.item();
			XPOperand<?> oprd = xp.asOperand();
			if(oprd == null) return false;
			
			XPIdentifier<?> xpIdentifier = oprd.asOPIdentifier();
			if(xpIdentifier == null) return false;
			
			typeMan = classesMan.getType(typeMan.propertyValueClass(xpIdentifier.identifier().name())) ;
			
			i--;
		}
		
		return typeMan == type();
	}

	@Override
	public XPOperand<T> createResultOperand(Vector<XPOperand<?>> params) throws ManagedException {
		return new XPProperty(params);
	}
	
	@SuppressWarnings("unchecked")
	public <V>V getProperyValue(List<XPOperand<?>> params, XPEvaluator evaluator) throws ManagedException {
		XPOperand<?> oprd = params.get(0);
		if(oprd == null) throw new ManagedException(String.format("Unexpected error near '.'"));
		
		ClassesMan classesMan = evaluator.classesMan();
		
		Object object = oprd.value(evaluator);
		Type<?> typeMan = oprd.type();
		
		for(int i=1; i<params.size(); i++) {
			oprd =params.get(i);
			if(oprd == null) throw new ManagedException(String.format("Unexpected error near '.'"));
			XPIdentifier<?> xpIdentifier = oprd.asOPIdentifier();
			if(xpIdentifier == null) throw new ManagedException(String.format("Unexpected error near '.'"));
			
			String identifierName = xpIdentifier.identifier().name();
			object = typeMan.getProperty(object, identifierName);
			
			typeMan = classesMan.getType(typeMan.propertyValueClass(identifierName));
		}

		return (V)typeMan.valueOrNull(object);
	}	

}
