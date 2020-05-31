package com.exa.expression.eval;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.exa.eva.ComputedItem;
import com.exa.eva.ComputedOperator;
import com.exa.eva.ComputedOperatorManager;
import com.exa.eva.OperatorManager.OMAssociativity;
import com.exa.eva.OperatorManager.OMOperandType;
import com.exa.eva.OperatorManager.OMType;
import com.exa.eva.StackEvaluator;
import com.exa.expression.OM;
import com.exa.expression.OMBinary;
import com.exa.expression.OMClosedParenthesis;
import com.exa.expression.OMFunction;
import com.exa.expression.OMOpenParenthesis;
import com.exa.expression.OMOperandSeparator;
import com.exa.expression.OMParamSeparator;
import com.exa.expression.Variable;
import com.exa.expression.VariableContext;
import com.exa.expression.XPConstant;
import com.exa.expression.XPNull;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperator;
import com.exa.expression.XPression;
import com.exa.expression.eval.operators.OMCumulableBinary;
import com.exa.expression.eval.operators.XPOprtCummulableBinary;
import com.exa.utils.ManagedException;

public class XPEvaluator implements StackEvaluator<XPression<?>, XPOperand<?>, XPOperator<?>, XPEvaluator, OM> {
		
	public static final OMOpenParenthesis OP_OPEN_PARENTHESIS = new OMOpenParenthesis("(", 100);
	public static final OMClosedParenthesis OP_CLOSED_PARENTHESIS = new OMClosedParenthesis(")");
	public static final OMParamSeparator OP_PARAMS_SEPARATOR = new OMParamSeparator(",");
	public static final OMOperandSeparator OP_OPERAND_SEPARATOR = new OMOperandSeparator(":");
	
	public final static XPConstant<Boolean> TRUE = new XPConstant<>(Boolean.TRUE);
	public final static XPConstant<Boolean> FALSE = new XPConstant<>(Boolean.FALSE);
	
	public final static XPNull<?> NULL = new XPNull<>();
	
	protected Stack<ComputedItem<XPression<?>, XPression<?>, OM>> outputStack = new Stack<>();
	protected Stack<ComputedOperatorManager<OM, XPression<?>>> opStack = new Stack<>();
	
	private int order = 0;
	
	protected Map<String, OM> osmsPreUnary = new HashMap<>();
	protected Map<String, OM> osmsPostUnary = new HashMap<>();
	
	protected Map<String, OM> osmsBinary = new HashMap<>();
	
	protected Map<String, OMFunction<?>> osmsFunctions = new HashMap<>();
	
	private int nbFreeOperand = 0;
	
	private XPEClassesMan classesMan;
	
	private Stack<VariableContext> vcStack = new Stack<>();
	
	private VariableContext rootVC;
	
	protected OMBinary<XPOprtCummulableBinary<?>> omBinaryMemberAcces = new OMCumulableBinary(".", 2);
	
	private Map<String, Set<VariableContext>> registeredVariableContexts = new HashMap<>();
	
	
	public XPEvaluator(VariableContext rootVC/*, ContextResolver contextResolver*/) {
		super();
		//variablesContexts.put(defaultVariableContext, variableContext);
		
		this.rootVC = rootVC;
		
		vcStack.push(rootVC);
		//this.currentVariableContext = rootVC;
		this.classesMan = new XPEClassesMan(this);
		
		//this.contextResolver = contextResolver;
		
		addBinaryOM(omBinaryMemberAcces);
	}
	
	public XPEvaluator() {
		this(new MapVariableContext()/*, CR_DEFAULT*/);
	}
	
	public VariableContext getCurrentVariableContext() {
		return vcStack.peek();
	}
	
	public void pushVariableContext(VariableContext vc) {
		vcStack.push(vc);
	}
	
	public VariableContext popVariableContext() {
		return vcStack.pop();
	}

	public ClassesMan classesMan() { return classesMan; }
	
	public int decNbFreeOperand() { return --nbFreeOperand; }
	
	/*public boolean contextExists(String name) {
		return variablesContexts.containsKey(name);
	}*/
	
	public void addPreUnaryOp(OM osm) {
		osmsPreUnary.put(osm.symbol(), osm);
	}
	
	public void addPostUnaryOp(OM osm) {
		osmsPostUnary.put(osm.symbol(), osm);
	}
	
	public void addBinaryOM(OM osm) {
		osmsBinary.put(osm.symbol(), osm);
	}
	
