package com.xyram.ticketingTool.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

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
