package com.xyram.ticketingTool.service;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Articles;
import com.xyram.ticketingTool.enumType.ArticleStatus;


public interface ArticleService {
	
	ApiResponse createArticle(Articles article);
	
	ApiResponse editArticle(Articles article);
	
	ApiResponse deleteArticle(String articleId);
	
	ApiResponse changeArticleStatus(String articleId, ArticleStatus status);
	
	ApiResponse getAllArticles(Pageable pageable);
	
	ApiResponse getArticleById(String articleId);
	
	ApiResponse searchArticle(Pageable pageable, String searchString);

	ApiResponse getAllMyArticles(Pageable pageable);


}
