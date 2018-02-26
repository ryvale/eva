package com.exa.expression.eval;

public class XPComputedItem<T> {
	protected T item;
	protected int order;
	
	
	public XPComputedItem(T item, int order) {
		super();
		this.item = item;
		this.order = order;
	}
	
	public T item() { return item; }
	
	public int order() { return order; }
	
	public XPComputedOSM asComputedOSM() { return null;}
	
	public XPComputedXPression asComputedXPression() { return null;}
	
	public XPComputedOperator asComputedOperator() { return null;}
	

}
