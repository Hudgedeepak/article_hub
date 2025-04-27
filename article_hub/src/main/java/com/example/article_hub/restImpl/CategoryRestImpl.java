package com.example.article_hub.restImpl;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.article_hub.entity.Category;
import com.example.article_hub.rest.CategoryRest;
import com.example.article_hub.service.CategoryService;



@RestController
public class CategoryRestImpl implements CategoryRest  {
	
	Logger log = LoggerFactory.getLogger(CategoryRestImpl.class);
	
	
	@Autowired
	CategoryService categoryService;

	@Override
	public ResponseEntity<?> addNewCategory(Category category) {
		
		try {
			
			return categoryService.addNewCategory(category);
			
		} catch (Exception e) {
			log.error("Exception in addNewCategory : {}", e.getMessage());
		}
		
		return new ResponseEntity<>("{\"message\":\"something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getAllCategory() {
		try {
			
			return categoryService.getAllCategory();
			
		} catch (Exception e) {
			log.error("Exception in getAllCategory : {}", e.getMessage());
		}
		return  new ResponseEntity<>("{\"message\":\"something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> updateCategory(Category category) {
		try {
			return categoryService.updateCategory(category);
		} catch (Exception e) {
			log.error("Exception in updateCategory : {}", e.getMessage());
		}
		return  new ResponseEntity<>("{\"message\":\"something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
