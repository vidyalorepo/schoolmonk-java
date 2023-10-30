package com.dcc.schoolmonk.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.RoleBaseAccessVo;

public interface RoleBaseAccessDao extends CrudRepository<RoleBaseAccessVo, Long> {

//	@Query(nativeQuery = true, value = "SELECT * FROM ROLE_BASE_ACCESS WHERE role_id = (SELECT  role_id FROM USER_ROLE_MAPPING a INNER JOIN USER_ROLE b ON a.`role_id` = b.`roleId` WHERE a.`user_key` = ?1 AND b.workflow_role = 0)")
	@Query(nativeQuery = true, value = "SELECT * FROM ROLE_BASE_ACCESS rba INNER JOIN USER_ROLE_MAPPING urm ON rba.`role_id` = urm.`role_id` INNER JOIN USER_ROLE ur ON ur.`roleId` = urm.`role_id` WHERE urm.`user_key` = ?1 AND ur.workflow_role = 0")
	RoleBaseAccessVo getRoleBaseAccess(long userKey);
}
