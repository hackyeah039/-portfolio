package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

//Url가 아닌 이미지 파일을 받을 DTO

@Data
public class ImageUploadDto {
	private MultipartFile file; //url의 원본 이미지
	private String caption; //부가 설명
}
