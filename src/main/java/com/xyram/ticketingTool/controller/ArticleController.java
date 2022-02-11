package com.xyram.ticketingTool.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/createArticle",
			AuthConstants.HR_ADMIN_BASEPATH + "/createArticle", AuthConstants.INFRA_USER_BASEPATH + "/createArticle",
			AuthConstants.HR_BASEPATH + "/createArticle", AuthConstants.DEVELOPER_BASEPATH + "/createArticle",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/createArticle" })
	public ApiResponse createArticle(@RequestBody Articles article) {
		logger.info("Creating new Article");
		return articleService.createArticle(article);
	}

	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/editArticle",
			AuthConstants.HR_ADMIN_BASEPATH + "/editArticle", AuthConstants.INFRA_USER_BASEPATH + "/editArticle",
			AuthConstants.HR_BASEPATH + "/editArticle", AuthConstants.DEVELOPER_BASEPATH + "/editArticle",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/editArticle" })
	public ApiResponse editArticle(@RequestBody Articles article) {
		logger.info("Editing Article");
		return articleService.editArticle(article);
	}
	
	@PostMapping(value = { AuthConstants.ADMIN_BASEPATH + "/changeArticleStatus/{articleId}/status/{articleStatus}",
			AuthConstants.HR_ADMIN_BASEPATH + "/changeArticleStatus/{articleId}/status/{articleStatus}", 
			AuthConstants.INFRA_USER_BASEPATH + "/changeArticleStatus/{articleId}/status/{articleStatus}",
			AuthConstants.HR_BASEPATH + "/changeArticleStatus/{articleId}/status/{articleStatus}", 
			AuthConstants.DEVELOPER_BASEPATH + "/changeArticleStatus/{articleId}/status/{articleStatus}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/changeArticleStatus/{articleId}/status/{articleStatus}" })
	public ApiResponse updateEmployeeStatus(@PathVariable String articleId, @PathVariable ArticleStatus articleStatus) {
		logger.info("Received request to change article status to: " + articleStatus + "for articleId: " + articleId);
		return articleService.changeArticleStatus(articleId, articleStatus);
	}
	
	@DeleteMapping(value = { AuthConstants.ADMIN_BASEPATH + "/deleteArticle/{articleId}",
			AuthConstants.HR_ADMIN_BASEPATH + "/deleteArticle/{articleId}", 
			AuthConstants.INFRA_USER_BASEPATH + "/deleteArticle/{articleId}",
			AuthConstants.HR_BASEPATH + "/deleteArticle/{articleId}", 
			AuthConstants.DEVELOPER_BASEPATH + "/deleteArticle/{articleId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/deleteArticle/{articleId}" })
	public ApiResponse deleteArticle(@PathVariable String articleId) {
		logger.info("Received deleting Artcle: ");
		return articleService.deleteArticle(articleId);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getArticleById/{articleId}",
			AuthConstants.HR_ADMIN_BASEPATH + "/getArticleById/{articleId}", 
			AuthConstants.INFRA_USER_BASEPATH + "/getArticleById/{articleId}",
			AuthConstants.HR_BASEPATH + "/getArticleById/{articleId}", 
			AuthConstants.DEVELOPER_BASEPATH + "/getArticleById/{articleId}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getArticleById/{articleId}" })
	public ApiResponse getArticleById(@PathVariable String articleId) {
		logger.info("Received get Article by Id ");
		return articleService.getArticleById(articleId);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllArticles",
			AuthConstants.HR_ADMIN_BASEPATH + "/getAllArticles", AuthConstants.INFRA_USER_BASEPATH + "/getAllArticles",
			AuthConstants.HR_BASEPATH + "/getAllArticles", AuthConstants.DEVELOPER_BASEPATH + "/getAllArticles",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllArticles" })
	public ApiResponse getAllArticles(Pageable pageable) {
		logger.info("Get all Articles ");
		return articleService.getAllArticles(pageable);
	} 
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/getAllMyArticles",
			AuthConstants.HR_ADMIN_BASEPATH + "/getAllMyArticles", AuthConstants.INFRA_USER_BASEPATH + "/getAllMyArticles",
			AuthConstants.HR_BASEPATH + "/getAllMyArticles", AuthConstants.DEVELOPER_BASEPATH + "/getAllMyArticles",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/getAllMyArticles" })
	public ApiResponse getAllMyArticles(Pageable pageable) {
		logger.info("Get all getAllMyArticles ");
		return articleService.getAllMyArticles(pageable);
	}
	
	@GetMapping(value = { AuthConstants.ADMIN_BASEPATH + "/searchArticle/{searchString}",
			AuthConstants.HR_ADMIN_BASEPATH + "/searchArticle/{searchString}", AuthConstants.INFRA_USER_BASEPATH + "/searchArticle/{searchString}",
			AuthConstants.HR_BASEPATH + "/searchArticle/{searchString}", AuthConstants.DEVELOPER_BASEPATH + "/searchArticle/{searchString}",
			AuthConstants.INFRA_ADMIN_BASEPATH + "/searchArticle/{searchString}" })
	public ApiResponse searchArticle(Pageable pageable,@PathVariable String searchString) {
		logger.info("Get all Articles ");
		return articleService.searchArticle(pageable,searchString);
	}

}
