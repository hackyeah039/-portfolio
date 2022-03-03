package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.like.LikesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeService {

	private final LikesRepository likesRepository;
	
	@Transactional
	public void like(int imageid,int principalid) {
		likesRepository.mLike(imageid, principalid);
	}
	
	@Transactional
	public void disLike(int imageid,int principalid) {
		likesRepository.mUnLike(imageid, principalid);
	}
}
