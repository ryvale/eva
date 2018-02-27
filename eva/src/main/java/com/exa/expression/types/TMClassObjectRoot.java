package com.exa.expression.types;

import java.util.HashMap;
import java.util.Map;

public class TMClassObjectRoot extends TMClassObject<Object, Object> {
	
	private Map<Class<?>, TMClassObject<?, ?>> classes = new HashMap<>();

	public TMClassObjectRoot() {
		super(null);
	}
	
	public void addClass(TMClassObject<?, ?> cls) {
		classes.put(cls.getClass(), cls);
	}

}
