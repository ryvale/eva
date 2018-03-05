package com.exa.expression.eval;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.exa.expression.Type;
import com.exa.expression.types.TBoolean;
import com.exa.expression.types.TObjectClass;
import com.exa.expression.types.TDate;
import com.exa.expression.types.TDouble;
import com.exa.expression.types.TInteger;
import com.exa.expression.types.TString;
import com.exa.expression.types.TUnknown;

public class ClassesMan {
	public final static Type<String> T_STRING = new TString();
	public final static Type<Integer> T_INTEGER = new TInteger();
	public final static Type<Double> T_DOUBLE = new TDouble();
	public final static Type<Date> T_DATE = new TDate();
	public final static Type<Boolean> T_BOOLEAN = new TBoolean();
	
	public final static Type<?> T_UNKNOWN = new TUnknown();
	
	static {
		T_STRING.initialize();
		T_INTEGER.initialize();
		T_DOUBLE.initialize();
		T_DATE.initialize();
		T_BOOLEAN.initialize();
		T_UNKNOWN.initialize();
	}
	
	public final static ClassesMan STANDARD = new ClassesMan();
	
	protected final Map<Class<?>, Type<?>> types = new HashMap<>();
	protected final Map<String, Type<?>> typesByNames = new HashMap<>();
	
	{
		types.put(String.class, T_STRING);
		types.put(Integer.class, T_INTEGER);
		types.put(Double.class, T_DOUBLE);
		types.put(Boolean.class, T_BOOLEAN);
		types.put(Date.class, T_DATE);
		
		typesByNames.put(T_STRING.typeName(), T_STRING);
		typesByNames.put(T_INTEGER.typeName(), T_INTEGER);
		typesByNames.put(T_DOUBLE.typeName(), T_DOUBLE);
		typesByNames.put(T_BOOLEAN.typeName(), T_BOOLEAN);
		typesByNames.put(T_DATE.typeName(), T_DATE);
	}
	
	public ClassesMan() {
		super();
		
	}

	//value have not to be null
	public <T>Type<?> getType(T value) {
		return types.get(value.getClass());
	}
	
	public <T>Type<?> getType(Class<T> valueClass) {
		Type<?> type = types.get(valueClass);
		
		return type;
	}
	
	public Type<?> getType(String typeName) {
		return typesByNames.get(typeName);
	}
	
	public <T>void registerClass(TObjectClass<T, ?> cls) {
		cls.initialize();
		types.put(cls.valueClass(), cls);
		typesByNames.put(cls.typeName(), cls);
	}
	
	
	
	
}
