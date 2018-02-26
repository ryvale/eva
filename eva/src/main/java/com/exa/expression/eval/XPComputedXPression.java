package com.exa.expression.eval;

import com.exa.expression.XPression;

public class XPComputedXPression extends XPComputedItem<XPression<?>> {

	public XPComputedXPression(XPression<?> item, int order) {
		super(item, order);
	}

	@Override
	public XPComputedXPression asComputedXPression() {
		return this;
	}
	
	

}
