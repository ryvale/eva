package com.exa.expression.eval.operators;

import com.exa.expression.Type;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperatorBase;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public class XPOperatorWithResolver<T> extends XPOperatorBase<T> {
	public static interface Resolver {
		XPOperand<?> resolve(XPEvaluator eval, int order, int nbOperands) throws ManagedException;
	}
	
	private Type<?> type;
	private Resolver resolver;

	public XPOperatorWithResolver(String symbol, int nbOperands, Type<?> type, Resolver resolver) {
		super(symbol, nbOperands);
		
		this.type = type;
		this.resolver = resolver;
	}

	@Override
	public void resolve(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
		if(eval.numberOfOperands() < nbOperands) throw new ManagedException(String.format("Insufficient number of parameters for the operator %s . % expected while % provide.", symbol, nbOperands, eval.numberOfOperands()));
		
		XPOperand<?> oprdResult = resolver.resolve(eval, order, nbOperands);
		
		eval.push(oprdResult);
	}

	@Override
	public boolean canManage(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
		return false;
	}

	@Override
	public Type<?> type() {
		return type;
	}

}
