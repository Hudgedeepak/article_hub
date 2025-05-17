package com.example.article_hub.service;

import org.springframework.http.ResponseEntity;

import com.example.article_hub.entity.AuthRequest;
import com.example.article_hub.entity.UserInfo;

public interface UserInfoService {

	
	ResponseEntity<?> addNewAppuser(UserInfo userinfo);

	ResponseEntity<?> login(AuthRequest authRequest);

	ResponseEntity<?> getAllAppuser();

	ResponseEntity<?> updateUserStatus(UserInfo userInfo);

	ResponseEntity<?> checkToken();

	ResponseEntity<?> updateUser(UserInfo userInfo);
	
	ResponseEntity<?> deleteUser(Integer id);


}
