package com.exa.eva;

import com.exa.utils.ManagedException;

public class EvaException extends ManagedException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EvaException(String message) {
		super(message);
	}

	public EvaException(Throwable cause) {
		super(cause);
	}

}
