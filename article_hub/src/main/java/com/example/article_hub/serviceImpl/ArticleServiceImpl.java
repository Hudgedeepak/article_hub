package com.example.article_hub.serviceImpl;

import java.util.Date;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.article_hub.dao.ArticleRepository;
import com.example.article_hub.entity.Article;
import com.example.article_hub.entity.Category;
import com.example.article_hub.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Autowired
	ArticleRepository articleRepository;

	@Override
	public ResponseEntity<?> addNewArticle(Article article) {
		try {

			if (!Objects.isNull(article)) {

				String errorKeyValue = article.checkForNullValues();
				if (Objects.isNull(errorKeyValue)) {
					article.setPublication_date(new java.sql.Date(System.currentTimeMillis()));					article.setCategory(new Category(article.getCategoryId()));
					articleRepository.save(article);
					return new ResponseEntity<>("{\"message\":\"article added successfully\"}", HttpStatus.OK);
				} else {
					return new ResponseEntity<>("{\"message\":\"Invalid value(" + errorKeyValue + ")\"}",
							HttpStatus.CONFLICT);
				}
			}
			return new ResponseEntity<>("{\"message\":\"invalid data\"}", HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			log.error("exception in addNewArticle", e);
		}
		return new ResponseEntity<>("{\"message\":\"somthing went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<?> getAllArticle() {
		try {
			return new ResponseEntity<>(articleRepository.getAllArticle(null), HttpStatus.OK);

		} catch (Exception e) {
			log.error("exception in getAllArticle", e);
		}
		return new ResponseEntity<>("{\"message\":\"somthing went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<?> getAllPublishedArticle() {
		try {
			return new ResponseEntity<>(articleRepository.getAllArticle("Published"), HttpStatus.OK);

		} catch (Exception e) {
			log.error("exception in getAllPublishedArticle", e);
		}
		return new ResponseEntity<>("{\"message\":\"somthing went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<?> updateArticle(Article article) {
		try {

			String errorKeyValue = article.checkForNullValues();
			if (Objects.isNull(errorKeyValue) && !Objects.isNull(article.getId())) {
				Integer updateCount = articleRepository.updateArticle(article.getTitle(), article.getContent(),
						article.getCategoryId(), new Date(0), article.getStatus(), article.getId());
				
				if(updateCount==0) {
					return new ResponseEntity<>("{\"message\":\"Article id does not found\"}", HttpStatus.NOT_FOUND);
				}
				else {
					
					return new ResponseEntity<>("{\"message\":\"Article updated successfully\"}", HttpStatus.OK);

				}
			}
			return new ResponseEntity<>("{\"message\":\"Invalid values for (" + errorKeyValue + ")\"}",
					HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			log.error("exception in updateCategory", e);
		}

		return new ResponseEntity<>("{\"message\":\"somthing went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> deleteArticle(Integer id) {
		try {
			if(!Objects.isNull(id)) {
			
				Integer deleteCount = articleRepository.deleteArticle(id);
				if(deleteCount==0) {
					return new ResponseEntity<>("{\"message\":\"Article id does not found\"}", HttpStatus.NOT_FOUND);
				}
				else {
					return new ResponseEntity<>("{\"message\":\"Article Id deleted successfully\"}", HttpStatus.OK);

				}
			}
			
				return new ResponseEntity<>("{\"message\":\"Invalid values for (Article id)\"}",
						HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("exception in deleteArticle", e);
		}
		return new ResponseEntity<>("{\"message\":\"somthing went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
