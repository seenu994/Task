package com.xyram.ticketingTool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Articles;
import com.xyram.ticketingTool.enumType.ArticleStatus;
import com.xyram.ticketingTool.service.ArticleService;
import com.xyram.ticketingTool.util.AuthConstants;

@RestController
@CrossOrigin
public class ArticleController {

	private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	ArticleService articleService;

	@PostMapping("/createArticle")
	public ApiResponse createArticle(@RequestBody Articles article) throws Exception{
		logger.info("Creating new Article");
		return articleService.createArticle(article);
	}

	@PostMapping("/editArticle")
	public ApiResponse editArticle(@RequestBody Articles article) throws Exception{
		logger.info("Editing Article");
		return articleService.editArticle(article);
	}
	
	@PostMapping("/changeArticleStatus/{articleId}/status/{articleStatus}")
	public ApiResponse changeArticleStatus(@PathVariable String articleId, @PathVariable ArticleStatus articleStatus) throws Exception {
		logger.info("Received request to change article status to: " + articleStatus + "for articleId: " + articleId);
		return articleService.changeArticleStatus(articleId, articleStatus);
	}
	
	@DeleteMapping("/deleteArticle/{articleId}")
	public ApiResponse deleteArticle(@PathVariable String articleId)  throws Exception{
		logger.info("Received deleting Artcle: ");
		return articleService.deleteArticle(articleId);
	}
	
	@GetMapping("/getArticleById/{articleId}") 
	public ApiResponse getArticleById(@PathVariable String articleId) throws Exception {
		logger.info("Received get Article by Id ");
		return articleService.getArticleById(articleId);
	}
	
	@GetMapping("/getAllArticles") 
	public ApiResponse getAllArticles(Pageable pageable) throws Exception {
		logger.info("Get all Articles ");
		return articleService.getAllArticles(pageable);
	} 
	
	@GetMapping("/getAllMyArticles")
	public ApiResponse getAllMyArticles(Pageable pageable) throws Exception {
		logger.info("Get all getAllMyArticles ");
		return articleService.getAllMyArticles(pageable);
	}
	
	@GetMapping("/searchArticle/{searchString}")
	public ApiResponse searchArticle(Pageable pageable,@PathVariable String searchString) throws Exception  {
		logger.info("Get all Articles ");
		return articleService.searchArticle(pageable,searchString);
	}

}
