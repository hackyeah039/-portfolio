package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//구독 정보를 보는 Dto
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
	private int userid; // 누구를 구독하는지에 해당하는 아이디
	private String username; //누구를 구독하는지에 해당하는 유저네임
	private String profileImageUrl; //사진
	private Integer subscribeState; //구독 상태인지 아닌지
	private Integer equalUserState; //본인인지 아닌지
}
