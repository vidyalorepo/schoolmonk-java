package com.dcc.schoolmonk.dao;

import java.util.List;

import javax.transaction.Transactional;

import com.dcc.schoolmonk.vo.HelpDeskDtlVo;
import com.dcc.schoolmonk.vo.IssueStateVo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface HelpDeskDao extends CrudRepository<HelpDeskDtlVo, Long> {
        public HelpDeskDtlVo findByIssueId(Long issueId);

        @Query(nativeQuery = true, value = "CALL customSearchIssuesByInput(:whereClause, :sort, :limitStr);")
        List<HelpDeskDtlVo> customSearchIssuesByInput(@Param("whereClause") String whereClause,
                        @Param("sort") String sort,
                        @Param("limitStr") String limitStr);

        @Query(nativeQuery = true, value = "CALL countTotalIssuesByinput(:whereClause, :tableName);")
        Long getTotalIssuesCountByInput(@Param("whereClause") String whereClause,
                        @Param("tableName") String tableName);

        @Modifying
        @Transactional
        @Query(nativeQuery = true, value = "update HELP_DESK_DTL set issue_state = :status, issue_status_updated_by =:userId, issue_status_updated_on = CURDATE() where issue_id in :id ")
        void updateIssueStatus(List<Long> id, String status, long userId);

        @Query("SELECT new com.dcc.schoolmonk.vo.IssueStateVo(COUNT(issueState),issueState) "
                        + "FROM HelpDeskDtlVo GROUP BY issueState")
        List<IssueStateVo> getIssueCount();
}
