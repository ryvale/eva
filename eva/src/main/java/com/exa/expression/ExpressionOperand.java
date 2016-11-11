package com.exa.expression;

public abstract class ExpressionOperand<T extends XPItem<T>> extends OperandBase<T> {
	protected StackEvaluator<T> evaluator;
	
	public ExpressionOperand(StackEvaluator<T> evaluator) {
		super();
		this.evaluator = evaluator;
	}
	
	public Operand<T> compute() throws XPressionException { return evaluator.compute(); }

	public StackEvaluator<T> evaluator() { return evaluator; }
}
