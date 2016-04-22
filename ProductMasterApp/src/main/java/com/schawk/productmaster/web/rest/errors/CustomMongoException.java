package com.schawk.productmaster.web.rest.errors;

import com.mongodb.MongoException;

public class CustomMongoException extends MongoException{
    
    public String getStyleNumber() {
        return styleNumber;
    }

    public void setStyleNumber(String styleNumber) {
        this.styleNumber = styleNumber;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    String styleNumber;
    int code;

    public CustomMongoException(String styleNumber, int code, String exception) {
        super(exception);
        this.styleNumber= styleNumber;
        this.code = code;
    }
    
    public CustomMongoException(String exception){
        super(exception);
    }

}
