package com.example.article_hub.service;

import org.springframework.http.ResponseEntity;

import com.example.article_hub.entity.Article;

public interface ArticleService {

	ResponseEntity<?> addNewArticle(Article article);

	ResponseEntity<?> getAllArticle();

	ResponseEntity<?> getAllPublishedArticle();

	ResponseEntity<?> updateArticle(Article article);

	ResponseEntity<?> deleteArticle(Integer id);

}
