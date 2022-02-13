package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	
	private String website;
	@NotBlank
	private String password;//필수
	@NotBlank
	private String name;//필수 
	private String bio; 
	private String phone;
	private String gender;
	
	public User toEntity() {
		return User.builder()
				.name(name)//validation체크 필수
				.password(password)//validation체크 필수
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
