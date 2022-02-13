package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomValidaiotnApiException;
import com.cos.photogramstart.handler.ex.CustomValidaiotnException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController //데이터 반환
@ControllerAdvice //모든 exception을 낚아챔
public class ControllerExceptionHandler {
	
	//런타임시 모든 exception을 낚아챔
	@ExceptionHandler(CustomValidaiotnException.class)
	public String validaiotnException(CustomValidaiotnException e) {
		return Script.back(e.getErrorMap().toString()); //자바 스크립트 응답
	} 
	
	@ExceptionHandler(CustomValidaiotnApiException.class)
	public ResponseEntity<?> validaiotnApiException(CustomValidaiotnApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST); // 오브젝트 응답
	} 

	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<?> ApiException(CustomApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
	}
	
}
