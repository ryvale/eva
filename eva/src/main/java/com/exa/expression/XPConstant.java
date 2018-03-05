package com.exa.expression;

import com.exa.eva.EvaException;
import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.XPEvaluator;

public class XPConstant<T> extends XPDynamicTypeOperand<T> {
	private T v;
	private Type<?> type;

	public XPConstant(T v) {
		super();
		this.v = v;
		
		type = ClassesMan.STANDARD.getType(v);
	}
	
	public XPConstant(T v, Type<T> type) {
		super();
		this.v = v;
		
		this.type = type;
	}

	@Override
	public boolean isConstant() { return true; }

	@Override
	public T value(XPEvaluator evaluator) throws EvaException {
		return v;
	}

	@Override
	public Type<?> type() {
		return type;
	}
	
}
