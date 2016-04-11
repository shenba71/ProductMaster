package com.aspire.productmaster.web.rest.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aspire.productmaster.web.rest.controller.ProductMasterController;

@ControllerAdvice(basePackages = {"com.aspire.productmaster.web.rest.controller"})
public class ProductMasterExceptionControllerAdvice {
	
	private final Logger log = LoggerFactory.getLogger(ProductMasterController.class);
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public void throwExceptionResponse(Exception e){
		
		log.error("Exception occured in ProductMaster*******",e);
		
	}
	

}
