package com.example.article_hub.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.example.article_hub.entity.Article;

import jakarta.transaction.Transactional;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

	List<Article> getAllArticle(@Param("status") String status);

	@Modifying
	@Transactional
	Integer updateArticle(@Param("title") String title, @Param("content") String content,
			@Param("categoryId") Integer categoryId, @Param("publication_date") Date publication_date,
			@Param("status") String status, @Param("id") Integer id);

	@Modifying
	@Transactional
	Integer deleteArticle(@Param("id") Integer id);
}
