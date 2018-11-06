package com.exa.expression;

import java.util.Date;

import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public class XPIdentifier<T> extends XPOperandBase<T> {
	class Specific<V> extends XPDynamicTypeOperand<V> {
		private Type<?> type;
		
		public Specific(Type<T> type) {
			super();
			this.type = type;
		}

		@SuppressWarnings("unchecked")
		@Override
		public V value(XPEvaluator evaluator) throws ManagedException {
			
			Variable<?> var = evaluator.getVariable(identifier.name());
			if(var == null) throw new ManagedException(String.format("Unable to retreive % variable value", identifier.name()));
			
			
			Object res = var.value();
			
			return (V)type().valueOrNull(res);
		}
		
		@Override
		public Type<?> type() {	
			return type;
		}
		
	}
	
	private Identifier identifier;
	
	private String context;
	
	private Specific<?> cachedSpecific = null;
	
	public XPIdentifier(Identifier identifier, String context) {
		super();
		this.identifier = identifier;
		this.context = context;
	}
	
	private Specific<?> getSpecific() {
		if(cachedSpecific != null) return cachedSpecific;
		
		Type<?> type = type();
		
		if(type == ClassesMan.T_STRING) 
			cachedSpecific = new Specific<>(ClassesMan.T_STRING);
		else if(type == ClassesMan.T_INTEGER) 
			cachedSpecific = new Specific<>(ClassesMan.T_INTEGER);
		else if(type == ClassesMan.T_BOOLEAN) 
			cachedSpecific = new Specific<>(ClassesMan.T_BOOLEAN);
		else if(type == ClassesMan.T_DOUBLE) 
			cachedSpecific = new Specific<>(ClassesMan.T_DOUBLE);
		else if(type == ClassesMan.T_DATE) 
			cachedSpecific = new Specific<>(ClassesMan.T_DATE);
		
		return cachedSpecific;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T value(XPEvaluator evaluator) throws ManagedException {
		/*Variable<?> var = evaluator.contextExists(context) ?  evaluator.getVariable(identifier.name(), context) : evaluator.getVariable(identifier.name());
		if(var == null) throw new ManagedException(String.format("Unable to retreive % variable value", identifier.name()));*/
		
		Variable<?> var = evaluator.getVariable(identifier.name());
		
		Object res = var.value();
		
		return (T)type().valueOrNull(res);
	}

	@Override
	public Type<?> type() {
		VariableIdentifier vi = identifier.asVariableIdentifier();
		
		if(vi == null) return null;
		return vi.type();
	}
	
	
	public Identifier identifier() { return identifier; }

	@Override
	public XPIdentifier<?> asOPIdentifier() {
		return this;
	}

	@Override
	public XPOperand<String> asOPString() {
		return ClassesMan.T_STRING.valueOrNull(getSpecific());
	}

	@Override
	public XPOperand<Date> asOPDate() {
		return ClassesMan.T_DATE.valueOrNull(getSpecific());
	}

	@Override
	public XPOperand<Integer> asOPInteger() {
		return ClassesMan.T_INTEGER.valueOrNull(getSpecific());
	}

	@Override
	public XPOperand<Boolean> asOPBoolean() {
		return ClassesMan.T_BOOLEAN.valueOrNull(getSpecific());
	}

	
	
}
