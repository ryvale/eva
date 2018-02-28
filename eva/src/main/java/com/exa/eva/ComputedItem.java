package com.exa.eva;

public class ComputedItem<_T, _I extends Item<_I, ?, ? , ?, _OM>, _OM extends OperatorManager<_I, ?, ?, ?, _OM>> {
	protected _T item;
	protected int order;

	public ComputedItem(_T item, int order) {
		super();
		this.item = item;
		this.order = order;
	}
	
	public _T item() { return item; }
	
	public int order() { return order; }
	
	public ComputedOperator<_I, _OM> asComputedOperator() { return null; }
	
	public ComputedOperatorManager<_OM, _I> asComputedOperatorManager() { return null; }

}
