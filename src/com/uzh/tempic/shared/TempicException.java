package com.uzh.tempic.shared;

import java.io.Serializable;

/**
 * A generic exception that is thrown when an error on the server side occurs and can then be processed by the front end
 */
public class TempicException extends Exception implements Serializable {
    private String message;
    protected TempicException() {

    }
    public TempicException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
