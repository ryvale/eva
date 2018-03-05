package com.exa.expression.types;

import java.util.HashMap;
import java.util.Map;

public class TObjectClassRoot  {
	
	private Map<Class<?>, TObjectClass<?, ?>> classes = new HashMap<>();
	
	public void registerClass(TObjectClass<?, ?> cls, Class<?> valueClass) {
		classes.put(valueClass, cls);
	}
	
	public TObjectClass<?, ?> getRegisteredClass(Object object) {
		return classes.get(object.getClass());
	}
	
	public TObjectClass<?, ?> getRegisteredClass(Class<?> valueClass) {
		return classes.get(valueClass);
	}

}
