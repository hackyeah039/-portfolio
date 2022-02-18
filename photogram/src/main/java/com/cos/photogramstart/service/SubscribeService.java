package com.cos.photogramstart.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em; // 모든Repository 는 em의 구현체 
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> subList(int principalid,int PageUserid){
		
		// 1. 쿼리 준비
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id,u.username,u.profileImageUrl, ");
		sb.append("if((?=u.id),1,0) equalUserState, ");
		sb.append("if((SELECT 1 from subscribe where fromuserid=? and toUserid=u.id),1,0) subscribeState  ");
		sb.append("FROM user u INNER JOIN subscribe s  ");
		sb.append("ON u.id =s.touserid ");
		sb.append("WHERE s.fromUserid=?  ");
		/*
		 * SELECT u.id,u.username,u.profileImageUrl, if((3=u.id),1,0) equalstate,
		 * if((SELECT 1 from subscribe where fromuserid=3 and toUserid=u.id),1,0)
		 * subscribeState FROM user u INNER JOIN subscribe s ON u.id =s.touserid WHERE
		 * s.fromUserid=3;
		 */
		//첫번째 물음표 로그인 한 사람의 아이디 principalid
		//그다음 물음표 (로그인 한 사람의 아이디 ) principalid
		//마지막 물음표 페이지의 주인(pageUserid)
		
		//쿼리 완성
		Query query =em.createNativeQuery(sb.toString())
				.setParameter(1, principalid)
				.setParameter(2, principalid)
				.setParameter(3, PageUserid);
		
		//쿼리 실행
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto> dtos=result.list(query, SubscribeDto.class); //리스트로 리턴 list()
		//result결과(DB결과)를 자바 클래스(subscribDto)자동으로 매핑해주는 qlrm라이브러리 사용
		return dtos;
	}
	
	
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
