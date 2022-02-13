package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer>{

	@Modifying
	@Query(value = "INSERT INTO subscribe(fromUserid,toUserid,createDate) VALUES(:fromUserid,:toUserid,now())", nativeQuery = true)
	void mSubscribe(int fromUserid,int toUserid); 
	
	@Modifying   //데이터 베이스에 변경을 주는 네이티브 쿼리틑 modifying 필요
	@Query(value = "DELETE FROM subscribe WHERE fromUserid=:fromUserid AND to Userid=:toUserid",nativeQuery = true)
	void mUnSubscribe(int fromUserid, int toUserid);
}
