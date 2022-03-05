package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.handler.ex.CustomValidaiotnApiException;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

	private final CommentService commentService;
	
	@PostMapping("/api/comment")
	// x-www-form url encoded key value 타입만 commentdto로 받을수 있기 때문에 이건 못받고 , Json형식 받을려면 @RequestedBody로 받아야 됨 
	public ResponseEntity<?> commetSave(@Valid @RequestBody CommentDto commentDto,BindingResult bindingResult,@AuthenticationPrincipal PrincipalDetails principalDetails){
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error:bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new CustomValidaiotnApiException("유효성 검사 실패함", errorMap);
		}
		
		Comment comment =commentService.writeComment(commentDto.getContent(),commentDto.getImageid() , principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1,"댓글 쓰기 완료",comment),HttpStatus.CREATED); 
	}
	
	@DeleteMapping("/api/comment/{id}")
	public ResponseEntity<?> deleteById(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails){
		commentService.deleteComment(id);
		return new ResponseEntity<>(new CMRespDto<>(1,"댓글 삭제 완료",null),HttpStatus.OK);
	}
}

//		CMRespDto<?> returnData = new CMRespDto<>();
//		returnData.setCode(1);
//		returnData.setMessage(null);
//		returnData.setData(null);
//		
//		return null;