package com.dcc.schoolmonk.dao;

import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.SchoolStreamSubjectDtlVo;

public interface SchoolStreamSubjectDtlDao extends CrudRepository<SchoolStreamSubjectDtlVo, Long> {

	//@Query(nativeQuery = true, value = "select roleId from ROLE_MST where role_name = :roleName")
	void deleteBySchoolStreamDtlVoIdAndSubjectType(Long streamId, String subjectType);
}
