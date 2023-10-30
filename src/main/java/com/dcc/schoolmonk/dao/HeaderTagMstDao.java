package com.dcc.schoolmonk.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.dcc.schoolmonk.vo.HeaderTagMstVo;

public interface HeaderTagMstDao extends JpaRepository<HeaderTagMstVo, Long> {
    
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE HEADER_TAG_MST SET is_Publish = false")
    int updateStatus();

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM HEADER_TAG_MST")
    int countTotal();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE HEADER_TAG_MST SET is_Publish = ?1 and id =?2")
    void updateStatusById(boolean status, long id);

    @Query(nativeQuery = true, value = "SELECT * FROM HEADER_TAG_MST WHERE is_Publish = true")
    HeaderTagMstVo fetchById();
}
