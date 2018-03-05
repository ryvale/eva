package com.exa.expression.eval;

import com.exa.expression.eval.operators.XPOprtMemberAccess;
import com.exa.expression.eval.operators.XPOprtStaticMemberAccess;
import com.exa.expression.types.TObjectClass;

public class XPEClassesMan extends ClassesMan {
	private XPEvaluator evaluator;

	public XPEClassesMan(XPEvaluator evaluator) {
		super();
		this.evaluator = evaluator;
	}

	@Override
	public <T>void registerClass(TObjectClass<T, ?> cls) {
		super.registerClass(cls);
		
		evaluator.addMemberAccessOprt(new XPOprtMemberAccess<>(".", cls));
		
		evaluator.addMemberAccessOprt(new XPOprtStaticMemberAccess<>(".", cls));
	}
	
	
	
	
}
