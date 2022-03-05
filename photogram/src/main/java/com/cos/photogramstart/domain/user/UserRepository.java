package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

// 어노테이션 X -> 자동으로 IOC등록
public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUsername(String username);
	
	
	
}
