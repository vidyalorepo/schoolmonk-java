package com.dcc.schoolmonk.vo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name ="USER_ROLE_MAPPING")
@EntityListeners(AuditingEntityListener.class)
public class UserRoleMappingVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "school_id")
	private Long schoolId;
	
	@Column(name = "isActive" , columnDefinition = "tinyint(1) default 1")
	private boolean isActive;
	
	@OneToOne(cascade = CascadeType.MERGE )
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    private SubcriptionMstVo subcriptionMstVo;

	public Long getRoleId() {
		return roleId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public SubcriptionMstVo getSubcriptionMstVo() {
		return subcriptionMstVo;
	}

	public void setSubcriptionMstVo(SubcriptionMstVo subcriptionMstVo) {
		this.subcriptionMstVo = subcriptionMstVo;
	}
	
}
