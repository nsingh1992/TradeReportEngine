package com.Vanguard.TradeReportEngine.Exception;

public class ResourceNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super("Resource not found in db!!");
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
}

