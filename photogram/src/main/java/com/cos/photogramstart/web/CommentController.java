package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController {

	private final CommentService commentService;
	
	@DeleteMapping("/comment/{id}")
	public CMRespDto<?> deleteById(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails){
		commentService.deleteComment(id, principalDetails.getUser().getId());
		
		CMRespDto<?> returnData = new CMRespDto<>();
		returnData.setCode(1);
		returnData.setMessage(null);
		returnData.setData(null);
		
		return returnData;
	}
}
