package com.example.article_hub.entity;

import java.io.Serializable;
import java.sql.Date;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity(name="article")
@Table(name="article")
@Data
@AllArgsConstructor
public class Article implements Serializable {

	private static final long  serialVersionUID= 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	private String title;
	
	@Lob
	private String content;
	
	@ManyToAny
	@JoinTable(name="category_id")
	private Category category;
	
	private Date publication_date;
	
	private String status;
	
	
//	----------------
	
	@Transient
	private String categoryId;
	
	@Transient
	private String categoryName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Date getPublication_date() {
		return publication_date;
	}

	public void setPublication_date(Date publication_date) {
		this.publication_date = publication_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public Article() {
		super();
	}

	public Article(Integer id, String title, String content,String status, Date publication_date,  String categoryId) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.publication_date = publication_date;
		this.status = status;
		this.categoryId = categoryId;
	}
	
	
	private String CheckForNullValues() {
		if(title==null) {
			return "title";
		}
		if(content==null) {
			return "content";
		}
		if(categoryId==null) {
			return "categoryId";
		}
		if(status==null) {
			return "status";
		}
		return null;
	}
	
}
