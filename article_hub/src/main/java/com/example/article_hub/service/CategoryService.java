package com.example.article_hub.service;

import org.springframework.http.ResponseEntity;

import com.example.article_hub.entity.Category;

public interface CategoryService {

	ResponseEntity<?> addNewCategory(Category category);

	ResponseEntity<?> getAllCategory();

	ResponseEntity<?> updateCategory(Category category);

}
