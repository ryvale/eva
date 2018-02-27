package com.exa.expression.eval.operators;

import java.util.ArrayList;
import java.util.List;

import com.exa.expression.XPOperator;
import com.exa.expression.eval.OperatorSymbMan;
import com.exa.expression.eval.XPEvaluator;
import com.exa.utils.ManagedException;

public abstract class OSMMutiple<T extends XPOperator<?>> extends OperatorSymbMan {
	private String symbol;
	
	private Integer priority;
	private int nbOperand;

	private List<T> operators = new ArrayList<>();
	
	public OSMMutiple(String symbol, Integer priority, int nbOperand) {
		super();
		this.symbol = symbol;
		this.priority = priority;
		this.nbOperand = nbOperand;
	}
	
	public void addOperator(T oprt) {
		operators.add(oprt);
	}
	
	@Override
	public String symbol() {
		return symbol;
	}
	
	@Override
	public T operatorOf(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
		for(T oprt : operators) {
			if(oprt.canManage(eval, order, nbOperands)) return oprt;
		}
		return null;
	}

	@Override
	public Integer priority() {	return priority; }

	@Override
	public int nbOperand() {
		return nbOperand;
	}

	
	
}
