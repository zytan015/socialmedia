package com.dxc.exception;

public class PasswordNotMatchException extends Exception{
	public PasswordNotMatchException() {
        super();
    }


    public PasswordNotMatchException(String message) {
        super(message);
    }


    public PasswordNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}

