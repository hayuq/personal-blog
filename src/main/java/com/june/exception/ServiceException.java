package com.june.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -6652136444106370517L;
	
	private int code;

	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	public int getCode() {
		return code;
	}
	
}
