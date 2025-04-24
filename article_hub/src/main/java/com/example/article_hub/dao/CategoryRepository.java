package com.example.article_hub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.article_hub.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	
	
}
