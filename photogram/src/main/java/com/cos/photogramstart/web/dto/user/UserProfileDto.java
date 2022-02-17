package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
	private boolean pageOwnerState; //페이지 주인인지 아닌지
	private int imageCount; //사진갯수
	private boolean subscribeState; //구독 상태
	private int subscribeCount;  //구독 갯수
	private User user; //유저 정보
}
