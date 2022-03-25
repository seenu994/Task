/*
 * package com.xyram.ticketingTool.Repository;
 * 
 * import java.util.List; import java.util.Map;
 * 
 * import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
 * import org.springframework.data.domain.Page; import
 * org.springframework.data.domain.Pageable; import
 * org.springframework.data.jpa.repository.JpaRepository; import
 * org.springframework.data.jpa.repository.Query; import
 * org.springframework.data.repository.query.Param; //import
 * org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension; import
 * org.springframework.stereotype.Repository; import
 * org.springframework.stereotype.Service;
 * 
 * import com.xyram.ticketingTool.apiresponses.ApiResponse; import
 * com.xyram.ticketingTool.entity.Announcement; import
 * com.xyram.ticketingTool.entity.Asset;
 * 
 * 
 * @Repository public interface AssetRepository extends JpaRepository<Asset,
 * String>{
 * 
 * @Query("Select distinct new map(a.aId as aId,a.vId as vId," +
 * "a.brand as brand,a.purchasedate as purchasedate,a.model as model," +
 * "a.serialno as serialno,a.warantydate as warantydate,a.ram as ram," +
 * "a.bagavailable as bagavailable,a.powercordavailable as powercordavailable,"
 * + "a.mouseavailable as mouseavailable,a.assetphotourl as assetphotourl," +
 * "a.assetstatus as assetstatus) from Asset a") Page<Map>
 * getAllAssets(java.awt.print.Pageable pageable); Asset findAll(String getaId);
 * 
 * 
 * @Query("Select distinct new map(a.aId as aId,a.vId as vId," +
 * "a.brand as brand,a.purchasedate as purchasedate,a.model as model," +
 * "a.serialno as serialno,a.warantydate as warantydate,a.ram as ram," +
 * "a.bagavailable as bagavailable,a.powercordavailable as powercordavailable,"
 * + "a.mouseavailable as mouseavailable,a.assetphotourl as assetphotourl," +
 * "a.assetstatus as assetstatus) from Asset a " +
 * "where a.title like %:searchString% or a.description like %:searchString% " +
 * " ORDER BY a.createdAt DESC") Page<Map> searchAllAssets(Pageable pageable,
 * String searchString);
 * 
 * List<Map> searchAsset(Object assetId, String searchString);
 * 
 * 
 * @Query("SELECT a from Asset a where a.aId=:id") Asset findAssetByaId(String
 * id);
 * 
 * @Query("SELECT a from Asset a where a.vId=:id") Asset findAssetByvId(String
 * id);
 * 
 * ApiResponse save(ApiResponse addasset);
 * 
 * 
 * 
 * 
 * }
 */