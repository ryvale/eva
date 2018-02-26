package com.exa.lexing;


public class WordSeparator extends ActiveWord {

	public WordSeparator(String keyword, LexingRules lexer) {
		super(keyword, lexer);
	}
	
	public final boolean isWordSeparator() { return true; }

}
