package com.exa.expression.eval;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.exa.eva.ComputedItem;
import com.exa.eva.ComputedOperator;
import com.exa.eva.ComputedOperatorManager;
import com.exa.eva.OperatorManager.OMAssociativity;
import com.exa.eva.OperatorManager.OMOperandType;
import com.exa.eva.OperatorManager.OMType;
import com.exa.eva.StackEvaluator;
import com.exa.expression.OM;
import com.exa.expression.OMClosedParenthesis;
import com.exa.expression.OMFunction;
import com.exa.expression.OMOpenParenthesis;
import com.exa.expression.OMParamSeparator;
import com.exa.expression.Variable;
import com.exa.expression.VariableContext;
import com.exa.expression.XPConstant;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperator;
import com.exa.expression.XPression;
import com.exa.utils.ManagedException;

public class XPEvaluator implements StackEvaluator<XPression<?>, XPOperand<?>, XPOperator<?>, XPEvaluator, OM> {
	public static final OMOpenParenthesis OP_OPEN_PARENTHESIS = new OMOpenParenthesis("(", 100);
	public static final OMClosedParenthesis OP_CLOSED_PARENTHESIS = new OMClosedParenthesis(")");
	public static final OMParamSeparator OP_PARAMS_SEPARATOR = new OMParamSeparator(",");
	
	public final static XPConstant<Boolean> TRUE = new XPConstant<>(Boolean.TRUE);
	public final static XPConstant<Boolean> FALSE = new XPConstant<>(Boolean.FALSE);
	
	protected Stack<ComputedItem<XPression<?>, XPression<?>, OM>> outputStack = new Stack<>();
	protected Stack<ComputedOperatorManager<OM, XPression<?>>> opStack = new Stack<>();
	
	private int order = 0;
	
	protected Map<String, OM> osmsPreUnary = new HashMap<>();
	protected Map<String, OM> osmsPostUnary = new HashMap<>();
	
	protected Map<String, OM> osmsBinary = new HashMap<>();
	
	protected Map<String, OMFunction<?>> osmsFunctions = new HashMap<>();
	
	//private VariableContext variableContext;
	private String defaultVariableContext = "_global";
	
	

	private Map<String, VariableContext> variablesContexts = new HashMap<>();

	private int nbFreeOperand = 0;
	
	public XPEvaluator(VariableContext variableContext) {
		super();
		variablesContexts.put(defaultVariableContext, variableContext);
	}
	
	public XPEvaluator() {
		this(new MapVariableContext());
	}
	
	public void addPreUnaryOp(OM osm) {
		osmsPreUnary.put(osm.symbol(), osm);
	}
	
	public void addPostUnaryOp(OM osm) {
		osmsPostUnary.put(osm.symbol(), osm);
	}
	
	public void addBinaryOp(OM osm) {
		osmsBinary.put(osm.symbol(), osm);
	}
	
	public void addFunction(OMFunction<?> osm) {
		osmsFunctions.put(osm.symbol(), osm);
	}
	
	public OM getPreUnaryOp(String symbol) {
		return osmsPreUnary.get(symbol);
	}
	
	public OM getPostUnaryOp(String symbol) {
		return osmsPostUnary.get(symbol);
	}
	
	public OM getBinaryOp(String symbol) {
		return osmsBinary.get(symbol);
	}
	
	public OMFunction<?> getFunction(String symbol) {
		return osmsFunctions.get(symbol);
	}
	
	public void addVariableContext(VariableContext vc, String name, String bindTo) throws ManagedException {
		if(variablesContexts.containsKey(name)) throw new ManagedException(String.format("The variable context %s already exists", name));
		 
		VariableContext parent = variablesContexts.get(bindTo);
		if(parent == null) throw new ManagedException(String.format("The variable context %s does not exist", bindTo));
		 
		vc.setParent(parent);
		
		variablesContexts.put(name, vc);
	}
	
	public VariableContext getVariableContext(String name) {
		return variablesContexts.get(name);
	}
	
	public void addVariable(String context, String name, Class<?> valueClass, Object defaultValue) throws ManagedException {
		VariableContext vc = variablesContexts.get(context);
		if(vc == null) throw new ManagedException(String.format("The variable context %s is not defined", context));
		vc.addVariable(name, valueClass, defaultValue);
	}
	
