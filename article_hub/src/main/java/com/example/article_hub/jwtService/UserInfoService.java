package com.example.article_hub.jwtService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.article_hub.dao.UserInfoRepo;
import com.example.article_hub.entity.UserInfo;

public class UserInfoService implements UserDetailsService{

	@Autowired
	private UserInfoRepo repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserInfo> userDetails = repository.findByEmail(email);
		return userDetails.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found" + email))
				;
	}
	

}
