package com.cos.photogramstart.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Integer>{
	
	@Modifying
	@Query(value = "INSERT INTO likes(imageid, userid,createDate) VALUES(:imageid, :principalid,now())", nativeQuery = true)
	int mLike(int imageid, int principalid);
	
	@Modifying
	@Query(value = "DELETE FROM likes where imageid = :imageid AND userid = :principalid", nativeQuery = true)
	int mUnLike(int imageid, int principalid);
}