	public void addVariable(String name, Class<?> valueClass, Object defaultValue) throws ManagedException {
		addVariable(defaultVariableContext, name, valueClass, defaultValue);
	}
	
	
	
	public void clearVariables() {
		
	}
	
	public Variable<?> getVariable(String name, String context) throws ManagedException {
		VariableContext vc = variablesContexts.get(context);
		if(vc == null) throw new ManagedException(String.format("The variable context %s is not defined", context));
		Variable<?> var = vc.getVariable(name);
		
		if(var == null) return null;
		
		return var;
	}
	
	public Variable<?> getVariable(String name) throws ManagedException {
		return getVariable(defaultVariableContext, name);
	}

	@Override
	public int numberOfOperands() {
		return outputStack.size();
	}
	
	@Override
	public void push(XPOperand<?> item) {
		if(opStack.size() > 0) {
			ComputedOperatorManager<OM, XPression<?>> coprt = opStack.peek();
			
			if(opStack.size() > 1) {
				if(coprt.item() == OP_OPEN_PARENTHESIS) {
					ComputedOperatorManager<OM, XPression<?>> coprt2 = opStack.get(opStack.size()-2);
					if(coprt2.item().type().equals(OMType.FUNCTION)) {
						//if(coprt2.expectOperand()) coprt2.incOperandNumber();
					}
					outputStack.push(new ComputedItem<>(item, order++));
					++nbFreeOperand;
					return;
				}
			}
			
			//if(coprt.expectOperand()) coprt.incOperandNumber();
			
		}
		++nbFreeOperand;
		outputStack.push(new ComputedItem<>(item, order++));
	}
	
	private void _push(OM osm) {
		OMOperandType otype = osm.operandType();
		if(otype == null) {
			opStack.push(new ComputedOperatorManager<>(osm, order++, 1));
			return;
		}
		
		if(otype.equals(OMOperandType.POST_OPERAND)) {
			opStack.push(new ComputedOperatorManager<>(osm, order++, 1));
			return;
		}
	
		if(otype.equals(OMOperandType.PRE_OPERAND)) {
			opStack.push(new ComputedOperatorManager<>(osm, order++, 0));
			return;
		}
		
		opStack.push(new ComputedOperatorManager<>(osm, order++, 1));
	}
	
	/*public boolean popOperatorToOutput() throws ManagedException {
		XPComputedOSM cop = opStack.pop();
		if(cop == null) return false;
		
		nbFreeOperand -= cop.nbExpectedNbOperands();
		
		XPOperator<?> op = cop.item().operatorOf(this, cop.order, cop.nbExpectedNbOperands());
		
		outputStack.push(new XPComputedOperator(op, cop.order(), cop.nbExpectedNbOperands()));
		
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
	*/
	
	/*public void push(OperatorSymbMan osm) throws ManagedException {

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
	*/

	/*
	@Override
	public ComputedItem<XPression<?>, XPEvaluator> getStackOperand(int indexFormTop) {
		//return outputStack.get(outputStack.size() - 1 - indexFormTop);
		
		return null;
	}
	*/
	
