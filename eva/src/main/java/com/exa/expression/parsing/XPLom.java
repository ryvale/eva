package com.exa.expression.parsing;

import com.exa.buffer.CharReader;
import com.exa.expression.XPConstant;
import com.exa.expression.XPOperand;
import com.exa.expression.XPOperator;
import com.exa.lexing.LexerOperationMan;
import com.exa.utils.ManagedException;

public interface XPLom extends LexerOperationMan<XPLexingRules> {
	void pushOperand(XPOperand<?> oprd);
	
	void push(XPOperator<?> oprt);
	
	void pushOperator(String oprtStr);
	
	XPConstant<? extends Number> readNumeric(CharReader cr) throws ManagedException;
	
}
