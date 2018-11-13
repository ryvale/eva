package com.exa.expression.eval;

import com.exa.expression.Variable;
import com.exa.expression.VariableContext;

import com.exa.expression.XPOperand;
import com.exa.utils.ManagedException;

public class XPressionVariable<T> implements Variable<T> {
	private String name;
	private XPOperand<T> xpValue;
	
	private XPEvaluator evaluator;
	
	private Class<?> valueClass;
	
	private VariableContext vc;

	public XPressionVariable(String name, Class<?> valueClass, XPOperand<T> xpValue, XPEvaluator evaluator, VariableContext vc) {
		super();
		this.name = name;
		this.xpValue = xpValue;
		
		this.valueClass = valueClass;
		
		this.evaluator = evaluator;
		
		this.vc = vc;
	}

	@Override
	public T value() throws ManagedException {
		evaluator.pushVariableContext(vc);
		T res = xpValue.value(evaluator);
		evaluator.popVariableContext();
		return res;
	}

	@Override
	public void value(T value) {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void valueToSet(Object xpValue) throws ManagedException {
		if(xpValue instanceof XPOperand) this.xpValue = (XPOperand<T>)xpValue;
		
		throw new ManagedException("The variable %s canot be assigned by %");
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Class<?> valueClass() {
		return valueClass;
	}

}
