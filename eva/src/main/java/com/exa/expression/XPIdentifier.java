package com.exa.expression;

import java.util.Date;

import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public class XPIdentifier<T> extends XPOperandBase<T> {
	class Specific<V> extends XPDynamicTypeOperand<V> {
		private TypeMan<?> type;
		
		public Specific(TypeMan<T> type) {
			super();
			this.type = type;
		}

		@SuppressWarnings("unchecked")
		@Override
		public V value(XPEvaluator evaluator) throws ManagedException {
			
			Variable<?> var = evaluator.getVariable(identifier.name(), context);
			if(var == null) throw new ManagedException(String.format("Unable to retreive % variable value", identifier.name()));
			
			
			Object res = var.value();
			
			return (V)type().valueOrNull(res);
		}
		
		@Override
		public TypeMan<?> type() {	
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
		
		TypeMan<?> type = type();
		
		if(type == TypeMan.STRING) 
			cachedSpecific = new Specific<>(TypeMan.STRING);
		else if(type == TypeMan.INTEGER) 
			cachedSpecific = new Specific<>(TypeMan.INTEGER);
		else if(type == TypeMan.BOOLEAN) 
			cachedSpecific = new Specific<>(TypeMan.BOOLEAN);
		else if(type == TypeMan.DOUBLE) 
			cachedSpecific = new Specific<>(TypeMan.DOUBLE);
		else if(type == TypeMan.DATE) 
			cachedSpecific = new Specific<>(TypeMan.DATE);
		
		return cachedSpecific;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T value(XPEvaluator evaluator) throws ManagedException {
		Variable<?> var = evaluator.getVariable(identifier.name(), context);
		if(var == null) throw new ManagedException(String.format("Unable to retreive % variable value", identifier.name()));
		
		
		Object res = var.value();
		
		return (T)type().valueOrNull(res);
	}

	@Override
	public TypeMan<?> type() {
		VariableIdentifier vi = identifier.asVariableIdentifier();
		
		if(vi == null) return null;
		return TypeMan.getType(vi.valueClass());
	}
	
	
	public Identifier identifier() { return identifier; }

	@Override
	public XPIdentifier<?> asOPIdentifier() {
		return this;
	}

	@Override
	public XPOperand<String> asOPString() {
		return TypeMan.STRING.valueOrNull(getSpecific());
	}

	@Override
	public XPOperand<Date> asOPDate() {
		return TypeMan.DATE.valueOrNull(getSpecific());
	}

	@Override
	public XPOperand<Integer> asOPInteger() {
		return TypeMan.INTEGER.valueOrNull(getSpecific());
	}

	@Override
	public XPOperand<Boolean> asOPBoolean() {
		return TypeMan.BOOLEAN.valueOrNull(getSpecific());
	}

	
	
}
