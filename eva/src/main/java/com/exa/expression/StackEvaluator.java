package com.exa.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class StackEvaluator<T extends XPItem<T>> {
	
	class SubExpProperty {
		String resolutionSymbol;
		int firstOfStack;

		public SubExpProperty(String resolutionSymbol, int firstOfStack) {
			this.resolutionSymbol = resolutionSymbol;
			this.firstOfStack = firstOfStack;
		}
	}
	
	protected Stack<ComputedItem<T>> stack = new Stack<>();
	protected Stack<ComputedOperator<T>> opStack = new Stack<>();
	protected Stack<SubExpProperty> subExpProperties = new Stack<>();
	
	protected int order = 0;
	protected SubExpressionFactory<T> subExpressionFactory = null;
	
	protected List<ItemMan<T>> managers;
	
	public StackEvaluator(List<ItemMan<T>> managers, SubExpressionFactory<T> subExpressionFactory) {
		super();
		this.managers = managers;
		
		this.subExpressionFactory = subExpressionFactory;
		openSubExpression(null);
		
		if(subExpressionFactory == null) return;
		
		Map<String, SubExpOperandMan<T>> subExpManagers = subExpressionFactory.subExpManagers(this);
		for(SubExpOperandMan<T> seom : subExpManagers.values()) managers.add(seom);
	}
	
	public StackEvaluator(SubExpressionFactory<T> subExpressionFactory) { this(new ArrayList<ItemMan<T>>(), subExpressionFactory); }
	
	public Operand<T> compute() throws XPressionException {
		ComputedOperator<T> cop;
		
		while(opStack.size() > 0) {
			cop = opStack.pop();
			cop.item().asOperator().resolve(this, cop.order(), cop.nbOperand());
			if(opStack.size()>0) {
				cop = opStack.peek();
				if(cop.expectOperand()) cop.incOperandNumber();
			}
		}
		
		ComputedItem<T> coprd = stack.peek();
		T item = coprd.item();
		if(item.asOperand() == null) {
			stack.pop();
			item.asOperator().resolve(this, coprd.order(), coprd.asComputedOperator().nbOperand());
		}
		
		return stack.get(0).item().asOperand();
	}
	
	public void flushOperatorStack() {
		while(subExpProperties.size()>2) {
			if(rollTopOperatorInOperand()) continue;
			subExpProperties.pop();
		}
	}
	
	public void push(String word) throws XPressionException {
		//if(subExpEvaluator != null) { subExpEvaluator.push(word); return; }
		
		for(ItemMan<T> man : managers) {
			if(man.process(word)) break;
		}
	}
	
	public void pushOperand(T item) { 
		//if(subExpEvaluator != null) { subExpEvaluator.pushOperand(item); return; }
		
		Operator<T> oprt = item.asOperator();
		stack.push(oprt == null ? new ComputedItem<>(item, order++) : new ComputedOperator<>(oprt, order++, oprt.nbOperand()));	
	}
	
	public void pushOperator(Operator<T> item) { 
		//if(subExpEvaluator != null) { subExpEvaluator.pushOperator(item); return; }
		
		if(item.isPostUnary()) {
			opStack.push(new ComputedOperator<>(item, order++, 1));
			return;
		}
	
		if(item.isPreUnary()) {
			opStack.push(new ComputedOperator<>(item, order++, 0));
			return;
		}
		
		opStack.push(new ComputedOperator<>(item, order++, 1)); //binary or ternary
	}
	
	public ComputedItem<T> peekOperand() {
		if(stack.size()>0) return stack.peek();
		
		return null;
	}
	
	public ComputedItem<T> popOperand() {
		if(stack.size()>0) return stack.pop();
		
		return null;
	}
	
	public Operand<T> popComputedOperand() throws XPressionException {
		ComputedItem<T> ci = stack.pop();
		T item = ci.item();
		Operand<T> oprd = item.asOperand();
		if(oprd == null) {
			item.asOperator().resolve(this, ci.order(), ci.asComputedOperator().nbOperand());
			oprd = stack.pop().item().asOperand();
		}
			
		return oprd;
	}
	
	public ComputedOperator<T> peekOperator() {
		if(opStack.size()>subExpProperties.peek().firstOfStack) return opStack.peek();
		
		return null;
	}
	
	public ComputedOperator<T> popOperator() {
		if(opStack.size()>subExpProperties.peek().firstOfStack) return opStack.pop();
		
		return null;
	}
	
	public boolean rollTopOperatorInOperand() {
		//if(opStack.size() <= subExpProperties.peek().firstOfStack) return false;
		
		//ComputedOperator<T> cop = opStack.pop();
		
		ComputedOperator<T> cop = popOperator();
		if(cop == null) return false;
		
		stack.push(cop);
		
		if((cop = peekOperator()) == null) return true;
		if(cop.expectOperand()) cop.incOperandNumber();
		
		return true;
	}
	
	public void openSubExpression(String resolutionSymbol) {
		subExpProperties.push(new SubExpProperty(resolutionSymbol, opStack.size()));
	}
	
	public void closeSubExpression(String checkSymbol) throws XPressionException {
		if(subExpProperties.size() == 0) throw new XPressionException("Error in expression near '"+checkSymbol+"'");
		SubExpProperty sep = subExpProperties.peek();
		
		if(checkSymbol.equals(sep.resolutionSymbol)) {
			
			while(rollTopOperatorInOperand());
			subExpProperties.pop();
			
			ComputedOperator<T> cop = peekOperator();
			if(cop == null) return;
			if(cop.expectOperand()) cop.incOperandNumber();
			return;
			
		}
		
		throw new XPressionException("Error in expression near '"+checkSymbol+"'");
	}
	
	public int stackLength() { return stack.size(); }
}
