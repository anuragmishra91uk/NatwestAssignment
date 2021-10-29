package com.api.rest.exception;

public class CheckBalanceException extends SystemException {

	public CheckBalanceException(String message, String errorCode) {
		super(message, errorCode);
	}

}
