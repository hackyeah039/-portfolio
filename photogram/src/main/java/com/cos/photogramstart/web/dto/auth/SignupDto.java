package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class SignupDto {
	
	@Size(min = 2,max = 20)
	private String username;	
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private String name;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.email(email)
				.password(password)
				.name(name)
				.build();
	}
}
