package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.HistoryVo;

public interface HistoryDao extends CrudRepository<HistoryVo, Long>{

	@Query(nativeQuery = true, value = "SELECT * from TX_HISTORY where form_code = ?2 and transaction_id = ?1")
	public List<HistoryVo> getAllHistory(long txId, long formId);

}
