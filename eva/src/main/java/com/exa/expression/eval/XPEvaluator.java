package com.exa.expression.eval;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.exa.eva.ComputedItem;
import com.exa.eva.ComputedOperator;
import com.exa.eva.StackEvaluator;
import com.exa.expression.VariableIdentifier;
import com.exa.expression.Variable;
import com.exa.expression.VariableContext;
import com.exa.expression.XPConstant;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperator;
import com.exa.expression.XPression;
import com.exa.expression.eval.operators.OSMClosedParenthesis;
import com.exa.expression.eval.operators.OSMFunction;
import com.exa.expression.eval.operators.OSMOpenParenthesis;
import com.exa.expression.eval.operators.OSMParamSeparator;
import com.exa.utils.ManagedException;

import static com.exa.expression.eval.OperatorSymbMan.OSMType;
import static com.exa.expression.eval.OperatorSymbMan.OSMAssociativity;
import static com.exa.expression.eval.OperatorSymbMan.OSMOperandType;

public class XPEvaluator implements StackEvaluator<XPression<?>> {
	public static final OSMOpenParenthesis OP_OPEN_PARENTHESIS = new OSMOpenParenthesis("(");
	public static final OSMClosedParenthesis OP_CLOSED_PARENTHESIS = new OSMClosedParenthesis(")");
	public static final OSMParamSeparator OP_PARAMS_SEPARATOR = new OSMParamSeparator(",");
	
	public final static XPConstant<Boolean> TRUE = new XPConstant<>(Boolean.TRUE);
	public final static XPConstant<Boolean> FALSE = new XPConstant<>(Boolean.FALSE);
	
	protected Stack<XPComputedItem<XPression<?>>> outputStack = new Stack<>();
	protected Stack<XPComputedOSM> opStack = new Stack<>();
	
	private int order = 0;
	
	protected Map<String, OperatorSymbMan> osmsPreUnary = new HashMap<>();
	protected Map<String, OperatorSymbMan> osmsPostUnary = new HashMap<>();
	
	protected Map<String, OperatorSymbMan> osmsBinary = new HashMap<>();
	
	protected Map<String, OSMFunction<?>> osmsFunctions = new HashMap<>();
	
	private VariableContext variableContext;
	
	private int nbFreeOperand = 0;
	
	public XPEvaluator(VariableContext variableContext) {
		super();
		this.variableContext = variableContext;
	}
	
	public XPEvaluator() {
		this(new MapVariableContext());
	}
	
	public void addPreUnaryOp(OperatorSymbMan osm) {
		osmsPreUnary.put(osm.symbol(), osm);
	}
	
	public void addPostUnaryOp(OperatorSymbMan osm) {
		osmsPostUnary.put(osm.symbol(), osm);
	}
	
	public void addBinaryOp(OperatorSymbMan osm) {
		osmsBinary.put(osm.symbol(), osm);
	}
	
	public void addFunction(OSMFunction<?> osm) {
		osmsFunctions.put(osm.symbol(), osm);
	}
	
	public OperatorSymbMan getPreUnaryOp(String symbol) {
		return osmsPreUnary.get(symbol);
	}
	
	public OperatorSymbMan getPostUnaryOp(String symbol) {
		return osmsPostUnary.get(symbol);
	}
	
	public OperatorSymbMan getBinaryOp(String symbol) {
		return osmsBinary.get(symbol);
	}
	
	public OSMFunction<?> getFunction(String symbol) {
		return osmsFunctions.get(symbol);
	}
	
	public void addVariable(String name, Class<?> valueClass, Object defaultValue) throws ManagedException {
		variableContext.addVariable(name, valueClass, defaultValue);
	}
	
	public VariableIdentifier getIdentifier(String name) {
		Variable<?> var = variableContext.getVariable(name);
		
		if(var == null) return null;
		
		return new VariableIdentifier(name, var.valueClass(), var.variableContext());
	}
	
	@Override
	public ComputedOperator<XPression<?>, ? extends XPEvaluator> topOperator() {
		
		return null;
	}
	
	public XPComputedOSM topOSM() {
		if(opStack.size() == 0) return null;
		return opStack.peek();
	}

	@Override
	public void pushOperator(XPression<?> oprd) {
		
	}

	@Override
	public void push(XPression<?> oprt) {
		
	}

	@Override
	public int numberOfOperands() {
		return outputStack.size();
	}

	@Override
	public ComputedItem<XPression<?>, XPEvaluator> popOperand() {
		return null;
	}
	
	public XPComputedItem<XPression<?>> popOutput() {
		return outputStack.pop();
	}

	@Override
	public void pushOperand(XPression<?> item) {
		/*XPOperator<?> oprt = item.asOperator();
		outputStack.push(oprt == null ? new XPComputedItem<>(item, order++) : new XPComputedOperator(oprt, order++, oprt.nbOperand()));*/
	}
	
	public void push(XPOperand<?> item) {
		if(opStack.size() > 0) {
			XPComputedOSM coprt = opStack.peek();
			
			if(opStack.size() > 1) {
				if(coprt.item() == OP_OPEN_PARENTHESIS) {
					XPComputedOSM coprt2 = opStack.get(opStack.size()-2);
					if(coprt2.item().type().equals(OSMType.FUNCTION)) {
						//if(coprt2.expectOperand()) coprt2.incOperandNumber();
					}
					outputStack.push(new XPComputedItem<>(item, order++));
					++nbFreeOperand;
					return;
				}
			}
			
			//if(coprt.expectOperand()) coprt.incOperandNumber();
			
		}
		++nbFreeOperand;
		outputStack.push(new XPComputedItem<>(item, order++));
	}
	
