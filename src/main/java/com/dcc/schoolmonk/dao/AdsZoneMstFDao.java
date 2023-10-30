package com.dcc.schoolmonk.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dcc.schoolmonk.vo.AdszoneMstVo;

public interface AdsZoneMstFDao extends JpaRepository<AdszoneMstVo, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM ADS_ZONE_MST where zone_name=?1")
    AdszoneMstVo findByZoneName(String zone);

}