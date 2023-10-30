package com.dcc.schoolmonk.dao;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dcc.schoolmonk.vo.FeedbackVo;

public interface FeedBackDao extends JpaRepository<FeedbackVo, Long> {
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update FEEDBACK_MST set Feedback_Status = :feedbackStatus where id in :id ")
	void updateFeedbackStatus(List<Long> id, String feedbackStatus);
	
	@Query(nativeQuery = true, value="CALL getFeedBackForAdmin(:orderByClause, :limitClause, :whereClause);")
	List<FeedbackVo> findAll(@Param("whereClause") String whereClause, @Param("limitClause") String limitStr,@Param("orderByClause") String orderByStr);
	
	@Query(nativeQuery = true, value = "CALL countTotalByinput(:whereClause, :tableName);")
	Long getTotalCountByInput(@Param("whereClause") String whereClause, @Param("tableName") String tableName);

}


