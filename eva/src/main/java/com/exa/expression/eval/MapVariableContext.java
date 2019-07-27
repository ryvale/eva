package com.exa.expression.eval;

import java.util.HashMap;
import java.util.Map;

import com.exa.expression.Variable;
import com.exa.expression.VariableContext;
import com.exa.expression.XPOperand;
import com.exa.utils.ManagedException;

public class MapVariableContext implements VariableContext {
	
	private Map<String, Variable<?>> variables = new HashMap<>();
	
	private VariableContext parent;
	
	public MapVariableContext(VariableContext parent) {
		super();
		this.parent = parent;
	}
	
	public MapVariableContext() { this(null); }

	@Override
	public <T>Variable<T> addVariable(String name, Class<?> valueClass, T defaultValue) throws ManagedException {
		if(variables.containsKey(name)) throw new ManagedException(String.format("The variable %s alredy exists", name));
		
		MemoryVariable<T> res = new MemoryVariable<T>(name, valueClass, defaultValue);
		variables.put(name, res);
		
		return res;
	}

	@Override
	public VariableContext getParent() {
		return parent;
	}

	@Override
	public Variable<?> getContextVariable(String name) {
		return variables.get(name);
	}

	@Override
	public Variable<?> getVariable(String name) {
		VariableContext vc = this;
		Variable<?> res = null;
		do {
			res = vc.getContextVariable(name);
			if(res != null) break;
			
			vc = vc.getParent();
			
		} while(vc != null);
		
		return res;
	}

	@Override
	public void releaseVariable(String name) {
		variables.remove(name);
		
	}

	@Override
	public void clearVariable() {
		variables.clear();
	}

	@Override
	public void setParent(VariableContext vc) {
		this.parent = vc;
		
	}

	@Override
	public <T>void assignVariable(String name, T value) throws ManagedException {
		VariableContext vc = this;
		Variable<?> res = null;
		do {
			res = vc.getContextVariable(name);
			if(res != null) {
				vc.assignContextVariable(name, value);
				return;
			}
			
			vc = vc.getParent();
			
		} while(vc != null);
		
		throw new ManagedException(String.format("No variable %s found", name));
	}

	@Override
	public <T>void assignContextVariable(String name, T value) {
		variables.put(name, new MemoryVariable<T>(name, value.getClass(), value));
	}

	@Override
	public <T> void assignOrDeclareVariable(String name, Class<?> valueClass, T value) {
		VariableContext vc = this;
		Variable<?> res = null;
		do {
			res = vc.getContextVariable(name);
			if(res != null) {
				vc.assignContextVariable(name, value, valueClass);
				return;
			}
			
			vc = vc.getParent();
			
		} while(vc != null);
		
		try {
			addVariable(name, valueClass, value);
		} catch (ManagedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public <T> void assignContextVariable(String name, T value, Class<?> valueClass) {
		variables.put(name, new MemoryVariable<T>(name, valueClass, value));
		
		
	}

	@Override
	public <T> Variable<T> addVariable(String name, Class<?> valueClass, XPOperand<T> xpValue, XPEvaluator evaluator, VariableContext vc)	throws ManagedException {
		if(variables.containsKey(name)) throw new ManagedException(String.format("The variable %s alredy exists", name));
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		XPressionVariable<T> res = new XPressionVariable(name, valueClass, xpValue, evaluator, vc);
		variables.put(name, res);
		
		return res;
	}

	@Override
	public void visitAll(Visitor visitor) {
		for(String name : variables.keySet()) {
			visitor.visit(name, variables.get(name));
		}
		
	}
	
	

}
