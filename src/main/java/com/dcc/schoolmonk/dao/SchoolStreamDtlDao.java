package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

/**
 * @author: SD
 */

import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.SchoolStreamDtlVo;

public interface SchoolStreamDtlDao extends CrudRepository<SchoolStreamDtlVo, Long> {

	/*@Query(nativeQuery = true, value = "select roleId from ROLE_MST where role_name = :roleName")
	String getRoleIdByName(String roleName);*/
	
	
	@Query(nativeQuery = true, value = "SELECT distinct stream_name FROM SCHOOL_STREAM_DTL")
	List<String> getClassListOfSchool();
}
