package com.xyram.ticketingTool.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xyram.ticketingTool.Repository.ArticleRepository;
import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Articles;
import com.xyram.ticketingTool.enumType.ArticleStatus;
import com.xyram.ticketingTool.request.CurrentUser;
import com.xyram.ticketingTool.service.ArticleService;
import com.xyram.ticketingTool.util.ResponseMessages;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{
	
	private static final Logger logger = LoggerFactory.getLogger(EmpoloyeeServiceImpl.class);


	@Autowired
	CurrentUser currentUser;

	@Autowired
	ArticleServiceImpl articleServiceImpl;

	@Autowired
	ArticleRepository articleRepository;

	@Override
	public ApiResponse createArticle(Articles article) {
		ApiResponse response = new ApiResponse(false);
		
		if(article != null) {
			
//			try {
			if(article.getDescription().length() > 5000) {
				response.setSuccess(false);
				response.setMessage("Description length exceeded. Only 5000 characters will allow");
				return response;
			}
				article.setCreatedBy(currentUser.getUserId());
				article.setUserId(currentUser.getUserId());
				article.setUserName(currentUser.getFirstName());
				article.setUpdatedBy(currentUser.getUserId());
				article.setCreatedAt(new Date());
				article.setLastUpdatedAt(new Date());
				
				articleRepository.save(article);
				
				response.setSuccess(true);
				response.setMessage(ResponseMessages.ARTICLE_ADDED);
				Map content = new HashMap();
				content.put("ArticleId", article.getArticleId());
				response.setContent(content);
//			}catch(Exception e) {
//				response.setSuccess(false);
//				response.setMessage(ResponseMessages.ARTICLE_NOT_ADDED+" "+e.getMessage());
//			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ARTICLE_NOT_ADDED);
		}
		
		return response;
	}

	@Override
	public ApiResponse editArticle(Articles article) {
		ApiResponse response = new ApiResponse(false);
		
		Articles articleObj = articleRepository.findArticleById(article.getArticleId());
		if(!currentUser.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN") && !currentUser.getUserId().equalsIgnoreCase(articleObj.getUserId())) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.NOT_AUTHORIZED);
			return response;
		}
		
		if(articleObj != null) {		
			try {
				if(article.getDescription().length() > 5000) {
					response.setSuccess(false);
					response.setMessage("Description length exceeded. Only 5000 characters will allow");
					return response;
				}
				articleObj.setTitle(article.getTitle());
				articleObj.setDescription(article.getDescription());
				articleObj.setSearchLabels(article.getSearchLabels());
				articleObj.setUpdatedBy(currentUser.getUserId());
				articleObj.setLastUpdatedAt(new Date());
				
				articleRepository.save(articleObj);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ARTICLE_EDITED);
				
			}catch(Exception e) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ARTICLE_NOT_EDITED+" "+e.getMessage());
			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ARTICLE_NOT_EDITED);
		}
		
		return response;
	}

	@Override
	public ApiResponse deleteArticle(String articleId) {
		ApiResponse response = new ApiResponse(false);
		
		Articles articleObj = articleRepository.findArticleById(articleId);
		if(!currentUser.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN") && !currentUser.getUserId().equalsIgnoreCase(articleObj.getUserId())) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.NOT_AUTHORIZED);
			return response;
		}
		if(articleObj != null) {		
			try {
				
				articleRepository.delete(articleObj);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ARTICLE_DELETED);
				
			}catch(Exception e) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ARTICLE_NOT_DELETED+" "+e.getMessage());
			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ARTICLE_NOT_DELETED);
		}
		
		return response;
	}

	@Override
	public ApiResponse changeArticleStatus(String articleId, ArticleStatus status) {
		
		ApiResponse response = new ApiResponse(false);
		
		Articles articleObj = articleRepository.findArticleById(articleId);
		if(!currentUser.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN") && !currentUser.getUserId().equalsIgnoreCase(articleObj.getUserId())) {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.NOT_AUTHORIZED);
			return response;
		}
		if(articleObj != null) {		
			try {
				
				articleObj.setStatus(status);
				articleObj.setUpdatedBy(currentUser.getUserId());
				articleObj.setLastUpdatedAt(new Date());
				
				articleRepository.save(articleObj);

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ARTICLE_STATUS_CHANGED);
				
			}catch(Exception e) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ARTICLE_STATUS_CHANGED+" "+e.getMessage());
			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ARTICLE_STATUS_NOT_CHANGED);
		}
		
		return response;
	}

	@Override
	public ApiResponse getAllArticles(Pageable pageable) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		
		Page<Map> list = null;
		
		if(!currentUser.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN"))
			list = articleRepository.getAllActiveArticles(pageable);
		else
			list = articleRepository.getAllArticles(pageable);
		
		if(list.getSize() > 0) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ARTICLE_LIST_RETREIVED);
			Map<String, Page<Map>> content = new HashMap<String, Page<Map>>();
			content.put("Articles", list);
			response.setContent(content);
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ARTICLE_LIST_NOT_RETREIVED);
		}
		
		return response;
	} 
	
	@Override
	public ApiResponse getAllMyArticles(Pageable pageable) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse(false);
		
		Page<Map> list = articleRepository.getAllMyArticles(pageable,currentUser.getUserId());
		
		if(list.getSize() > 0) {
			response.setSuccess(true);
			response.setMessage(ResponseMessages.ARTICLE_LIST_RETREIVED);
			Map<String, Page<Map>> content = new HashMap<String, Page<Map>>();
			content.put("Articles", list);
			response.setContent(content);
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ARTICLE_LIST_NOT_RETREIVED);
		}
		
		return response;
	}

	@Override
	public ApiResponse searchArticle(Pageable pageable, String searchString) {

		ApiResponse response = new ApiResponse(false);
		
		Page<Map> list = null;
		searchString = searchString.replaceAll("[-+.^:,]!@#$%&*()_~`/","");
		if(currentUser.getUserRole().equalsIgnoreCase("TICKETINGTOOL_ADMIN"))
		{
			response.setMessage(ResponseMessages.ARTICLE_LIST_RETREIVED+" For Super admin");
			list = articleRepository.searchAllArticles(pageable,searchString.toLowerCase());
		}
		else {
			response.setMessage(ResponseMessages.ARTICLE_LIST_RETREIVED+" For employees");
			list = articleRepository.searchAllActiveArticles(pageable,searchString.toLowerCase());
		}
		
		if(list.getSize() > 0) {
			response.setSuccess(true);
			
			Map<String, Page<Map>> content = new HashMap<String, Page<Map>>();
			content.put("Articles", list);
			response.setContent(content);
		}
		else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ARTICLE_LIST_NOT_RETREIVED);
		}
		return response;
	}

	@Override
	public ApiResponse getArticleById(String articleId) {
		ApiResponse response = new ApiResponse(false);
		
		Articles articleObj = articleRepository.findArticleById(articleId);
//		if(!currentUser.getUserRole().equals("TICKETINGTOOL_ADMIN") && !currentUser.getUserId().equals(articleObj.getUserId())) {
//			response.setSuccess(false);
//			response.setMessage(ResponseMessages.NOT_AUTHORIZED);
//			return response;
//		}
		if(articleObj != null) {		
			try {

				response.setSuccess(true);
				response.setMessage(ResponseMessages.ARTICLE_DETAILS_RETREIVED);
				Map content = new HashMap();
				content.put("Detail", articleObj);
				response.setContent(content);
				
			}catch(Exception e) {
				response.setSuccess(false);
				response.setMessage(ResponseMessages.ARTICLE_DETAILS_RETREIVED+" "+e.getMessage());
				
			}	
		}else {
			response.setSuccess(false);
			response.setMessage(ResponseMessages.ARTICLE_DETAILS_NOT_RETREIVED);
		}
		
		return response;
	}

}
