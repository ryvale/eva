package com.exa.expression.eval;

import java.util.ArrayList;
import java.util.List;

import com.exa.expression.Variable;
import com.exa.expression.VariableContext;
import com.exa.expression.XPOperand;
import com.exa.utils.ManagedException;

public class CombinedVariableContext implements VariableContext {
	private List<VariableContext> vcs = new ArrayList<>();
	 
	private VariableContext parent;
	
	public CombinedVariableContext(VariableContext parent, VariableContext vc1, VariableContext vc2) {
		vcs.add(vc1);
		vcs.add(vc2);
	}

	@Override
	public <T> Variable<T> addVariable(String name, Class<?> valueClass, T defaultValue) throws ManagedException {
		return null;
	}

	@Override
	public <T> Variable<T> addVariable(String name, Class<?> valueClass, XPOperand<T> xpValue, XPEvaluator evaluator,
			VariableContext vc) throws ManagedException {
		
		return null;
	}

	@Override
	public void releaseVariable(String name) {
		
		
	}

	@Override
	public VariableContext getParent() {
		return parent;
	}

	@Override
	public void setParent(VariableContext vc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Variable<?> getContextVariable(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Variable<?> getVariable(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearVariable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void assignContextVariable(String name, T value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void assignContextVariable(String name, T value, Class<?> valueClass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void assignVariable(String name, T value) throws ManagedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void assignOrDeclareVariable(String name, Class<?> valueClass, T value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitAll(Visitor visitor) {
		// TODO Auto-generated method stub
		
	}

}
