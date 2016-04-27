package com.schawk.productmaster.web.rest.errors;

public class MissingParameterException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public MissingParameterException(String message) {
        super(message);
    }

}
