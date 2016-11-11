package com.exa.expression;

public class ComputedItem<T extends XPItem<T>> {
	protected T item;
	protected int order;

	public ComputedItem(T item, int order) {
		super();
		this.item = item;
		this.order = order;
	}
	
	public T item() { return item; }
	
	public int order() { return order; }
	
	public ComputedOperator<T> asComputedOperator() { return null; };

}
