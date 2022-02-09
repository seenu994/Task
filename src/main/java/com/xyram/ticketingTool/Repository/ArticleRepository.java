package com.xyram.ticketingTool.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.entity.Articles;
import com.xyram.ticketingTool.enumType.ArticleStatus;

@Repository
@Transactional
public interface ArticleRepository extends JpaRepository<Articles, String>{
	
	/*
	 * @Column(name = "article_id")
	private String articleId;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ArticleStatus status = ArticleStatus.ACTIVE;
	
	@Column(name = "user_id", nullable = false)
	private String userId;
	
	@Column(name = "user_name", nullable = false)
	private String userName;
	
	@Column(name = "created_at")
    private Date createdAt;
	
	@CreatedBy
	@Column(name = "created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	@Column(name = "last_updated_at")
	private Date lastUpdatedAt;

	@Column(name = "updated_by")
	@LastModifiedBy
	private String UpdatedBy;
	 */
	
	@Query("Select distinct new map( a.articleId as articleId,a.title as title,a.searchLabels as searchLabels,a.description as description,a.userId as userId,"
			+ " a.userName as userName, a.createdAt as createdAt,a.lastUpdatedAt as lastUpdatedAt,"
			+ " a.UpdatedBy as UpdatedBy, a.createdBy as createdBy) from Articles a where a.status = 'ACTIVE' "
			+ " ORDER BY a.createdAt DESC")
	Page<Map> getAllActiveArticles(Pageable pageable);
	
	@Query("Select distinct new map( a.articleId as articleId,a.title as title,a.searchLabels as searchLabels,a.description as description,a.userId as userId,"
			+ " a.userName as userName, a.createdAt as createdAt,a.lastUpdatedAt as lastUpdatedAt,"
			+ " a.UpdatedBy as UpdatedBy, a.createdBy as createdBy) from Articles a "
			+ " ORDER BY a.createdAt DESC")
	Page<Map> getAllArticles(Pageable pageable);
	
	@Query("Select distinct new map( a.articleId as articleId,a.title as title,a.searchLabels as searchLabels,a.description as description,a.userId as userId,"
			+ " a.userName as userName, a.createdAt as createdAt,a.lastUpdatedAt as lastUpdatedAt,"
			+ " a.UpdatedBy as UpdatedBy, a.createdBy as createdBy) from Articles a where a.userId=:userId  "
			+ " ORDER BY a.createdAt DESC")
	Page<Map> getAllMyArticles(Pageable pageable, String userId); 
	
	@Query("Select distinct new map( a.articleId as articleId,a.title as title,a.searchLabels as searchLabels,a.description as description,a.userId as userId,"
			+ " a.userName as userName, a.createdAt as createdAt,a.lastUpdatedAt as lastUpdatedAt,"
			+ " a.UpdatedBy as UpdatedBy, a.createdBy as createdBy) from Articles a where a.title like %:searchString% or a.description like %:searchString% "
			+ " ORDER BY a.createdAt DESC")
	Page<Map> searchAllArticles(Pageable pageable, String searchString);
	
	@Query("Select distinct new map( a.articleId as articleId,a.title as title,a.searchLabels as searchLabels,a.description as description,a.userId as userId,"
			+ " a.userName as userName, a.createdAt as createdAt,a.lastUpdatedAt as lastUpdatedAt,"
			+ " a.UpdatedBy as UpdatedBy, a.createdBy as createdBy) from Articles a where a.status = 'ACTIVE' and a.title like %:searchString% or a.description like %:searchString% or a.searchLabels like %:searchString% "
			+ " ORDER BY a.createdAt DESC")
	Page<Map> searchAllActiveArticles(Pageable pageable, String searchString);
	
	@Query("SELECT a from Articles a where a.articleId=:id")
	Articles findArticleById(String id);


}
