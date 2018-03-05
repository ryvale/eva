package com.exa.expression.eval.operators;

import java.util.Vector;

import com.exa.eva.ComputedItem;
import com.exa.expression.Identifier;
import com.exa.expression.Identifier.IDType;
import com.exa.expression.OM;
import com.exa.expression.Type;
import com.exa.expression.XPIdentifier;
import com.exa.expression.XPOperand;
import com.exa.expression.XPression;
import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public class XPOprtStaticMemberAccess<T>  extends XPOprtCummulableBinary<T> {
	
	private Type<T> type;
	
	public XPOprtStaticMemberAccess(String symbol, Type<T> type) {
		super(symbol);
		
		this.type = type;
	}
	
	@Override
	public Type<?> type() {
		return type;
	}
	
	@Override
	public boolean canManage(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
		if(!super.canManage(eval, order, nbOperands)) return false;
		
		ClassesMan classesMan = eval.classesMan();
		
		ComputedItem<XPression<?>, XPression<?>, OM> coprd = eval.stackOperand(nbOperands-1);
		XPression<?> xp = coprd.item();
		
		XPOperand<?> oprd = xp.asOperand();
		if(oprd == null) return false;
		
		XPIdentifier<?> xpIdentifier = oprd.asOPIdentifier();
		
		if(xpIdentifier == null) return false;
		
		Identifier id = xpIdentifier.identifier();
		if(id == null) return false;
		
		if(!id.idType().equals(IDType.CLASS)) return false;
		
		Type<?> typeMan = eval.classesMan().getType(id.name());
		
		coprd = eval.stackOperand(nbOperands-2);
		oprd = xp.asOperand();
		if(oprd == null) return false;
		
		xpIdentifier = oprd.asOPIdentifier();
		if(xpIdentifier == null) return false;
		
		typeMan = classesMan.getType(typeMan.staticPropertyValueClass(xpIdentifier.identifier().name())) ;
	
		if(eval.numberOfOperands() == 2) return typeMan == type();
		
		int i = nbOperands-3;
		while(i >= 0) {
			coprd = eval.stackOperand(i);
			xp = coprd.item();
			oprd = xp.asOperand();
			if(oprd == null) return false;
			
			xpIdentifier = oprd.asOPIdentifier();
			if(xpIdentifier == null) return false;
			
			typeMan = classesMan.getType(typeMan.propertyValueClass(xpIdentifier.identifier().name())) ;
			
			i--;
		}
		
		return typeMan == type();
	}

	@Override
	public XPOperand<T> createResultOperand(Vector<XPOperand<?>> params) throws ManagedException {
		// TODO Auto-generated method stub
		return null;
	}

}
