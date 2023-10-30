package com.dcc.schoolmonk.dao;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.SchoolNoticeBoardVo;

public interface SchoolNoticeBoardDao extends CrudRepository<SchoolNoticeBoardVo, Long> {

	//search by school id, date range etc.. / only visible to school related persons
	/*@Query(nativeQuery = true, value = "select roleId from ROLE_MST where role_name = :roleName")
	String getRoleIdByName(String roleName);*/
}
