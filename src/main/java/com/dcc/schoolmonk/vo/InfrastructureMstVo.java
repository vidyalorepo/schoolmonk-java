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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "INFRASTRUCTURE_MST")
@EntityListeners(AuditingEntityListener.class)
public class InfrastructureMstVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@Column(name = "infra_name", unique = true)
	private String infraName;

	@Column(name = "infra_desc", length = 600)
	private String infraDesc;					//pic can also be uploaded later
	
	@Column(name = "infra_icon_path")
	private String infraIconPath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInfraName() {
		return infraName;
	}

	public void setInfraName(String infraName) {
		this.infraName = infraName;
	}

	public String getInfraDesc() {
		return infraDesc;
	}

	public void setInfraDesc(String infraDesc) {
		this.infraDesc = infraDesc;
	}

	public String getInfraIconPath() {
		return infraIconPath;
	}

	public void setInfraIconPath(String infraIconPath) {
		this.infraIconPath = infraIconPath;
	}

	@Override
	public String toString() {
		return "RoleMstVo [id=" + id + ", infraName=" + infraName + ", infraDesc=" + infraDesc + "]";
	}
}
