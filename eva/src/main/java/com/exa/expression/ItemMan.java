package com.exa.expression;

public abstract class ItemMan<T> {
	
	public boolean process(String word) throws XPressionException {
		T item = parsed(word);
		if(item == null) return false;
		
		return push(item);
	}
	
	public abstract T parsed(String word);
	public abstract boolean push(T item) throws XPressionException;
}
