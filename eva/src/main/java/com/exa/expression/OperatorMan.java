package com.exa.expression;

public abstract class OperatorMan<T extends XPItem<T>> extends ItemMan<T> {
	public static final int DO_NOTHING = 0;
	public static final int PUSH = 1;
	public static final int ROLL = 2;
	
	protected StackEvaluator<T> evaluator;
	
	public OperatorMan(StackEvaluator<T> evaluator) {
		super();
		this.evaluator = evaluator;
	}
	
	@Override
	public boolean push(T item) throws XPressionException {
		Operator<T> op = item.asOperator();
		if(op == null) throw new XPressionException("Defined operator expected in the expression");
		
		int resolutionStatus = resolutionStatus(op);
		
		if((resolutionStatus & ROLL) == ROLL) resolutionStatus = evaluator.rollTopOperatorInOperand(op, resolutionStatus);
		
		if((resolutionStatus & PUSH) == PUSH) evaluator.pushOperator(op);
		
		return true;
	}
	
	protected int resolutionStatus(Operator<T> op) {
		ComputedOperator<T> cop = evaluator.peekOperator();
		if(cop == null) return PUSH;
		Operator<T> top = cop.item().asOperator();
		
		if(top.operandsAreCumulable() && op == top) {
			cop.incExpectedOperandNumber();
			return DO_NOTHING;
		}
		
		if(top.priority() < op.priority()) return ROLL + PUSH;
		
		if(top.priority() == op.priority()) {
			if(cop.expectOperand()) return PUSH;
			return ROLL + PUSH;
		}
		
		return  PUSH;
	}
	
}
