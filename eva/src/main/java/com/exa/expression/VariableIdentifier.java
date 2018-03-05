package com.exa.expression;

public class VariableIdentifier extends Identifier {
	//private Class<?> valueClass;
	private Type<?> type;

	public VariableIdentifier(String name, Type<?> type) {
		super(name, IDType.VARIABLE);
		this.name = name;
		//this.valueClass = valueClass;
		this.type = type;
	}
	
	/*public Class<?> valueClass() {
		return valueClass;
	}*/
	
	public Type<?> type() {
		return type;
	}


	@Override
	public VariableIdentifier asVariableIdentifier() {
		return this;
	}

}
