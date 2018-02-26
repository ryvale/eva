package com.exa.lexing;

import com.exa.buffer.CharReader;
import com.exa.utils.ManagedException;

public interface LexerOperationMan<T extends LexingRules> {
	
	T lexingRules();
	
	boolean readOperand(CharReader cr) throws ManagedException;
	
	void deliver(String str);
}
