package com.dxc.exception;

public class UserExistException extends Exception{
	
	public UserExistException() {
        super();
    }


    public UserExistException(String message) {
        super(message);
    }


    public UserExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
