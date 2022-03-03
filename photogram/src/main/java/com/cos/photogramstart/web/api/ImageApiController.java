package com.cos.photogramstart.web.api;

import java.util.List; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.service.LikeService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiController {
	
	private final ImageService imageService;
	private final LikeService likeService;
	//이미지 정보를 들고오는 api
	@RequestMapping(value = "/api/image", method = RequestMethod.GET)
	public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PageableDefault(size = 3) Pageable pageable){
		Page<Image> images =imageService.imageStory(principalDetails.getUser().getId(),pageable);
		return new ResponseEntity<>(new CMRespDto<>(1,"스토리 반환 완료",images),HttpStatus.OK);
	}
	//이미지를 좋아하는 컨트롤러
	@PostMapping("/api/image/{imageid}/likes")
	public ResponseEntity<?> likes(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable int imageid){
		System.out.println("들어옴");
//		int imageid = Integer.parseInt(imageId);
		
		likeService.like(imageid, principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 성공", null), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/api/image/{imageid}/likes")
	public ResponseEntity<?> unlikes(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable int imageid){
		likeService.disLike(imageid, principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 취소 성공", null), HttpStatus.OK);
	}
	
}
