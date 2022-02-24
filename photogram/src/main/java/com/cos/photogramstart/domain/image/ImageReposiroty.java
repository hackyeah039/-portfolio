package com.cos.photogramstart.domain.image;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ImageReposiroty extends JpaRepository<Image, Integer>{
	
	//자신(로그인 한)을 제외한 모든 정보(이미지)를 가져오는 쿼리 
	@Query(value = "SELECT * FROM image WHERE userid IN(SELECT touserid FROM subscribe WHERE FROMuserid =:principalid) order by id desc",nativeQuery = true)
	Page<Image> mStory(int principalid,Pageable pageable);
}


