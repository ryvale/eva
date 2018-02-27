package com.exa.expression;

public class Identifier {
	public static enum IDType { VARIABLE, PROPERTY, METHOD, FUNCTION }

	protected String name;
	protected IDType idType;
	
	public Identifier(String name, IDType idType) {
		super();
		this.name = name;
		this.idType = idType;
	}

	public String name() {	return name;}

	
	public IDType idType() {	return idType; }

	public VariableIdentifier asVariableIdentifier() { return null; }

}