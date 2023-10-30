package com.dcc.schoolmonk.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.SchoolMstBulkVo;

public interface SchoolMstBulkDao extends CrudRepository<SchoolMstBulkVo, Long> {
	
	@Query(nativeQuery = true, value = "select ID from STATE_MST where State_Name = :stateName ")
	Long getStateID(String stateName);
	
	@Query(nativeQuery = true, value = "select ID from DISTRICT_MST where District_Name = :districtName ")
	Long getDistrictID(String districtName);
	
	@Query(nativeQuery = true, value = "select count(id) from SchoolMonk.SCHOOL_MST_BULK where year(created_on) = year(current_date())")
	Long getTotalSchoolCount();

}
