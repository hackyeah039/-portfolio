package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidaiotnException extends RuntimeException{
	
	//객체 구분 시
	private static final long serialVersionUID=1L;
	
	private Map<String, String> errorMap;
	
	public CustomValidaiotnException (String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap=errorMap;
	}
	
	public Map<String, String> getErrorMap(){
		return errorMap;
	}

}
