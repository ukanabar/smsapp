package com.smsapp.exceptions;

public class SmsException extends Exception {

	private static final long serialVersionUID = 1L;

	public SmsException() {
		super();
	}

	public SmsException(final String message) {
		super(message);
	}

}