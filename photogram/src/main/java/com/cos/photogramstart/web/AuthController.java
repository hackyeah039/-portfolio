package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidaiotnException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class AuthController {

	@Autowired
	private final AuthService authService; //final 쓰는 이유는 객체,생성자 생성시 무조건 초기화

	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto dto,BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error:bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("이거임"+error.getDefaultMessage());
			}
			throw new CustomValidaiotnException("유효성 검사 실패함", errorMap);
		}else {
			User user = dto.toEntity();
			authService.signUp(user);
			return "auth/signin";			
		}
	}
}
