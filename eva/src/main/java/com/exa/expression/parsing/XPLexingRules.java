package com.exa.expression.parsing;

import com.exa.lexing.LexingRules;
import com.exa.lexing.WordWithOpenCloseDelimiter;

public class XPLexingRules extends LexingRules {
	
	static final String IDENTIFIER_CHARS_LC = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static final String NUMERIC_DIGITS = "0123456789";
	
	public static final String EXTENDED_NUMERIC_TERMINATION = "d";


	public XPLexingRules() {
		super(" \t\n\r");
		
		addWordSeparator(new WordWithOpenCloseDelimiter(this, '"','"'));
		addWordSeparator(new WordWithOpenCloseDelimiter(this, '\'','\''));
		addWordSeparator("=", "+=", "-=", "*=", "/=", "+", "-", "*", "/", "?", ":", ".", "(", ",", ")", ">", "&", "&&", "|", "||");
	}
	
	public boolean isInteger(String str, boolean exetendNum) {
		if(exetendNum) {
			for(int i=0; i<str.length()-1; i++) {
				char ch = str.charAt(i);
				if(NUMERIC_DIGITS.indexOf(ch) < 0) return false;
			}
			
			char ch = str.charAt(str.length()-1);
			if(NUMERIC_DIGITS.indexOf(ch) < 0) {
				if(EXTENDED_NUMERIC_TERMINATION.indexOf(ch) < 0) return false;
			}
			
			return true;
		}
		
		for(int i=1; i<str.length(); i++) {
			char ch = str.charAt(i);
			if(NUMERIC_DIGITS.indexOf(ch) < 0) return false;
		}
		
		return true;
	}
	
	public boolean isIdentifier(String name) {
		if(name == null || name.equals("")) return false;
		
		name = name.toLowerCase();

		char ch = name.charAt(0);
		
		if(IDENTIFIER_CHARS_LC.indexOf(ch) < 0) return false;
		
		for(int i=1; i<name.length(); i++) {
			ch = name.charAt(i);
			if((IDENTIFIER_CHARS_LC.indexOf(ch) < 0) && (NUMERIC_DIGITS.indexOf(ch) < 0)) return false;
		}
		
		return true;
	}

}
