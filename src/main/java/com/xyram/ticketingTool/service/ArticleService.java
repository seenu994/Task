package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Articles;
import com.xyram.ticketingTool.enumType.ArticleStatus;


public interface ArticleService {
	
	ApiResponse createArticle(Articles article) throws Exception;
	
	ApiResponse editArticle(Articles article) throws Exception;
	
	ApiResponse deleteArticle(String articleId) throws Exception;
	
	ApiResponse changeArticleStatus(String articleId, ArticleStatus status) throws Exception;
	
	ApiResponse getAllArticles(Pageable pageable) throws Exception;
	
	ApiResponse getArticleById(String articleId) throws Exception;
	
	ApiResponse searchArticle(Pageable pageable, String searchString) throws Exception;

	ApiResponse getAllMyArticles(Pageable pageable) throws Exception;


}
