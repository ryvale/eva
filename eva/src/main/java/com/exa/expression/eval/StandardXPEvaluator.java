package com.exa.expression.eval;

import com.exa.expression.OMBinary;
import com.exa.expression.OMFunction;
import com.exa.expression.VariableContext;
import com.exa.expression.XPOperator;
import com.exa.expression.eval.XPEvaluator;
import com.exa.expression.eval.functions.XPFunctSubstr;
import com.exa.expression.eval.operators.OMCumulableBinary;
import com.exa.expression.eval.operators.OMDifferent;
import com.exa.expression.eval.operators.OMEqualTo;
import com.exa.expression.eval.operators.OMTernary;
import com.exa.expression.eval.operators.XPOprtConcatenation;
import com.exa.expression.eval.operators.XPOprtCummulableBinary;
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
		
		OMBinary<XPOprtCummulableBinary<?>> ombc = new OMCumulableBinary("+", 6);
		ombc.addOperator(new XPOprtConcatenation("+"));
		ombc.addOperator(new XPOprtIntAdd("+"));
		ombc.addOperator(new XPOprtDblAdd("+"));
		addBinaryOM(ombc);
		
		ombc = new OMCumulableBinary("-", 6);
		ombc.addOperator(new XPOprtIntSubstract("-"));
		ombc.addOperator(new XPOprtDblSubstract("-"));
		addBinaryOM(ombc);
		
		ombc = new OMCumulableBinary("*", 5);
		ombc.addOperator(new XPOprtIntMultiply("*"));
		ombc.addOperator(new XPOprtDblMultiply("*"));
		addBinaryOM(ombc);
		
		ombc = new OMCumulableBinary("/", 5);
		ombc.addOperator(new XPOprtIntDiv("/"));
		ombc.addOperator(new XPOprtDblDiv("/"));
		addBinaryOM(ombc);
		
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
		
		addBinaryOM(omBinaryMemberAcces);
		
		OMFunction<XPOperator<String>> osmf = new OMFunction<>("substr", 3);
		
		osmf.addOperator(new XPFunctSubstr("substr", 3));
		
		addFunction(osmf);
		
		addBinaryOM(new OMEqualTo("==", 10));
		addBinaryOM(new OMDifferent("!=", 10));
		
		addBinaryOM(new OMTernary("?", 16));
	}
	
	
}
