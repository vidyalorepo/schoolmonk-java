package com.dcc.schoolmonk.dao;

import java.util.List;

import com.dcc.schoolmonk.vo.TestimonialVo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TestimonialDao extends JpaRepository<TestimonialVo, Long> {
        @Query(nativeQuery = true, value = "CALL customSearchTestimonialsByInput(:whereClause, :sort, :limitStr);")
        List<TestimonialVo> customSearchTestimonialsByInput(@Param("whereClause") String whereClause,
                        @Param("sort") String sort,
                        @Param("limitStr") String limitStr);

        @Query(nativeQuery = true, value = "CALL countTotalTestimonialsByinput(:whereClause, :tableName);")

        Long getTotalTestimonialsCountByInput(@Param("whereClause") String whereClause,
                        @Param("tableName") String tableName);
}
