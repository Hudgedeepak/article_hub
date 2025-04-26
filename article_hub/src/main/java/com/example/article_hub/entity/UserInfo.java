package com.example.article_hub.entity;


import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;



@NamedQuery(name="UserInfo.getAllAppuser", query = "select new com.example.article_hub.entity.UserInfo(id,name,email,status) from appuser where isDeletable = 'true' and email not in (:email)")

@NamedQuery(name = "UserInfo.updateUserStatus", query = "update appuser set status=:status where id=:id and isDeletable='true'")

@Entity(name= "appuser")
@Table(name= "appuser")

@AllArgsConstructor
public class UserInfo implements Serializable {

	private static final long serialVersionUID =1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String password;
	private String isDeletable;
	private String status;

	public UserInfo(int id, String name, String email, String status) {
	    this.id = id;
	    this.name = name;
	    this.email = email;
	    this.status = status;
	}

	public String getIsDeletable() {
		return isDeletable;
	}

	public void setIsDeletable(String isDeletable) {
		this.isDeletable = isDeletable;
	}

	public UserInfo() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
