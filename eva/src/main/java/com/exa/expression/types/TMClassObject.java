package com.exa.expression.types;

import com.exa.expression.TypeMan;

public class TMClassObject<_C extends _P, _P> extends TypeMan<_C> {
	protected TMClassObject<_P, ?> parent;

	public TMClassObject(TMClassObject<_P, ?> parent) {
		super();
		this.parent = parent;
	}
	
}
