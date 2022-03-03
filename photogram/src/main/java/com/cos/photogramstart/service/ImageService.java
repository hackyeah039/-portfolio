package com.cos.photogramstart.service;

import java.nio.file.Files ;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.Tag.Tag;
import com.cos.photogramstart.domain.Tag.TagRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageReposiroty;
import com.cos.photogramstart.util.TagUtils;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageReposiroty imageReposiroty;
	private final TagRepository tagRepository;
	
	@Transactional(readOnly = true)
	public Page<Image> imageStory(int principalid,Pageable pageable){
		Page<Image> images = imageReposiroty.mStory(principalid,pageable);
		
		//image에 좋아요 담기
		images.forEach((image)->{
			
			image.setLikeCount(image.getLikes().size());
			
			image.getLikes().forEach((like)->{
				if(like.getUser().getId()==principalid) { //해당 이미지에 좋아요한 사람들을 찾아서 현재 로긴한 사람이 좋아요 한것인지 비교
					image.setLikeState(true);
				}
			});
		});
		return images;
	} 
	
	
	
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
		// 1. image 저장
		Image image =dto.toEntity(imageFileName,principalDetails.getUser());
//		Image imageEntity = imageReposiroty.save(image); 
		Image imageEntity =imageReposiroty.save(image); 
		
//		// 2. Tag 저장
//		List<Tag> tags = TagUtils.parsingToTagObject(dto.getTags(), imageEntity);
//		tagRepository.saveAll(tags);
		
	}
	
	@Transactional(readOnly = true)
	public List<Image> favImage(){
		return imageReposiroty.mPopular();
	}
}
