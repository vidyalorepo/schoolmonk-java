package com.dcc.schoolmonk.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
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

import com.dcc.schoolmonk.vo.UserRoleMappingVo;

public interface UserRoleMappingDao extends CrudRepository<UserRoleMappingVo, Long> {

	
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update USER_ROLE_MAPPING set subscription_id = :id where user_id = :userId ")
	void upgradeSubscription(Long userId, Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM USER_ROLE_MAPPING where user_id=:user_id")
	UserRoleMappingVo findsubscriptionid(String user_id);
      
    
	
	



}
