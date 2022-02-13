package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	
	@Transactional
	public void sub(int fromUserid, int toUserid) {
		try {
			subscribeRepository.mSubscribe(fromUserid, toUserid);
			
		} catch (Exception e) {
			throw new CustomApiException("이미 구독 중입니다.");
		}
		
	}
	@Transactional
	public void cancelsub(int fromUserid, int toUserid) {
		subscribeRepository.mUnSubscribe(fromUserid, toUserid); 		
	}
}