	public void addMemberAccessOprt(XPOprtCummulableBinary<?> oprt) {
		omBinaryMemberAcces.addOperator(oprt);
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
	
	/*public void addVariableContext(VariableContext vc, String name, String bindTo) throws ManagedException {
		if(variablesContexts.containsKey(name)) throw new ManagedException(String.format("The variable context %s already exists", name));
		 
		VariableContext parent = variablesContexts.get(bindTo);
		if(parent == null) throw new ManagedException(String.format("The variable context %s does not exist", bindTo));
		 
		vc.setParent(parent);
		
		variablesContexts.put(name, vc);
	}*/
	
	/*public VariableContext getVariableContext(String name) {
		return variablesContexts.get(name);
	}
	
	public void assignVariable(String context, String name, Object value) throws ManagedException {
		/*VariableContext vc = variablesContexts.get(context);
		if(vc == null) throw new ManagedException(String.format("The variable context %s is not defined", context));
		
		vc.assignVariable(name, value);
		
	}*/
	
	public void assignVariable(String name, Object value) throws ManagedException {
		
		vcStack.peek().assignVariable(name, value);
	}
	
	public void assignOrDeclareVariable(String name, Class<?> valueClass, Object value) {
		vcStack.peek().assignOrDeclareVariable(name, valueClass, value);
	}
	
	public void addVariable(String name, Class<?> valueClass, Object defaultValue) throws ManagedException {
		vcStack.peek().addVariable(name, valueClass, defaultValue);
	}
	
	public Variable<?> getVariable(String name) throws ManagedException {
		return vcStack.peek().getVariable(name);
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
					if(coprt2.item().type().equals(OMType.FUNCTION) || coprt2.item().type().equals(OMType.METHOD)) {
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
		if(om.type().equals(OMType.REGULAR) || om.type().equals(OMType.REGULAR_OPERAND_START)) {
			if(opStack.size() > 0) {
				ComputedOperatorManager<OM, XPression<?>> coprt = opStack.peek();
				
				OM topOm = coprt.item();
				
				if(topOm == om && om.canCumulateOperands()) {
					coprt.incExpectedOperandNumber();
					return;
				}
				
				while(opStack.size() > 0) {
					ComputedOperatorManager<OM, XPression<?>> cop = opStack.peek();
					OM actualTopOM = cop.item();
					if(actualTopOM.type().equals(OMType.PARAMS_START)) break;
					
					boolean c1 = actualTopOM.priority() < om.priority();
					boolean c2 = (actualTopOM.priority() == om.priority()) && om.associativity().equals(OMAssociativity.LEFT_TO_RIGHT);
					
					//boolean ltr = om.associativity().equals(OMAssociativity.LEFT_TO_RIGHT) && (om.priority() >= actualTopOM.priority());
					//boolean rtl =om.associativity().equals(OMAssociativity.RIGHT_TO_LEFT) && (om.priority() < actualTopOM.priority());
					
					if(!c1 && !c2) break;
					
					//if(!ltr && !rtl) break;
					
					popOperatorInOperandStack();
				} 
			}
			_push(om);
			return;
		}
		
		if(om.type().equals(OMType.FUNCTION) || om.type().equals(OMType.METHOD)) {
			_push(om);
			return;
		}
		
		if(om.type().equals(OMType.PARAMS_SEPARATOR)) {
			
			while(opStack.size() > 0) {
				ComputedOperatorManager<OM, XPression<?>> cop = opStack.peek();
				if(cop.item().type().equals(OMType.PARAMS_START)) break;
				
				popOperatorInOperandStack();
			}
			
			return;
		}
		
		if(om.type().equals(OMType.OPERAND_SEPARATOR)) {
			
			while(opStack.size() > 0) {
				ComputedOperatorManager<OM, XPression<?>> cop = opStack.peek();
				if(cop.item().type().equals(OMType.REGULAR_OPERAND_START)) break;
				
				popOperatorInOperandStack();
			}
			
			return;
		}
		
		if(om.type().equals(OMType.PARAMS_START)) {
			_push(om);
			return;
		}
		
		if(om.type().equals(OMType.PARAMS_END)) {
			while(opStack.size() > 0) {
				ComputedOperatorManager<OM, XPression<?>> cop = opStack.peek();
				if(cop.item().type().equals(OMType.PARAMS_START)) {
					opStack.pop();
					break;
				}
				
				popOperatorInOperandStack();
				
			}
			
			ComputedOperatorManager<OM, XPression<?>> cop = topOperatorManager();
			
			if(cop == null) return;
			
			OM osm2 = cop.item();
			if(osm2.type().equals(OMType.FUNCTION) || osm2.type().equals(OMType.METHOD)) {
				popOperatorInOperandStack();
				return;
			}
			
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
			
			if(!cop.item().type().equals(OMType.FUNCTION) && !cop.item().type().equals(OMType.METHOD)) return true;
		}
		//cop.incOperandNumber();
		
		return true;
	}
	
	public XPEClassesMan getClassesMan() {
		return classesMan;
	}

	public VariableContext getRootVC() {
		return rootVC;
	}
	
	public void registerVariableContext(String category, VariableContext vc) {
		Set<VariableContext> catVcs = registeredVariableContexts.get(category);
		if(catVcs == null) {
			catVcs = new HashSet<>();
			registeredVariableContexts.put(category, catVcs);
		}
		catVcs.add(vc);
		
	}
	
	public void unregisterVariableContext(String category, VariableContext vc) {
		Set<VariableContext> catVcs = registeredVariableContexts.get(category);
		if(catVcs == null) return;
		
		catVcs.remove(vc);
	}
	
	public Set<VariableContext> getRegisteredVariableContexts(String category) {
		return registeredVariableContexts.get(category);
	}
	
}
