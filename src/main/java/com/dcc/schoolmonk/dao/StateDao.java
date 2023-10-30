package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.StatesVo;

public interface StateDao extends CrudRepository<StatesVo, Long>{

	List<StatesVo> findAllByCountryIdOrderByStateNameAsc(Long countryId);

}
