package com.dcc.schoolmonk.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dcc.schoolmonk.vo.SchoolMstVo;

public interface SchoolMstDao extends JpaRepository<SchoolMstVo, Long> {

	SchoolMstVo findByContactUserUserId(Long userId);

	List<SchoolMstVo> findAllByOrderByUpdatedOnDesc();

	@Query(nativeQuery = true, value = "select School_Name from SCHOOL_MST where id = :id")
	String findSchoolNameById(Long id);

	@Query(nativeQuery = true, value = "select * from SCHOOL_MST where school_user_id =:userId and School_Status = 'Active' limit 1")
	SchoolMstVo getSchoolMst(Long userId);

	@Query(nativeQuery = true, value = "select count(*) from SCHOOL_MST")
	Long getTotalCount();

	@Query(nativeQuery = true, value = "select * from SCHOOL_MST where school_user_id =:userId and School_Status = 'Active' limit 1")
	SchoolMstVo getSchoolByUserId(long userId);

	@Query(nativeQuery = true, value = "select * from SCHOOL_MST where School_Name like %:schoolName% or School_Address like %:schoolName% and School_Status = 'Active' ")
	List<SchoolMstVo> getSchoolBySearch(String schoolName);

	// @Query(nativeQuery = true, value = "select * from SCHOOL_MST where
	// School_Status = 'Active' and CURDATE() between Admission_Start_Date and
	// Admission_End_Date ")
	@Query(nativeQuery = true, value = "select distinct a.* from SCHOOL_MST a, ( SELECT School_Id, min(Admission_Start_Date) min_date, max(Admission_End_Date) max_date FROM SCHOOL_ADMISSION_DTL where Publish_Status = 'Y' group by School_Id) as tab where a.id = tab.School_Id and tab.max_date >= now() and tab.min_date <= now()")
	List<SchoolMstVo> getAdmissionOpenSchool();

	@Query(nativeQuery = true, value = "select * from SCHOOL_MST where School_Status = 'Active' and Featured_School = 'Y' ")
	List<SchoolMstVo> getFeaturedSchool();

	@Query(nativeQuery = true, value = "CALL searchSchoolByinput(:orderByClause, :limitClause, :whereClause);")
	List<SchoolMstVo> searchByinput(@Param("whereClause") String whereClause, @Param("limitClause") String limitClause,
			@Param("orderByClause") String orderByClause);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update SCHOOL_MST set School_Status = :status, school_status_updated_by =:userId, school_status_updated_on = CURDATE() where id in :id ")
	void updateSchoolStatus(List<Long> id, String status, long userId);

	@Query(nativeQuery = true, value = "CALL commonSearchSchoolByinput(:whereClause);")
	List<SchoolMstVo> commonSearchSchoolByinput(@Param("whereClause") String whereClause);

	@Query(nativeQuery = true, value = "CALL countTotalByinput(:whereClause, :tableName);")
	Long getTotalCountByInput(@Param("whereClause") String whereClause, @Param("tableName") String tableName);

	List<SchoolMstVo> findBySchoolStatus(String schoolStatus); // Active/In-Active

	@Query(nativeQuery = true, value = "select count(id) from SCHOOL_MST where School_Status = :schoolStatus ")
	Long getSchoolCountByStatus(String schoolStatus);

	@Query(nativeQuery = true, value = "select count(id) from SCHOOL_MST where School_Status = 'Active' and Featured_School = 'Y' ")
	Long getFeaturedSchoolCount();

	// Akash

	@Query(nativeQuery = true, value = "select count(*) from SCHOOL_MST where School_Status = 'Active' ")
	Long getActiveSchoolCount();

	@Query(nativeQuery = true, value = "select count(*) from SCHOOL_MST where School_Status = 'In-Active' ")
	Long getInActiveSchoolCount();

	@Query(nativeQuery = true, value = "SELECT COUNT(School_Type) , School_Type FROM SCHOOL_MST where School_Type is not null GROUP BY School_Type")
	List<String> getSchoolTypeCount();

	@Query(nativeQuery = true, value = "SELECT count(School_Board) FROM SCHOOL_MST where School_Status = 'Active' And School_Board like %:boardName%")
	Long getSchoolBoardCount(@Param("boardName") String boardName);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update SCHOOL_MST set profile_step = :profileStep where id = :id ")
	void updateProfileStep(Long id, String profileStep);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update SCHOOL_MST set favorite_users = :favSet where id = :id ")
	void updateFavoriteUsers(Long id, String favSet);

	@Query(nativeQuery = true, value = "select profile_step from SCHOOL_MST where id = :id ")
	String getProfileStep(Long id);

	// For Bulk
	@Query(nativeQuery = true, value = "select ID from STATE_MST where State_Name = :stateName ")
	Long getStateID(String stateName);

	@Query(nativeQuery = true, value = "select ID from DISTRICT_MST where District_Name = :districtName ")
	Long getDistrictID(String districtName);

	@Query(nativeQuery = true, value = "select count(id) from SCHOOL_MST where year(created_on) = year(current_date())")
	Long getTotalSchoolCount();

	@Query(nativeQuery = true, value = "select id from SCHOOL_MST where school_user_id = :schoolUserId")
	List<Long> exsitingSchoolUsers(Long schoolUserId);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update SCHOOL_MST set school_user_id = null where id in :exsitingAssociatedSchool")
	void updateExsitingSchoolUsers(List<Long> exsitingAssociatedSchool);

	@Query(nativeQuery = true, value = "CALL customSearchSchoolByinput(:whereClause, :sort, :limitStr);")
	List<SchoolMstVo> customSearchSchoolByinput(@Param("whereClause") String whereClause, @Param("sort") String sort,
			@Param("limitStr") String limitStr);

	@Query(nativeQuery = true, value = "select email from USER_DETAILS where School_Id in :idListLoc")
	List<String> getEmailListById(List<Long> idListLoc);

	SchoolMstVo findBySchoolNameSlug(String schoolNameSlug);
	
	@Query(nativeQuery = true, value = "SELECT School_Name, School_Name_slug FROM SCHOOL_MST where id = (SELECT School_Id FROM SCHOOL_ADMISSION_DTL where id = :id)")
	String findSchoolFromNotice(Long id);

	@Query(nativeQuery = true, value = "select id, School_Name_Slug from SCHOOL_MST where find_in_set(:userId,favorite_users)")
	List<String> findFavourieSchools(String userId);

	
	@Query(nativeQuery = true, value = "select favorite_users from SCHOOL_MST where find_in_set(:userId,favorite_users) and id = :schoolId")
	String findFavourieSchools(String schoolId, String userId);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update SCHOOL_MST set favorite_users = :updatedStr where id = :schoolId")
	void updateFavourieSchools(String updatedStr, Long schoolId);

	@Query(nativeQuery = true, value = "SELECT * FROM schoolportalstage.SCHOOL_MST where School_Name = ?1 and Postal_Code = ?2 ")
    SchoolMstVo findbySchoolName(String schoolName, String postalCode);

}
