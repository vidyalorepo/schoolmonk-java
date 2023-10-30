package com.dcc.schoolmonk.dao;

import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.AddressVo;

public interface AddressDao extends CrudRepository<AddressVo, Long>{

}
