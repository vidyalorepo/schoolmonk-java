package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.DropdownMasterVo;

public interface DropdownMasterDao extends CrudRepository<DropdownMasterVo, Long>{

	@Query(nativeQuery = true,value = "select list_value from DROPDOWN_VALUE where form_code = ?1 and field_name = ?2 and list_id = ?3 and parent = ?4")
	String findListValue(long formCode, String fieldName, long listId, long parentId);
	
	/*@Query(nativeQuery = true,value = "select list_value from DROPDOWN_VALUE where form_code = ?1 and field_name = ?2 and list_id = ?3")
	String findListValue(long formCode, String fieldName, long listId);*/
	
	@Query(nativeQuery = true,value = "select list_value from DROPDOWN_VALUE where form_code = ?1 and field_name = ?2 and list_id = ?3")
	List<String> findListValue(long formCode, String fieldName, long listId);
	
	@Query(nativeQuery = true,value = "select * from DROPDOWN_VALUE where form_code = ?1 and field_name = ?2 and list_id = ?3")
	List<DropdownMasterVo> findBoardList(long formCode, String fieldName, long listId);
}
