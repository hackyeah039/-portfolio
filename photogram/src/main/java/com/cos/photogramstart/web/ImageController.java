package com.cos.photogramstart.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidaiotnException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {

	private final ImageService imageService;
	
	@GetMapping({"/image/story","/"})
	public String storyPage() {
		return "image/story";
	}
	
	@GetMapping("/image/popular")
	public String popularPage(Model model) {
		List<Image> images = imageService.favImage();
		model.addAttribute("images",images);
		return "image/popular";
	}
	
	@GetMapping("/image/upload")
	public String uploadPage() {
		return "image/upload";
	}
	
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		//multipart는 @blank 지원X @AuthController에서 @Valid형식같이 유효성 체크XX , 그래서 
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidaiotnException("이미지 첨부 안되었습니다.");
		}
		imageService.imageUpload(imageUploadDto, principalDetails);
		return "redirect:/user/"+principalDetails.getUser().getId();
	}
}
