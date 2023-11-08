package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dcc.schoolmonk.vo.ParentEnquiryVo;

public interface ParentEnquiryDao extends JpaRepository<ParentEnquiryVo, Long>{

    @Query(nativeQuery = true, value = "CALL getallEnquiryList(:whereClause, :orderByClause, :limitClause);")
    List<ParentEnquiryVo> getallEnquiry(@Param("whereClause") String whereClause, @Param("limitClause") String limitStr,@Param("orderByClause") String orderByStr);

    @Query(nativeQuery = true, value = "CALL getParentEnquirycount(:whereClause);")
    int getcountenquiry(@Param("whereClause") String whereClause);
    
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM schoolportalstage.PARENT_ENQUIRY_MST WHERE is_agree = true")
    int getGuardianEnquiryCount();
}
