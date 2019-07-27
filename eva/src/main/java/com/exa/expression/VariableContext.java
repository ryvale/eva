package com.exa.expression;

import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public interface VariableContext {
	
	public static interface Visitor {
		void visit(String name, Variable<?> v);
	}
	
	<T>Variable<T> addVariable(String name, Class<?> valueClass, T defaultValue) throws ManagedException;
	
	<T>Variable<T> addVariable(String name, Class<?> valueClass, XPOperand<T> xpValue, XPEvaluator evaluator, VariableContext vc) throws ManagedException;
	
	void releaseVariable(String name);
	
	VariableContext getParent();
	
	void setParent(VariableContext vc);
	
	Variable<?> getContextVariable(String name);
	
	Variable<?> getVariable(String name);
	
	void clearVariable();

	<T>void assignContextVariable(String name, T value);
	<T>void assignContextVariable(String name, T value, Class<?> valueClass);
	
	<T>void assignVariable(String name, T value) throws ManagedException;
	
	<T>void assignOrDeclareVariable(String name, Class<?> valueClass, T value);
	
	void visitAll(Visitor visitor);
}
