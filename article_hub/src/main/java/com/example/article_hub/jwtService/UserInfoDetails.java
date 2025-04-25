package com.example.article_hub.jwtService;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.article_hub.entity.UserInfo;

public class UserInfoDetails implements UserDetails {

	private String name;
	private String password;
	private String status;
	private List<GrantedAuthority> authorities;

	public UserInfoDetails(UserInfo userInfo) {
		name = userInfo.getEmail();
		password = userInfo.getPassword();
		status = userInfo.getStatus();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return name;
	}

	public Boolean isEnable() {
		return true;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
