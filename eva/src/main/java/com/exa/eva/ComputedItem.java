package com.exa.eva;

public class ComputedItem<T extends Item<T, _E>, _E extends StackEvaluator<T>> {
	protected T item;
	protected int order;

	public ComputedItem(T item, int order) {
		super();
		this.item = item;
		this.order = order;
	}
	
	public T item() { return item; }
	
	public int order() { return order; }
	
	public ComputedOperator<T, _E> asComputedOperator() { return null; };

}
