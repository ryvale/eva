package com.exa.expression.eval;

import com.exa.expression.OMBinary;
import com.exa.expression.OMFunction;
import com.exa.expression.VariableContext;
import com.exa.expression.XPOperator;
import com.exa.expression.eval.XPEvaluator;
import com.exa.expression.eval.functions.XPFunctSubstr;
import com.exa.expression.eval.operators.XPOprtConcatenation;
import com.exa.expression.eval.operators.XPOprtDblAdd;
import com.exa.expression.eval.operators.XPOprtDblDiv;
import com.exa.expression.eval.operators.XPOprtDblMultiply;
import com.exa.expression.eval.operators.XPOprtDblSubstract;
import com.exa.expression.eval.operators.XPOprtIntAdd;
import com.exa.expression.eval.operators.XPOprtIntDiv;
import com.exa.expression.eval.operators.XPOprtIntMultiply;
import com.exa.expression.eval.operators.XPOprtIntSubstract;
import com.exa.expression.eval.operators.XPOprtMemberAccess;
import com.exa.expression.eval.operators.XPOprtStaticMemberAccess;

public class StandardXPEvaluator extends XPEvaluator {

	public StandardXPEvaluator() {
		this(new MapVariableContext());
	}

	public StandardXPEvaluator(VariableContext variableContext) {
		super(variableContext);
		
		OMBinary osmm = new OMBinary("+", 6);
		osmm.addOperator(new XPOprtConcatenation("+"));
		osmm.addOperator(new XPOprtIntAdd("+"));
		osmm.addOperator(new XPOprtDblAdd("+"));
		addBinaryOSM(osmm);
		
		osmm = new OMBinary("-", 6);
		osmm.addOperator(new XPOprtIntSubstract("-"));
		osmm.addOperator(new XPOprtDblSubstract("-"));
		addBinaryOSM(osmm);
		
		osmm = new OMBinary("*", 5);
		osmm.addOperator(new XPOprtIntMultiply("*"));
		osmm.addOperator(new XPOprtDblMultiply("*"));
		addBinaryOSM(osmm);
		
		osmm = new OMBinary("/", 5);
		osmm.addOperator(new XPOprtIntDiv("/"));
		osmm.addOperator(new XPOprtDblDiv("/"));
		addBinaryOSM(osmm);
		
		//osmm = new OMBinary(".", 2);
		omBinaryMemberAcces.addOperator(new XPOprtMemberAccess<>(".", ClassesMan.T_STRING));
		omBinaryMemberAcces.addOperator(new XPOprtMemberAccess<>(".", ClassesMan.T_INTEGER));
		omBinaryMemberAcces.addOperator(new XPOprtMemberAccess<>(".", ClassesMan.T_BOOLEAN));
		omBinaryMemberAcces.addOperator(new XPOprtMemberAccess<>(".", ClassesMan.T_DOUBLE));
		omBinaryMemberAcces.addOperator(new XPOprtMemberAccess<>(".", ClassesMan.T_DATE));
		
		omBinaryMemberAcces.addOperator(new XPOprtStaticMemberAccess<>(".", ClassesMan.T_STRING));
		omBinaryMemberAcces.addOperator(new XPOprtStaticMemberAccess<>(".", ClassesMan.T_INTEGER));
		omBinaryMemberAcces.addOperator(new XPOprtStaticMemberAccess<>(".", ClassesMan.T_BOOLEAN));
		omBinaryMemberAcces.addOperator(new XPOprtStaticMemberAccess<>(".", ClassesMan.T_DOUBLE));
		omBinaryMemberAcces.addOperator(new XPOprtStaticMemberAccess<>(".", ClassesMan.T_DATE));
		//osmm.addOperator(new XPOprtMemberAccess<>(".", Type.OBJECT));
		
		addBinaryOSM(omBinaryMemberAcces);
		
		OMFunction<XPOperator<String>> osmf = new OMFunction<>("substr", 3);
		
		osmf.addOperator(new XPFunctSubstr("substr", 3));
		
		addFunction(osmf);
	}
	
	
}
