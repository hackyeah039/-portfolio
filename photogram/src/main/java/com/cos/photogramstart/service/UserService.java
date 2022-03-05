package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final SubscribeRepository subscribeRepository;
	private final UserRepository userRepository;
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Transactional
	public User imageUpdate(int principalid,MultipartFile profileImageFile) {
		
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename();
		
		
		Path imageFilePath = Paths.get(uploadFolder + imageFileName);
		
		try {
		
			Files.write(imageFilePath, profileImageFile.getBytes());
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
		
		User userEntity = userRepository.findById(principalid).orElseThrow(()->{
			throw new CustomApiException("유저를 찾을 수 없습니다.");
		});	
		
//		System.out.println(userEntity + "이게 유저 " + userEntity.getId() + " _"+userEntity.getEmail() +"_"+userEntity.getProfileImageUrl());
		userEntity.setProfileImageUrl(imageFileName);

		
		return userEntity;
	}// 더티체킹으로 업데이트 됨
	
	
	@Transactional(readOnly = true)
	public UserProfileDto 회원프로필(int pageUserid, int principalId) {
		
		UserProfileDto dto = new UserProfileDto();
		
		//SELECT * from image WHERE userid =:userid;
		User userEntity = userRepository.findById(pageUserid).orElseThrow(()->{ 
			throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
		});
		dto.setUser(userEntity); // 유저 정보 저장
		dto.setPageOwnerState(pageUserid ==principalId); //페이지 주인이랑 지금 로그인 회원이랑 같은지 확인
		dto.setImageCount(userEntity.getImages().size()); //view 전에 이미지 미리 계산
		
		int subscribeState=subscribeRepository.mSubscribeState(principalId, pageUserid);
		int subscribeCount=subscribeRepository.mSubscribeCount(pageUserid);
		
		dto.setSubscribeState(subscribeState==1); //구독 상황 , 1과 같으면 참
		dto.setSubscribeCount(subscribeCount); //구독자 수
		
		//프로필에 좋아요 카운트 추가
		userEntity.getImages().forEach((image)->{
			image.setLikeCount(image.getLikes().size());
		});
		
		return dto;
	}
	@Transactional
	public User userUpdate(int id,User user) {
		//1. 영속화
		User userEntity = userRepository.findById(id).orElseThrow(()->{return new IllegalArgumentException("찾을 수 없는 id입니다.");});
				
		//2. 영속화 된 오브젝트 수정 , 더티체킹(업데이트 완료)
		userEntity.setName(user.getName());
		userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		
		//비밀번호 수정시에 안넘기면 기존 비밀번호 유지
		if(!user.getPassword().equals("")) {
			userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
		
		
		return userEntity;
	}

}
