package com.exa.expression;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.exa.expression.types.TMBoolean;
import com.exa.expression.types.TMDate;
import com.exa.expression.types.TMDouble;
import com.exa.expression.types.TMInteger;
import com.exa.expression.types.TMString;

public abstract class TypeMan<T> {
	public final static TypeMan<String> STRING = new TMString();
	public final static TypeMan<Integer> INTEGER = new TMInteger();
	public final static TypeMan<Double> DOUBLE = new TMDouble();
	public final static TypeMan<Date> DATE = new TMDate();
	public final static TypeMan<Boolean> BOOLEAN = new TMBoolean();
	
	private final static Map<Class<?>, TypeMan<?>> types = new HashMap<>();
	
	static {
		types.put(String.class, STRING);
		types.put(Integer.class, INTEGER);
		types.put(Double.class, DOUBLE);
		types.put(Boolean.class, BOOLEAN);
		types.put(Date.class, DATE);
	}
	
	//v have not to be null
	public static <T>TypeMan<?> getType(T v) {
		return types.get(v.getClass());
	}
	
	public XPConstant<T> constant(T v) {
		return new XPConstant<T>(v);
	}
	
	@SuppressWarnings("unchecked")
	public XPOperand<T> valueOrNull(XPOperand<?> oprd) {
		if(oprd.type() == this) return (XPOperand<T>)oprd;
		
		return null;
	}

}
