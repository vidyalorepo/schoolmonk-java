package com.dcc.schoolmonk.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcc.schoolmonk.vo.SchoolInfraDtlVo;

public interface SchoolInfraDtlDao extends JpaRepository<SchoolInfraDtlVo, Long> {
	
}
