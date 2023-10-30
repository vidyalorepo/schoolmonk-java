package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dcc.schoolmonk.vo.EnhanceNoticeBoardVo;

public interface EnhanceNoticeBoardDao extends CrudRepository<EnhanceNoticeBoardVo, Long> {

	@Query(nativeQuery = true,value = "select * from NOTICE_BOARD where notice_type = ?1 and "
			+ "current_date() between starts_on and expires_on")
	List<EnhanceNoticeBoardVo> findByNoticeType(String noticeType);
	
	EnhanceNoticeBoardVo findByNoticeId(String noticeId);
	
	EnhanceNoticeBoardVo findByNoticeIdAndNoticeType(String noticeId, String noticeType);

//	List<EnhanceNoticeBoardVo> findAllBySchoolId(Long schoolId);
	
	@Query(nativeQuery = true, value = "CALL getNoticeBySchoolId( :orderByClause, :limitClause, :whereClause);")
	List<EnhanceNoticeBoardVo> findAllNoticeInfoBySchoolId(@Param("whereClause") String whereClause,@Param("limitClause") String limitClause,@Param("orderByClause") String orderByClause);
	
	@Query(nativeQuery = true, value = "CALL countTotalByinput(:whereClause, :tableName);")
	Long getTotalCountByInput(@Param("whereClause") String whereClause, @Param("tableName") String tableName);
}
