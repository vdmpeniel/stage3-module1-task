package com.mjc.school.common.exceptions;

public class IllegalFieldValueException extends Exception{
    private String errorCode;

    public IllegalFieldValueException() {
        super();
    }

    public IllegalFieldValueException(String message) {
        super(message);
    }

    public IllegalFieldValueException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public String getErrorCode(){
        return errorCode;
    }

}
