package com.exa.expression.eval;

import com.exa.expression.OMBinary;
import com.exa.expression.TypeMan;
import com.exa.expression.VariableContext;
import com.exa.expression.eval.XPEvaluator;
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
		addBinaryOp(osmm);
		
		osmm = new OMBinary("-", 6);
		osmm.addOperator(new XPOprtIntSubstract("-"));
		osmm.addOperator(new XPOprtDblSubstract("-"));
		addBinaryOp(osmm);
		
		osmm = new OMBinary("*", 5);
		osmm.addOperator(new XPOprtIntMultiply("*"));
		osmm.addOperator(new XPOprtDblMultiply("*"));
		addBinaryOp(osmm);
		
		osmm = new OMBinary("/", 5);
		osmm.addOperator(new XPOprtIntDiv("/"));
		osmm.addOperator(new XPOprtDblDiv("/"));
		addBinaryOp(osmm);
		
		osmm = new OMBinary(".", 2);
		osmm.addOperator(new XPOprtMemberAccess<>(".", TypeMan.STRING));
		osmm.addOperator(new XPOprtMemberAccess<>(".", TypeMan.INTEGER));
		osmm.addOperator(new XPOprtMemberAccess<>(".", TypeMan.BOOLEAN));
		osmm.addOperator(new XPOprtMemberAccess<>(".", TypeMan.DOUBLE));
		osmm.addOperator(new XPOprtMemberAccess<>(".", TypeMan.DATE));
		osmm.addOperator(new XPOprtMemberAccess<>(".", TypeMan.OBJECT));
		
		addBinaryOp(osmm);
		
		/*OMFunction<XPOperator<String>> osmf = new OMFunction<>("substr", 3);
		
		osmf.addOperator(new XPFunctSubstr("substr", 3));
		
		addFunction(osmf);*/
	}
	
	
}
