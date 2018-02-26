package com.exa.eva;

public abstract class ItemMan<T> {
	
	public boolean process(String word) throws EvaException {
		T item = parsed(word);
		if(item == null) return false;
		
		return push(item);
	}
	
	public abstract T parsed(String word);
	public abstract boolean push(T item) throws EvaException;
}
