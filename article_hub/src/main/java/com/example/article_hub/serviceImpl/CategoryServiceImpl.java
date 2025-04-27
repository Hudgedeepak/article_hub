package com.example.article_hub.serviceImpl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.article_hub.dao.CategoryRepository;
import com.example.article_hub.entity.Category;
import com.example.article_hub.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public ResponseEntity<?> addNewCategory(Category category) {
		try {

			if (!Objects.isNull(category) && Objects.isNull(category.getId()) && !Objects.isNull(category.getName())) {
				if (!categoryRepository.existsByNameIgnoreCase(category.getName())) {

					categoryRepository.save(category);
					return new ResponseEntity<>("{\"message\":\"category added successfully\"}", HttpStatus.OK);
				} else {
					return new ResponseEntity<>("{\"message\":\"category already exit\"}", HttpStatus.CONFLICT);
				}
			}

			else {
				return new ResponseEntity<>("{\"message\":\"invalid data\"}", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.error("exception in addNewCategory", e);
		}
		return new ResponseEntity<>("{\"message\":\"somthing went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getAllCategory() {
		try {
			return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
			
		} catch (Exception e) {
			log.error("exception in getAllCategory", e);
		}
		return new ResponseEntity<>("{\"message\":\"somthing went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	@Override
	public ResponseEntity<?> updateCategory(Category category) {
		
		try {
		
			if(!Objects.isNull(category) && !Objects.isNull(category.getId()) && !Objects.isNull(category.getName())) {
				
				if(!categoryRepository.existsByNameIgnoreCase(category.getName())) {
					Integer updateCount = categoryRepository.updateCategory(category.getName(), category.getId());
					
					if(updateCount==0) {
						return new ResponseEntity<>("{\"message\":\"Category id does not exit\"}", HttpStatus.NOT_FOUND);
					}
					else {
						return new ResponseEntity<>("{\"message\":\"Category updated successfully\"}", HttpStatus.OK);
					}
				}
				
				else {
					return new ResponseEntity<>("{\"message\":\"category with name ("+category.getName()+") already exit\"}", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
			}
			
			return new ResponseEntity<>("{\"message\":\"Invalid data\"}", HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			log.error("exception in updateCategory", e);
		}
		
		
		return new ResponseEntity<>("{\"message\":\"somthing went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
