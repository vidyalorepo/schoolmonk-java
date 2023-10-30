package com.dcc.schoolmonk.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "ROLE_MST")
@EntityListeners(AuditingEntityListener.class)
public class RoleMstVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;

	@Column(name = "role_name", unique = true)
	private String roleName;

	@Column(name = "role_descr")
	private String roleDescr;

	@Column(name = "workflow_role", columnDefinition = "tinyint(1) default 0")
	private boolean workflowRole;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescr() {
		return roleDescr;
	}

	public void setRoleDescr(String roleDescr) {
		this.roleDescr = roleDescr;
	}

	public boolean getWorkflowRole() {
		return workflowRole;
	}

	public void setWorkflowRole(boolean workflowRole) {
		this.workflowRole = workflowRole;
	}

}
