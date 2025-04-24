package com.example.article_hub.dao;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.article_hub.entity.UserInfo;

public interface UserInfoRepo extends JpaRepository<UserInfo, Integer> {

	Optional<UserInfo> findByEmail(String email);
	
	List<UserInfo> getAllAppuser(String email);
	
	@Modifying
	@Transactional
	Integer updateUserStatus(@Param("status") String status, @Param("id") Integer id);
}
