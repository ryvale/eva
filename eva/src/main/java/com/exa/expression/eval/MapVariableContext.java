package com.exa.expression.eval;

import java.util.HashMap;
import java.util.Map;

import com.exa.expression.Variable;
import com.exa.expression.VariableContext;
import com.exa.expression.XPOperand;
import com.exa.utils.ManagedException;

public class MapVariableContext implements VariableContext {
	public static interface VarRealName {
		String name(String varName);
	}
	
	public static final VarRealName IDEM_VAR_NAME = vn -> vn;
	
	private Map<String, Variable<?>> variables = new HashMap<>();
	
	private VariableContext parent;
	
	private VarRealName varRealName;
	
	public MapVariableContext(VariableContext parent, VarRealName varRealName) {
		super();
		this.parent = parent;
		this.varRealName = varRealName;
	}
	
	public MapVariableContext() { this(null, IDEM_VAR_NAME); }
	
	public MapVariableContext(VariableContext parent) { this(parent, IDEM_VAR_NAME); }
	
	//public MapVariableContext(VariableContext parent) { this(parent, IDEM_VAR_NAME); }

	@Override
	public <T>Variable<T> addVariable(String codeName, Class<?> valueClass, T defaultValue) throws ManagedException {
		String name = varRealName.name(codeName);
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
	public Variable<?> getContextVariable(String codeName) {
		String name = varRealName.name(codeName);
		return variables.get(name);
	}

	@Override
	public Variable<?> getVariable(String codeName) {
		String name = varRealName.name(codeName);
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
	public void releaseVariable(String codeName) {
		String name = varRealName.name(codeName);
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
	public <T>void assignVariable(String codeName, T value) throws ManagedException {
		String name = varRealName.name(codeName);
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
	public <T>void assignContextVariable(String codeName, T value) {
		String name = varRealName.name(codeName);
		variables.put(name, new MemoryVariable<T>(name, value.getClass(), value));
	}

	@Override
	public <T> void assignOrDeclareVariable(String codeName, Class<?> valueClass, T value) {
		String name = varRealName.name(codeName);
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
	public <T> void assignContextVariable(String codeName, T value, Class<?> valueClass) {
		String name = varRealName.name(codeName);
		variables.put(name, new MemoryVariable<T>(name, valueClass, value));
	}

	@Override
	public <T> Variable<T> addVariable(String codeName, Class<?> valueClass, XPOperand<T> xpValue, XPEvaluator evaluator, VariableContext vc)	throws ManagedException {
		String name = varRealName.name(codeName);
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
