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

import com.dcc.schoolmonk.vo.InfrastructureMstVo;
import com.dcc.schoolmonk.vo.UserRoleMappingVo;
import com.dcc.schoolmonk.vo.UserVo;

public interface RoleMstDao extends CrudRepository<InfrastructureMstVo, Long> {

	@Query(nativeQuery = true, value = "select roleId from ROLE_MST where role_name = :roleName")
	String getRoleIdByName(String roleName);
}
