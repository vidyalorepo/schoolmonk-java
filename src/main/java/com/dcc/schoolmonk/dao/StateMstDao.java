package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.StateMstVo;

public interface StateMstDao extends CrudRepository<StateMstVo, Long> {

	List<StateMstVo> findByOrderByStateName();
	
	@Query(nativeQuery = true, value = "SELECT State_Name FROM STATE_MST where ID = :id ")
	String getName(Long id);
}
