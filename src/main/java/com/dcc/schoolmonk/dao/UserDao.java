package com.dcc.schoolmonk.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dcc.schoolmonk.vo.UserRoleMappingVo;
import com.dcc.schoolmonk.vo.UserTypeVo;
import com.dcc.schoolmonk.vo.UserVo;

public interface UserDao extends CrudRepository<UserVo, Long> {

	UserVo findByIsActiveAndEmail(boolean isActive, String email);

	UserVo findByEmail(String email);

	UserVo findByIsActiveAndEmailOrPhone(boolean isActive, String email, String phone);

	List<UserVo> findByEmailOrPhone(String email, String phone);

	@Query(nativeQuery = true, value = "SELECT CASE WHEN COUNT(userId) > 0 THEN TRUE ELSE FALSE END FROM USER_DETAILS a INNER JOIN USER_ROLE_MAPPING b INNER JOIN USER_ROLE c ON a.`userId` = b.user_key WHERE a.`username` = ?1 AND c.`role_name` = ?2")
	String checkPermission(String userName, String roleName);

	// @Query(nativeQuery = true, value = "SELECT a.`email` FROM USER_DETAILS a
	// INNER JOIN USER_ROLE_MAPPING b ON a.`userKey`=b.`user_key` INNER JOIN
	// USER_ROLE c ON c.`roleId` = b.`role_id` WHERE c.`role_name` = ?1")
	// List<String> getAllItAdmin(String roleType);
	//
	// @Query(nativeQuery = true, value = "SELECT a.`email` FROM USER_DETAILS a
	// INNER JOIN USER_ROLE_MAPPING b ON a.`userKey`=b.`user_key` INNER JOIN
	// USER_ROLE c ON c.`roleId` = b.`role_id` WHERE c.`role_name` = ?1 AND
	// a.`location` = ?2")
	// List<String> getAllOfficeAdmin(String roleType, String adminLocation);

	Optional<UserVo> findByResetToken(String resetToken);

	// @Query(nativeQuery = true, value = "SELECT a.`role_name` FROM USER_ROLE a
	// INNER JOIN USER_ROLE_MAPPING b ON a.`roleId`=b.`role_id` INNER JOIN
	// `USER_DETAILS` c ON c.`userKey` = b.`user_key` WHERE c.`userKey` = ?1 AND
	// (a.`role_name` = 'Office_Admin' OR a.`role_name` = 'IT_Admin' OR
	// a.`role_name` = 'Office_Admin_KOLKATA' OR a.`role_name` = 'MCPI_Admin') ")
	// List<String> getAllAdmin(long userKey);

	// @Query(nativeQuery = true, value = "update USER_DETAILS set reset_token = ?2
	// where userKey = ?1")
	// UserVo updateToken(long userKey, String resetToken);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update USER_DETAILS set password = :password, otp = null, token = null, isActive = 1 where userId = :userId ")
	public int updatePassword(Long userId, String password);

	// Get User Details By UserId
	UserVo findByUserId(long userId);

	UserVo findByToken(String token);

	@Query(nativeQuery = true, value = "SELECT role_name FROM USER_DETAILS a, USER_ROLE_MAPPING b, ROLE_MST c where a.userId = b.user_id and c.roleId = b.role_id and b.user_id = :userId")
	String getRoleOfUser(Long userId);

	@Query(nativeQuery = true, value = "update USER_DETAILS set School_Id = :schoolId where userId = :userId")
	@Modifying
	@Transactional
	void updateSchoolIdOfUser(Long schoolId, Long userId);

	@Query(nativeQuery = true, value = "update USER_DETAILS set isActive = 0 where userId = :userId")
	@Modifying
	@Transactional
	void updateActiveStatusOfUser(Long userId);

	UserVo findByIsActiveAndSchoolIdAndUserType(boolean isActive, Long schoolId, String userType);

	@Query(nativeQuery = true, value = "CALL customSearchUserByinput(:whereClause, :sort, :limitStr);")
	List<UserVo> customSearchUserByinput(@Param("whereClause") String whereClause, @Param("sort") String sort,
			@Param("limitStr") String limitStr);

	@Query(nativeQuery = true, value = "CALL countTotalUsersByinput(:whereClause, :tableName);")
	Long getTotalActiveUsersCountByInput(@Param("whereClause") String whereClause,
			@Param("tableName") String tableName);

	@Query("SELECT new com.dcc.schoolmonk.vo.UserTypeVo(COUNT(u.userType),u.userType) "
			+ "FROM UserVo u  where u.isActive = 1 GROUP BY u.userType")
	List<UserTypeVo> getTotalActiveUserTypes();

	@Query(nativeQuery = true, value = "SELECT count(*) FROM USER_DETAILS where user_type = 'SCHOOL_USER'")
	int getSchoolUserLen();
	
//	Added by Mouli
	@Query(nativeQuery = true, value="SELECT * FROM USER_DETAILS WHERE email=?1 or phone =?2")
	List<UserVo> checkUserActive(String email, String string);

    @Query(nativeQuery = true, value="update USER_DETAILS set `otp` = :otp where `email` = :email")
	@Modifying
	@Transactional
	void replaceOtp(@Param("otp") String otp,@Param("email") String email);

    @Modifying
	@Transactional
	@Query(nativeQuery = true, value="update USER_DETAILS set isActive=?2 where userId=?1")
    void updateuserStatus(long user_id, Boolean status);

}