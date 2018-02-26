package com.exa.lexing;

public class BlankWord extends WordSeparator {
	
	public BlankWord() { super(" ", null); }

	public final boolean isBlank() { return true; }
	
}
