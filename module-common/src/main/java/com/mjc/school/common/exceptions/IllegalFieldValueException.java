package com.mjc.school.common.exceptions;

public class IllegalFieldValueException extends Exception{
    private int errorCode;

    public IllegalFieldValueException() {
        super();
    }

    public IllegalFieldValueException(String message) {
        super(message);
    }

    public IllegalFieldValueException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
