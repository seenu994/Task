package com.xyram.ticketingTool.Repository;


import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.xyram.ticketingTool.entity.Articles;

@Repository
public interface ArticleRepository extends JpaRepository<Articles, String>{
	
	
	
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
