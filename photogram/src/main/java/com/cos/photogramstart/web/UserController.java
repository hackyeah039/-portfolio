package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;
	
	@GetMapping("/user/profile")
	public String profilePage() {
		return "user/profile";
	}

	@GetMapping("/user/{id}/update")
	public String updatePage(@PathVariable int id,@AuthenticationPrincipal PrincipalDetails principalDetails) {
//		System.out.println("유저 정보 : "+principalDetails.getUser());
		return "user/update";
	}
	
	@GetMapping("/user/{pageUserId}")
	public String profile(@PathVariable int pageUserId,Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println(pageUserId+"이거랑 , "+principalDetails.getUser().getId()+"이거");
		UserProfileDto user=userService.회원프로필(pageUserId ,principalDetails.getUser().getId()); //해당 아이디가 있으면 진행, 없으면 Exception진행
		model.addAttribute("dto",user);
		return "user/profile";
	}
}
