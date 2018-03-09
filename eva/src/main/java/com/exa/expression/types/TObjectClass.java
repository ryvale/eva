package com.exa.expression.types;

import com.exa.expression.Type;

public class TObjectClass<_C extends _P, _P> extends Type<_C> {
	protected TObjectClass<_P, ?> parent;
	
	private String typeName;

	public TObjectClass(TObjectClass<_P, ?> parent, Class<?> valueClass, String typeName) {
		super(valueClass);
		this.parent = parent;
		this.typeName = typeName;
	}
	
	@Override
	public String typeName() {
		return typeName;
	}

	@Override
	public TObjectClass<_C, ?> asObjectClass() {
		return this;
	}

	@Override
	public boolean isSubTypeOf(Type<?> type) {
		TObjectClass<?, ?> t = this;
		while(t != null) {
			if(t == type) return true;
			t = t.parent;
		}
		
		return false;
	}

	@Override
	public boolean canBeComputedBy(Type<?> type) {
		return isSubTypeOf(type);
	}
	
	
	
}
