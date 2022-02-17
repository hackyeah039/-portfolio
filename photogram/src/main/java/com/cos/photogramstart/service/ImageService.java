package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageReposiroty;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageReposiroty imageReposiroty;
	
	@Value("${file.path}")
	private String uploadFolderAddress;
	
	@Transactional
	public void imageUpload(ImageUploadDto dto,PrincipalDetails principalDetails) {
		UUID uuid= UUID.randomUUID();
		String imageFileName = uuid+"_"+dto.getFile().getOriginalFilename(); 
		//  uuid_xx.jpg 이런게 리턴 , 서버에 저장된 파일을 똑같이 덮어 씌우는걸 방지하기 위해서
		
		Path imageFilePath = Paths.get(uploadFolderAddress+imageFileName);
		
		//통신,I/O 시에 예외 발생가능성O -> 컴파일 시에X , 런타임 시에 발생하니깐 잡을려고
		try {
			Files.write(imageFilePath, dto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(principalDetails.getUser()+" : 이게 유저 정보입니다. ");
		//image 테이블에 저장
		Image image =dto.toEntity(imageFileName,principalDetails.getUser());
//		Image imageEntity = imageReposiroty.save(image); 
		imageReposiroty.save(image); 
		
//		System.out.println(imageEntity);
	}
}
