package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.InfrastructureMstVo;

public interface InfrastructureMstDao extends CrudRepository<InfrastructureMstVo, Long> {

	List<InfrastructureMstVo> findByOrderByInfraName();
}
