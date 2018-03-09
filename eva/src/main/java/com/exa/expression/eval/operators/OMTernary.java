package com.exa.expression.eval.operators;

import java.util.Date;

import com.exa.expression.OM;
import com.exa.expression.Type;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperandBase;
import com.exa.expression.XPOperator;
import com.exa.expression.XPOperatorBase;
import com.exa.expression.eval.ClassesMan;
import com.exa.expression.eval.XPEvaluator;
import com.exa.hardcore.Pointer;
import com.exa.utils.ManagedException;

public class OMTernary extends OM {
	
	class ResultOPerand<T> extends XPOperandBase<T> {
		private XPOperand<Boolean> xpCondition;
		private XPOperand<?> xpThen;
		private XPOperand<?> xpElse;
		
		private Type<?> type;
		
		ResultOPerand(Type<?> type, XPOperand<Boolean> xpCondition, XPOperand<?> xpThen, XPOperand<?> xpElse) {
			super();
			this.type = type;
			this.xpCondition = xpCondition;
			this.xpThen = xpThen;
			this.xpElse = xpElse;
		}

		@Override
		public T value(XPEvaluator evaluator) throws ManagedException {
			Type<T> t = type.specificType();
			
			return xpCondition.value(evaluator) ? t.convert(xpThen.value(evaluator)) : t.convert(xpElse.value(evaluator));
		}

		@Override
		public Type<?> type() {
			return type;
		}

		@Override
		public XPOperand<String> asOPString() {
			if(type == ClassesMan.T_STRING) return new ResultOPerand<>(type, xpCondition, xpThen, xpElse);
			return super.asOPString();
		}

		@Override
		public XPOperand<Date> asOPDate() {
			if(type == ClassesMan.T_DATE) return new ResultOPerand<>(type, xpCondition, xpThen, xpElse);
			return super.asOPDate();
		}

		@Override
		public XPOperand<Integer> asOPInteger() {
			if(type == ClassesMan.T_INTEGER) return new ResultOPerand<>(type, xpCondition, xpThen, xpElse);
			return super.asOPInteger();
		}

		@Override
		public XPOperand<Boolean> asOPBoolean() {
			if(type == ClassesMan.T_BOOLEAN) return new ResultOPerand<>(type, xpCondition, xpThen, xpElse);
			return super.asOPBoolean();
		}

		@Override
		public XPOperand<Double> asOPDouble() {
			if(type == ClassesMan.T_DOUBLE) return new ResultOPerand<>(type, xpCondition, xpThen, xpElse);
			return super.asOPDouble();
		}
		
		
		
	}

	private Integer priority;
	private String symbol;
	
	public OMTernary(String symbol, Integer priority) {
		super();
		this.priority = priority;
		this.symbol = symbol;
	}

	@Override
	public String symbol() {
		return symbol;
	}

	@Override
	public XPOperator<?> operatorOf(XPEvaluator eval, int order, int nbOperands) throws ManagedException {
		Pointer<Type<?>> tp1 = new Pointer<>();
		int nb = operandAction(eval, 0, (xp) -> {
			tp1.deref(xp.type());
			
			return true;
		});

		
		Pointer<Type<?>> tp2 = new Pointer<>();
		nb = operandAction(eval, nb, (xp) -> {
			tp2.deref(xp.type());
			
			return true;
		});

		
		nb = operandAction(eval, nb, (xp) ->  xp.type() == ClassesMan.T_BOOLEAN);
		
		if(nb == - 1) return null;
		
		Type<?> type;
		
		if(tp1.deref().canBeComputedBy(tp2.deref())) type = tp2.deref();
		else if(tp2.deref().canBeComputedBy(tp1.deref())) type = tp1.deref();
		else type = ClassesMan.T_OBJECT;
		
		return new XPOperatorWithResolver<>(symbol, 3, type, (e, o, n) -> {
			XPOperand<?> xpElse = XPOperatorBase.resolveOperand(e);
			
			XPOperand<?> xpThen = XPOperatorBase.resolveOperand(e);
			
			XPOperand<?> oprdCondition = XPOperatorBase.resolveOperand(e);
			
			XPOperand<Boolean> xpCondition = oprdCondition.asOPBoolean();
			if(xpCondition == null) throw new ManagedException(String.format("Boolean expression expected in ternary operator"));
			
			return new ResultOPerand<>(type, xpCondition, xpThen, xpElse);
		});
	}

	@Override
	public Integer priority() {
		return priority;
	}

	@Override
	public int nbOperands() {
		return 3;
	}

	@Override
	public OMOperandType operandType() {
		return OMOperandType.POST_OPERAND;
	}

	@Override
	public OMAssociativity associativity() {
		return OMAssociativity.RIGHT_TO_LEFT;
	}

	@Override
	public OMType type() {
		return OMType.REGULAR_OPERAND_START;
	}

}
