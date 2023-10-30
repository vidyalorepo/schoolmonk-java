package com.dcc.schoolmonk.dao;

import com.dcc.schoolmonk.vo.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM TRANSACTION where order_id=?1")
    Transaction getTransationDtlByOrderId(String orderId);

}
