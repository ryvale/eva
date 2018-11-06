package com.exa.expression;

import java.util.HashMap;
import java.util.Map;

import com.exa.expression.eval.ClassesMan;
import com.exa.expression.types.TObjectClass;
import com.exa.utils.ManagedException;

public abstract class Type<T> {
	
	public static interface PropertySetter<T, V> {
		void set(T object, V value);
	}
	
	public static interface PropertyGetter<T, V> {
		V get(T object);
	}
	
	public class TypeVariable<V> {
		private String name;
		private Class<?> valueClass;
		
		public TypeVariable(String name, Class<V> valueClass) {
			super();
			this.name = name;
			this.valueClass = valueClass;
		}
		
		public String name() { return name; }
		
		public Class<?> valueClass() { return valueClass; }

	}
	
	public class Property<V> extends TypeVariable<V> {
		
		private V defaultValue;
		
		private PropertyGetter<T, V> getter;
		
		private PropertySetter<T, V> setter;
		
		public Property(String name, Class<V> valueClass, V defaultValue, PropertyGetter<T, V> getter, PropertySetter<T, V> setter) {
			super(name, valueClass);
			
			this.getter = getter;
			this.setter = setter;
			this.defaultValue = defaultValue;
		}
		
		public Property(String name, Class<V> type, PropertyGetter<T, V> getter, PropertySetter<T, V> setter) {
			this(name, type, null, getter, setter);
		}
		
		public Property(String name, Class<V> type, PropertyGetter<T, V> getter) {
			this(name, type, null, getter, null);
		}
		
		public V defaultValue() { return defaultValue; }
		
		public V getValue(T object) {
			return getter.get(object);
		}
		
		public void setValue(T object, V value) {
			setter.set(object, value);
		}
		
	}
	
	public class StaticProperty<V> extends TypeVariable<V> {
		
		private V value;

		public StaticProperty(Type<T> type, String name, Class<V> valueClass, V defaultValue) {
			type.super(name, valueClass);
			this.value = defaultValue;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}
		
	}
	
	public class Method<V> {
		private String name;
		private Class<?> valueClass;
		private OMMethod<V> osm;
		
		public Method(String name, Class<V> type, OMMethod<V> osm) {
			super();
			this.name = name;
			this.valueClass = type;
			this.osm = osm;
		}
		
		public String name() { return name; }
		
		public Class<?> valueClass() { return valueClass; }
		
		public OMMethod<V> osm() { return osm;}
		
	}
	
	protected final Map<String, Property<?>> properties = new HashMap<>();
	
	protected final Map<String, StaticProperty<?>> staticProperties = new HashMap<>();
	
	protected final Map<String, Method<?>> methods = new HashMap<>();
	
	protected final Map<String, Method<?>> staticMethods = new HashMap<>();
	
	protected final Class<?> valueClass;
		
	public Type(Class<?> valueClass) {
		super();
		this.valueClass = valueClass;
	}
	
	public XPConstant<T> constant(T v) throws ManagedException {
		return new XPConstant<T>(v, this);
	}
	
	@SuppressWarnings("unchecked")
	public XPOperand<T> valueOrNull(XPOperand<?> oprd) {
		if(oprd.type() == this) return (XPOperand<T>)oprd;
		
		return null;
	}
	
	//value have not to be null
	@SuppressWarnings("unchecked")
	public T valueOrNull(Object value) {
		if(value == null) return null;
		
		//if(valueClass == value.getClass()) return (T)value;
		
		if(valueClass.isInstance(value)) return (T)value;
		
		return null;
	}
	
	public Class<?> propertyValueClass(String name) {
		Property<?> prty = properties.get(name);
		if(prty == null) return null;
		
		return prty.valueClass();
	}
	
	public Class<?> staticPropertyValueClass(String name) {
		StaticProperty<?> prty = staticProperties.get(name);
		if(prty == null) return null;
		
		return prty.valueClass();
	}
	
	public Class<?> methodType(String name) {
		Method<?> method = methods.get(name);
		if(method == null) return null;

		return method.valueClass();
	}
	
	public Class<?> staticMethodType(String name) {
		Method<?> method = staticMethods.get(name);
		if(method == null) return null;

		return method.valueClass();
	}
	
	public Object getProperty(Object object, String propertyName) throws ManagedException {
		Property<?> property = properties.get(propertyName);
		if(property == null) throw new ManagedException(String.format("No property %s", propertyName));
		
		if(property.getter == null) throw new ManagedException(String.format("No getter define for property %s", propertyName));
		
		T checkObject = valueOrNull(object);
		
		if(checkObject == null) throw new ManagedException(String.format("Invalid object for getter operation", propertyName));
		return property.getValue(checkObject);
	}
	
	public Object getStaticProperty(String propertyName) throws ManagedException {
		StaticProperty<?> property = staticProperties.get(propertyName);
		if(property == null) throw new ManagedException(String.format("No static property %s for class", propertyName, typeName()));
		
		//if(property.getter == null) throw new ManagedException(String.format("No getter define for property %s", propertyName));
		
		//T checkObject = valueOrNull(object);
		
		return property.getValue();
	}
	
	public <V>Type<V> specificType() { return null; }
	
	
	public OMFunction<?> methodOSM(String methodName) {
		Method<?> method = methods.get(methodName);
		if(method == null) return null;
		
		return method.osm();
	}
	
	public OMFunction<?> staticMethodOSM(String methodName) {
		Method<?> method = staticMethods.get(methodName);
		if(method == null) return null;
		
		return method.osm();
	}
	
	public Class<?> valueClass() { return valueClass; }
	
	public abstract String typeName(); 
	
	public void initialize() {} 
	
	public TObjectClass<T, ?> asObjectClass() { return null; }
	
	public boolean isSubTypeOf(Type<?> type) { return type == this || type == ClassesMan.T_OBJECT; }
	
	public boolean canBeComputedBy(Type<?> type) { return type == this || type == ClassesMan.T_OBJECT; }
	
	public T convert(Object o) throws ManagedException {
		if(o == null) return null;
		throw new ManagedException(String.format("Unable to convert this object to a %s value", typeName()));
	}
	
}
