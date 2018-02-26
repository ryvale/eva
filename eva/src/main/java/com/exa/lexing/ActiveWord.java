package com.exa.lexing;

import com.exa.buffer.CharReader;
import com.exa.utils.ManagedException;

public class ActiveWord {
	public final static ActiveWord BLANK_WORD = new BlankWord();
	
	protected LexingRules lexingRules;
	protected String keyword;
	
	public ActiveWord(String keyword, LexingRules lexingRules) {
		this.keyword = keyword;
		this.lexingRules = lexingRules;
	}
	
	public String getKeyword() { return keyword; }
	
	public boolean isBlank() { return false; }
	
	public boolean isWordSeparator() { return false; }
	
	public boolean isFirstCharManager() { return false; }
	
	public void nextToEndOfWord(CharReader script) throws ParsingException {}
	
	public void nextToEndOfExpression(CharReader script) throws ManagedException {}
}
