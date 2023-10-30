package com.dcc.schoolmonk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

/*import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hpl.approvalapps.vo.AttachmentDetailVO;
import com.hpl.approvalapps.vo.AttachmentListVO;

@Repository
@Transactional
public class AttachmentDAO {
 @PersistenceContext
 private EntityManager entityManager;
 
 public long insert(AttachmentDetailVO attachment) {
  entityManager.persist(attachment);
  return attachment.getLinkId();
 }
 public AttachmentDetailVO find(long id) {
  return entityManager.find(AttachmentDetailVO.class, id);
 }
 public AttachmentListVO findAll() {
  TypedQuery<AttachmentDetailVO> query = entityManager.createNamedQuery(
   "query_find_all_attachements", AttachmentDetailVO.class);
  return (AttachmentListVO) query.getResultList();
 }
}*/

/**
 * @author: SD
 */

import org.springframework.data.repository.CrudRepository;

import com.dcc.schoolmonk.vo.AttachmentVo;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AttachmentDao extends CrudRepository<AttachmentVo, Long> {

    @Query(nativeQuery = true, value = "select file_id from TX_ATTACHMENT where transaction_id = ?1")
    List<String> findFileByTxId(long transactionId);

    List<AttachmentVo> findByTxId(long transactionId);

    @Query(nativeQuery = true, value = "select * from TX_ATTACHMENT where transaction_id = ?1 and form_code like %?2%")
    List<AttachmentVo> findByTxIdAndFormCode(long transactionId, String formCode);
    
    @Query(nativeQuery = true, value = "select * from TX_ATTACHMENT where transaction_id = ?1 and form_code like %?2%")
    AttachmentVo findByTxIdAndFormCodeSingle(Long transactionId, String formCode);

    @Query(nativeQuery = true, value = "SELECT * FROM TX_ATTACHMENT where ads_id =?1")
    List<AttachmentVo> findByAdsId(long id);

}
