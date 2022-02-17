package com.cos.photogramstart.service;

import java.util.function.Supplier; 


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	
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
	
		return userEntity;
	}
}
