package com.exa.expression;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exa.expression.eval.operators.OSMFunction;
import com.exa.expression.types.TMBoolean;
import com.exa.expression.types.TMClassObjectRoot;
import com.exa.expression.types.TMDate;
import com.exa.expression.types.TMDouble;
import com.exa.expression.types.TMInteger;
import com.exa.expression.types.TMString;
import com.exa.utils.ManagedException;

public abstract class TypeMan<T> {
	public final static TypeMan<String> STRING = new TMString();
	public final static TypeMan<Integer> INTEGER = new TMInteger();
	public final static TypeMan<Double> DOUBLE = new TMDouble();
	public final static TypeMan<Date> DATE = new TMDate();
	public final static TypeMan<Boolean> BOOLEAN = new TMBoolean();
	public final static TypeMan<Object> OBJECT = new TMClassObjectRoot();
	
	public static interface PropertySetter<T, V> {
		void set(T object, V value);
	}
	
	public static interface PropertyGetter<T, V> {
		V get(T object);
	}
	
	public static interface MethodRunner<T, V> {
		V execute(T object, List<XPOperand<?>> params) throws ManagedException;
	}
	
	public class Property<V> {
		private String name;
		private Class<?> type;
		
		private V defaultValue;
		
		private PropertyGetter<T, V> getter;
		
		private PropertySetter<T, V> setter;
		
		public Property(String name, Class<V> type, V defaultValue, PropertyGetter<T, V> getter, PropertySetter<T, V> setter) {
			super();
			this.name = name;
			this.type = type;
			this.defaultValue = defaultValue;
			
			this.getter = getter;
			this.setter = setter;
		}
		
		public Property(String name, Class<V> type, PropertyGetter<T, V> getter, PropertySetter<T, V> setter) {
			this(name, type, null, getter, setter);
		}
		
		public Property(String name, Class<V> type, PropertyGetter<T, V> getter) {
			this(name, type, null, getter, null);
		}

		public String name() { return name; }
		
		public TypeMan<?> type() { return TypeMan.getType(type) ; }
		
		public V defaultValue() { return defaultValue; }
		
		public V getValue(T object) {
			return getter.get(object);
		}
		
		public void setValue(T object, V value) {
			setter.set(object, value);
		}
		
	}
	
	public class Method<V> {
		private String name;
		private Class<?> type;
		private OSMMethod<V> osm;
		
		
		public Method(String name, Class<V> type, OSMMethod<V> osm) {
			super();
			this.name = name;
			this.type = type;
			this.osm = osm;
		}
		
		public String name() { return name; }
		
		public TypeMan<?> type() { return TypeMan.getType(type) ; }
		
		public OSMMethod<V> osm() { return osm;}
		
		
		/*public V excute(T object, List<XPOperand<?>> params) throws ManagedException {
			return methodRunner.execute(object, params);
		}*/
		
	}
	
	public static interface TypesBrowser {
		boolean visit(TypeMan<?> tm);
	}
	
	private final static Map<Class<?>, TypeMan<?>> types = new HashMap<>();
	
	static {
		types.put(String.class, STRING);
		types.put(Integer.class, INTEGER);
		types.put(Double.class, DOUBLE);
		types.put(Boolean.class, BOOLEAN);
		types.put(Date.class, DATE);
	}
	
	protected final Map<String, Property<?>> properties = new HashMap<>();
	
	protected final Map<String, Method<?>> methods = new HashMap<>();
	
	
	
	public static void browse(TypesBrowser browser) {
		for(TypeMan<?> tm : types.values()) {
			if(browser.visit(tm)) continue;
			break;
		}
	}
	//value have not to be null
	public static <T>TypeMan<?> getType(T value) {
		return types.get(value.getClass());
	}
	
	public static <T>TypeMan<?> getType(Class<T> valueClass) {
		return types.get(valueClass);
	}
	
	public XPConstant<T> constant(T v) throws ManagedException {
		return new XPConstant<T>(v);
	}
	
	public void initialize() {
	}
	
	@SuppressWarnings("unchecked")
	public XPOperand<T> valueOrNull(XPOperand<?> oprd) {
		if(oprd.type() == this) return (XPOperand<T>)oprd;
		
		return null;
	}
	
	//value have not to be null
	@SuppressWarnings("unchecked")
	public T valueOrNull(Object value) {
		if(getType(value.getClass()) == this) return (T)value;
		
		return null;
	}
	
	public TypeMan<?> propertyType(String name) {
		Property<?> prty = properties.get(name);
		if(prty == null) return null;
		
		return prty.type();
	}
	
	public TypeMan<?> methodType(String name) {
		Method<?> method = methods.get(name);
		if(method == null) return null;

		return method.type();
	}
	
	/*public Object excuteMethod(Object object, String methodName, List<XPOperand<?>> params) throws ManagedException {
		Method<?> method = methods.get(methodName);
		if(method == null) return null;
		
		if(method.methodRunner == null) throw new ManagedException(String.format("No method body %s defined", methodName));
		
		T checkObject = valueOrNull(object);
		
		if(checkObject == null) throw new ManagedException(String.format("Invalid object while executing the method %s", methodName));
		
		return method.methodRunner.execute(checkObject, params);
	}*/

	public Object getProperty(Object object, String propertyName) throws ManagedException {
		Property<?> property = properties.get(propertyName);
		if(property == null) throw new ManagedException(String.format("No property %s", propertyName));
		
		if(property.getter == null) throw new ManagedException(String.format("No getter define for property %s", propertyName));
		
		T checkObject = valueOrNull(object);
		
		if(checkObject == null) throw new ManagedException(String.format("Invalid object for getter operation", propertyName));
		return property.getValue(checkObject);
	}
	
	public <V>TypeMan<V> specificType() { return null; }
	
	
	public OSMFunction<?> methodOSM(String methodName) {
		Method<?> method = methods.get(methodName);
		if(method == null) return null;
		
		return method.osm();
	}
	
}