	private void _push(OperatorSymbMan osm) {
		OSMOperandType otype = osm.operandType();
		if(otype == null) {
			opStack.push(new XPComputedOSM(osm, order++, 1));
			return;
		}
		
		if(otype.equals(OSMOperandType.POST_OPERAND)) {
			opStack.push(new XPComputedOSM(osm, order++, 1));
			return;
		}
	
		if(otype.equals(OSMOperandType.PRE_OPERAND)) {
			opStack.push(new XPComputedOSM(osm, order++, 0));
			return;
		}
		
		opStack.push(new XPComputedOSM(osm, order++, 1));
	}
	
	public boolean popOperatorToOutput() throws ManagedException {
		XPComputedOSM cop = opStack.pop();
		if(cop == null) return false;
		
		nbFreeOperand -= cop.nbExpectedNbOperand();
		
		XPOperator<?> op = cop.item().operatorOf(this, cop.order, cop.nbExpectedNbOperand());
		
		outputStack.push(new XPComputedOperator(op, cop.order(), cop.nbExpectedNbOperand()));
		
		nbFreeOperand += 1;
		
		if(opStack.size() == 0) return true;
		
		cop = opStack.peek();
		
		if(cop.item() == OP_OPEN_PARENTHESIS) {
			if(opStack.size() == 1) return true;
			
			cop = opStack.get(opStack.size() - 2);
			
			if(!cop.item().type().equals(OSMType.FUNCTION)) return true;
		}
		//cop.incOperandNumber();
		
		return true;
	}
	
	public void push(OperatorSymbMan osm) throws ManagedException {

		if(osm.type().equals(OSMType.REGULAR)) {
			if(opStack.size() > 0) {
				XPComputedOSM coprt = opStack.peek();
				
				OperatorSymbMan topOsm = coprt.item();
				
				if(topOsm == osm && osm.canCumulateOperands()) {
					coprt.incExpectedOperandNumber();
					return;
				}
				
				while(opStack.size() > 0) {
					XPComputedOSM cop = opStack.peek();
					OperatorSymbMan osm2 = cop.item();
					boolean ltr = osm.associativity().equals(OSMAssociativity.LEFT_TO_RIGHT) && (osm.priority() >= osm2.priority());
					boolean rtl =osm.associativity().equals(OSMAssociativity.RIGHT_TO_LEFT) && (osm.priority() < osm2.priority());
					
					if(!ltr && !rtl) break;
					
					popOperatorToOutput();
				} 
			}
			_push(osm);
			return;
		}
		
		if(osm.type().equals(OSMType.FUNCTION)) {
			_push(osm);
			return;
		}
		
		if(osm.type().equals(OSMType.PARAMS_SEPARATOR)) {
			
			while(opStack.size() > 0) {
				XPComputedOSM cop = opStack.peek();
				if(cop.item().type().equals(OSMType.OPEN_PARENTHESIS)) break;
				
				popOperatorToOutput();
			}
			
			return;
		}
		
		if(osm.type().equals(OSMType.OPEN_PARENTHESIS)) {
			_push(osm);
			return;
		}
		
		if(osm.type().equals(OSMType.CLOSED_PARENTHESIS)) {
			while(opStack.size() > 0) {
				XPComputedOSM cop = opStack.peek();
				if(cop.item().type().equals(OSMType.OPEN_PARENTHESIS)) {
					opStack.pop();
					break;
				}
				
				popOperatorToOutput();
				
			}
			
			XPComputedOSM cop = topOSM();
			
			if(cop == null) return;
			
			OperatorSymbMan osm2 = cop.item();
			if(osm2.type().equals(OSMType.FUNCTION)) {
				popOperatorToOutput();
				return;
			}
			
			//if(cop.expectOperand()) cop.incOperandNumber();
			if(osm2.symbol().equals(".")) popOperatorToOutput();
			 
			
			return;
		}
		
	}

	@Override
	public ComputedItem<XPression<?>, XPEvaluator> getStackOperand(int indexFormTop) {
		//return outputStack.get(outputStack.size() - 1 - indexFormTop);
		
		return null;
	}
	
	public XPComputedItem<XPression<?>> stackOperand(int indexFormTop) {
		if(outputStack.size() == 0) return null;
		return outputStack.get(outputStack.size() - 1 - indexFormTop);
	}
	
	
	public XPOperand<?> compute() throws ManagedException {
		XPComputedOSM cop;
		
		while(opStack.size() > 0) {
			cop = opStack.pop();
			//XPOperator<?> op = cop.item().operatorOf(this, cop.order, cop.nbOperands());
			nbFreeOperand -= cop.nbExpectedNbOperand();
			XPOperator<?> op = cop.item().operatorOf(this, cop.order, cop.nbExpectedNbOperand());
			op.resolve(this, cop.order(), cop.nbExpectedNbOperand());
			if(opStack.size()>0) {
				cop = opStack.peek();
				//if(cop.expectOperand()) cop.incOperandNumber();
			}
		}
		
		XPComputedItem<XPression<?>> coprd = outputStack.peek();
		
		XPression<?> item = coprd.item();
		if(item.asOperand() == null) {
			outputStack.pop();
			item.asOperator().resolve(this, coprd.order(), coprd.asComputedOperator().nbOperands());
		}
		
		return outputStack.get(0).item().asOperand();
	}
	
	public void clear() {
		outputStack.clear();
		opStack.clear();
		order = 0;
		nbFreeOperand = 0;
	}

	public boolean popOperator() {
		if(opStack.size() == 0) return false;
		opStack.pop();
		
		return true;
	}
	
}
