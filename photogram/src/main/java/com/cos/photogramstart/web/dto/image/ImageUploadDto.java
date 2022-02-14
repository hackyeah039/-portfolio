package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;

//Url가 아닌 이미지 파일을 받을 DTO

@Data
public class ImageUploadDto {
	private MultipartFile file; //url의 원본 이미지
	private String caption; //부가 설명
	
	//파라미터 1. 어떤 유저가(user) , 어떤 사진을(postImageUrl)
	public Image toEntity(String postImageUrl,User user) {
		return Image.builder()
				.caption(caption)
				.postimageUrl(postImageUrl)
				.user(user)
				.build();
	}
}
