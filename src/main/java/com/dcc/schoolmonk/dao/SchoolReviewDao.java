package com.dcc.schoolmonk.dao;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dcc.schoolmonk.vo.SchoolReviewVo;

public interface SchoolReviewDao extends JpaRepository<SchoolReviewVo, Long> {
    @Query(nativeQuery = true, value = "CALL getallreviews(:whereClause, :limitClause);")
    List<SchoolReviewVo> getallreviews(@Param("whereClause") String whereClause, @Param("limitClause") String limitStr);

	@Query(nativeQuery = true, value = "CALL getReviewcount(:whereClause);")
    int getReviewcount(@Param("whereClause") String whereClause);
	
	@Query(nativeQuery = true, value = "SELECT * FROM SCHOOL_REVIEW_MST WHERE school_id=?1")
	List<SchoolReviewVo> findByid(long schoolid);
	
	@Query(nativeQuery = true, value = "SELECT * FROM SCHOOL_REVIEW_MST WHERE school_id=?1 and is_Approved=true")
	List<SchoolReviewVo> findReviewListByid(long schoolid);
	
	
	@Modifying
    @Transactional    
    @Query(nativeQuery = true, value = "UPDATE SCHOOL_REVIEW_MST SET is_Approved = ?1 WHERE id =?2")
	void updateStatusById(boolean status,long id);
	
	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM schoolportalstage.SCHOOL_REVIEW_MST WHERE is_Approved = true")
	int getReviewCount();
}

