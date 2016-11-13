package com.exa.expression;

public abstract class OperandMan<T extends XPItem<T>> extends ItemMan<T> {
	protected StackEvaluator<T> evaluator;
	
	public OperandMan(StackEvaluator<T> evaluator) {
		this.evaluator = evaluator;
	}

	@Override
	public boolean push(T item) {
		ComputedOperator<T> cop = evaluator.peekOperator();
		
		if(cop == null) {
			evaluator.pushOperand(item);
			return true;
		}
		
		if(cop.expectOperand()) {
			if(cop.canManageNextOperand(item.asOperand())) cop.incOperandNumber();
			evaluator.pushOperand(item);
			return true;
		}
		
		evaluator.rollTopOperatorInOperand(); 
		
		while((cop = evaluator.peekOperator()) != null) {
			if(cop.expectOperand()) {
				cop.incOperandNumber();
				if(cop.expectOperand()) {
					//cop.incOperandNumber();
					break;
				}
			}
			
			evaluator.rollTopOperatorInOperand();
		}
		
		evaluator.pushOperand(item);
		return true;
	}

}
