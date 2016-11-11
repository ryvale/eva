package com.exa.expression;

public abstract class SubExpOperandMan<T extends XPItem<T>> extends OperandMan<T> {
	protected String subExpSymbol;
	protected String subExpResolutionSymbol;

	public SubExpOperandMan(StackEvaluator<T> evaluator, String subExpSymbol, String subExpResolutionSymbol) {
		super(evaluator);
		this.subExpSymbol = subExpSymbol;
		this.subExpResolutionSymbol = subExpResolutionSymbol;
	}

	@Override
	public T parsed(String word) {
		if(subExpSymbol.equals(word)) {
			ExpressionOperand<T> expOp = createExpressionOperand();
			
			evaluator.openSubExpression(subExpResolutionSymbol);
			
			return expOp.asSpecificItem();
		}
		
		return null;
	}
	
	
	
	@Override
	public boolean process(String word) throws XPressionException {
		if(subExpSymbol.equals(word)) {
			evaluator.openSubExpression(subExpResolutionSymbol);
			return true;
		}
		if(subExpResolutionSymbol.equals(word)) {
			evaluator.closeSubExpression(subExpResolutionSymbol);
			return true;
		}
		return super.process(word);
	}

	protected abstract ExpressionOperand<T> createExpressionOperand();
	
}
