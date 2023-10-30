package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dcc.schoolmonk.vo.AdmissionStatusMstVo;
import com.dcc.schoolmonk.vo.EnhanceNoticeBoardVo;

public interface AdmissionStatusMstDao extends CrudRepository<AdmissionStatusMstVo, Long> {

	
	@Query(nativeQuery = true, value = "CALL getNoticeBySchoolId( :orderByClause, :limitClause, :whereClause);")
	List<EnhanceNoticeBoardVo> findAllAdmissionStatus(@Param("whereClause") String whereClause,@Param("limitClause") String limitClause,@Param("orderByClause") String orderByClause);
	
	@Query(nativeQuery = true, value = "CALL countTotalByinput(:whereClause, :tableName);")
	Long getTotalCountByInput(@Param("whereClause") String whereClause, @Param("tableName") String tableName);
}
