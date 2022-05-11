package com.xyram.ticketingTool.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyram.ticketingTool.admin.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	@Query("SELECT u FROM User u Where LOWER(u.username)=:username")
	List<User> findByUsername(String username);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update User  u set u.uid = :uid,u.osType = :osType,u.deviceId = :deviceId WHERE u.username=:username ")
	int updateUid(@Param("username") String username, @Param("uid") String uid, @Param("osType") String osType,@Param("deviceId") String deviceId);

	// Object findByUserId(String string);

	// Object findByUId(@Param("userId") String userId);

	@Query(" select r from User r where r.accesskey = :accesskey")
	Optional<User> findByAccestoken(@Param("accesskey") String accesskey);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update User p set p.password = :password WHERE p.username=:username")
	int updatePassword(@Param("username") String username, @Param("password") String password);
	@Query(value = "SELECT j from User j where j.id=:userId ")
    User getUserByIds(String userId);
	
	
	
   @Query(value = "SELECT j from User j where j.id=:userId ")
   String getUserById(String userId);

	@Query("select u from User u where u.id =:id")
	String getUserById(String id);

}

/*
 * @Query("SELECT count(DISTINCT u.id) FROM User u Where LOWER(u.userRole)=:role"
 * ) Integer countbyAdmin(UserRole role);
 * 
 * 
 * 
 * @Query("SELECT new map(u.image as blob,u.fileName as filename,u.mimeType as mimeType) FROM RPMUser u WHERE u.id = :userId"
 * ) Map getImage(String userId);
 * 
 * 
 * @Transactional
 * 
 * @Modifying(clearAutomatically = true)
 * 
 * @Query("update Care365User p set p.uid = :uid , p.deviceId = :deviceId ,p.uniqueDeviceCode=:deviceCode, p.lastUpdatedAt = now() WHERE p.username=:username"
 * ) int updateUIDForMobile(@Param("username")String username, @Param("uid")
 * String uid,@Param("deviceId") String deviceId,@Param("deviceCode") String
 * deviceCode);
 * 
 * @Transactional
 * 
 * @Modifying(clearAutomatically = true)
 * 
 * @Query("update Care365User p set p.uid = :uid ,p.uniqueDeviceCode=:deviceCode, p.lastUpdatedAt = now()  WHERE p.username=:username"
 * ) int updateUIDForWeb(@Param("username")String username, @Param("uid") String
 * uid ,@Param("deviceCode") String deviceCode);
 * 
 * 
 * @Transactional
 * 
 * @Modifying(clearAutomatically = true)
 * 
 * @Query("update User p set  p.lastUpdatedAt = now()  WHERE p.username=:username"
 * ) int InvalidateUserSession(@Param("username")String username, String
 * uniqueDeviceCode);
 * 
 * @Query("SELECT u FROM User u WHERE u.id=:id") User getUserDetails(String id);
 * 
 * @Query(value="SELECT * from rpm_user where user_role = :userType",
 * nativeQuery=true) List<User> getAllPtUserId(String userType);
 * 
 * @Transactional
 * 
 * @Modifying(clearAutomatically = true)
 * 
 * @Query("update User p set p.password = :password , p.status = :status WHERE p.username=:username"
 * ) int setPasswordAccountActivate(@Param("username")String
 * username, @Param("password") String password,@Param("userStatus") UserStatus
 * userStatus);
 * 
 * 
 * @Query("SELECT u FROM User u WHERE u.accesskey=:id") User checkToken(String
 * id);
 * 
 * @Transactional
 * 
 * @Modifying(clearAutomatically = true)
 * 
 * @Query("update User p set p.password = :password WHERE p.username=:username")
 * int updatePassword(@Param("username")String username, @Param("password")
 * String password);
 * 
 * 
 * @Query("SELECT p FROM  User p  WHERE p.username=:username") User
 * getUser(String username);
 * 
 * 
 * @Query("SELECT u.uid FROM  User u  WHERE u.scopeId=:scopeId") String
 * getUidByScopeId(String scopeId);
 * 
 * 
 * @Transactional
 * 
 * @Modifying(clearAutomatically = true)
 * 
 * @Query("update User p set p.userStatus = :status WHERE p.username=:username")
 * int active(String username, UserStatus status);
 * 
 * 
 * }
 * 
 */