	/*public XPComputedItem<XPression<?>> stackOperand(int indexFormTop) {
		if(outputStack.size() == 0) return null;
		return outputStack.get(outputStack.size() - 1 - indexFormTop);
	}
	*/
	
	
	public XPOperand<?> compute() throws ManagedException {
		ComputedOperatorManager<OM, XPression<?>> cop;
		
		while(opStack.size() > 0) {
			cop = opStack.pop();
			
			nbFreeOperand -= cop.nbExpectedNbOperands();
			XPOperator<?> op = cop.item().operatorOf(this, cop.order(), cop.nbExpectedNbOperands());
			op.resolve(this, cop.order(), cop.nbExpectedNbOperands());
			if(opStack.size()>0) {
				cop = opStack.peek();
				
			}
		}
		
		ComputedItem<XPression<?>, XPression<?>, OM> coprd = outputStack.peek();
		
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


	@Override
	public ComputedItem<XPression<?>, XPression<?>, OM> stackOperand(int indexFormTop) {
		if(outputStack.size() == 0) return null;
		return outputStack.get(outputStack.size() - 1 - indexFormTop);
	}

	@Override
	public ComputedOperatorManager<OM, XPression<?>> topOperatorManager() {
		if(opStack.size() == 0) return null;

		return opStack.peek();
	}

	@Override
	public void push(OM om) throws ManagedException {
		if(om.type().equals(OMType.REGULAR)) {
			if(opStack.size() > 0) {
				ComputedOperatorManager<OM, XPression<?>> coprt = opStack.peek();
				
				OM topOm = coprt.item();
				
				if(topOm == om && om.canCumulateOperands()) {
					coprt.incExpectedOperandNumber();
					return;
				}
				
				while(opStack.size() > 0) {
					ComputedOperatorManager<OM, XPression<?>> cop = opStack.peek();
					OM osm2 = cop.item();
					boolean ltr = om.associativity().equals(OMAssociativity.LEFT_TO_RIGHT) && (om.priority() >= osm2.priority());
					boolean rtl =om.associativity().equals(OMAssociativity.RIGHT_TO_LEFT) && (om.priority() < osm2.priority());
					
					if(!ltr && !rtl) break;
					
					popOperatorInOperandStack();
				} 
			}
			_push(om);
			return;
		}
		
		if(om.type().equals(OMType.FUNCTION)) {
			_push(om);
			return;
		}
		
		if(om.type().equals(OMType.PARAMS_SEPARATOR)) {
			
			while(opStack.size() > 0) {
				ComputedOperatorManager<OM, XPression<?>> cop = opStack.peek();
				if(cop.item().type().equals(OMType.OPEN_PARENTHESIS)) break;
				
				popOperatorInOperandStack();
			}
			
			return;
		}
		
		if(om.type().equals(OMType.OPEN_PARENTHESIS)) {
			_push(om);
			return;
		}
		
		if(om.type().equals(OMType.CLOSED_PARENTHESIS)) {
			while(opStack.size() > 0) {
				ComputedOperatorManager<OM, XPression<?>> cop = opStack.peek();
				if(cop.item().type().equals(OMType.OPEN_PARENTHESIS)) {
					opStack.pop();
					break;
				}
				
				popOperatorInOperandStack();
				
			}
			
			ComputedOperatorManager<OM, XPression<?>> cop = topOperatorManager();
			
			if(cop == null) return;
			
			OM osm2 = cop.item();
			if(osm2.type().equals(OMType.FUNCTION)) {
				popOperatorInOperandStack();
				return;
			}
			
			//if(cop.expectOperand()) cop.incOperandNumber();
			if(osm2.symbol().equals(".")) popOperatorInOperandStack();
			 
			
			return;
		}
		
	}

	@Override
	public ComputedItem<XPression<?>, XPression<?>, OM> popOperand() {
		if(outputStack.size() == 0) return null;
		return outputStack.pop();
	}

	@Override
	public boolean popOperatorInOperandStack() throws ManagedException {
		ComputedOperatorManager<OM, XPression<?>> cop = opStack.pop();
		if(cop == null) return false;
		
		nbFreeOperand -= cop.nbExpectedNbOperands();
		
		XPOperator<?> op = cop.item().operatorOf(this, cop.order(), cop.nbExpectedNbOperands());
		
		outputStack.push(new ComputedOperator<XPression<?>, OM>(op, cop.order(), cop.nbExpectedNbOperands()));
		
		nbFreeOperand += 1;
		
		if(opStack.size() == 0) return true;
		
		cop = opStack.peek();
		
		if(cop.item() == OP_OPEN_PARENTHESIS) {
			if(opStack.size() == 1) return true;
			
			cop = opStack.get(opStack.size() - 2);
			
			if(!cop.item().type().equals(OMType.FUNCTION)) return true;
		}
		//cop.incOperandNumber();
		
		return true;
	}
	
	public String getDefaultVariableContext() {
		return defaultVariableContext;
	}

	public void setDefaultVariableContext(String defaultVariableContext) {
		this.defaultVariableContext = defaultVariableContext;
	}
	
	
	
}
