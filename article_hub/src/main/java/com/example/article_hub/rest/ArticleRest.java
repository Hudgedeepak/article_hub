package com.example.article_hub.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.article_hub.entity.Article;

@RequestMapping(path="/article")
public interface ArticleRest {
	
	@PostMapping(path="/addNewArticle")
	ResponseEntity<?> addNewArticle(@RequestBody Article article);
	
	@GetMapping(path="/getAllArticle")
	ResponseEntity<?> getAllArticle();
	
	
	@GetMapping(path="/getAllPublishedArticle")
	ResponseEntity<?> getAllPublishedArticle();
	
	@PostMapping(path="/updateArticle")
	ResponseEntity<?> updateArticle(@RequestBody Article article);

	@GetMapping(path="/deleteArticle/{id}")
	ResponseEntity<?> deleteArticle(@PathVariable Integer id);
	
}
