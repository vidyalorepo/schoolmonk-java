package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.DistrictMstVo;

public interface DistrictMstDao extends CrudRepository<DistrictMstVo, Long> {

	List<DistrictMstVo> findByStateMstVoIdOrderByDistrictName(Long stateId);
	
	List<DistrictMstVo> findByStateName(String stateName);
	
	@Query(nativeQuery = true, value = "SELECT District_Name FROM DISTRICT_MST where ID = :id ")
	String getName(Long id);
	
	List<DistrictMstVo> findByStateMstVoIdInOrderByDistrictName(List<Long> stateId);
}
