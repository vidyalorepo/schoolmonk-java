package com.dcc.schoolmonk.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SCHOOL_INFRASTRUCTURE_DTL")
@EntityListeners(AuditingEntityListener.class)
public class SchoolInfraDtlVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "School_Id", nullable = false )
    @JsonBackReference
	private SchoolMstVo schoolMstVo;

	@Column(name = "infra_name")
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
	
	public SchoolMstVo getSchoolMstVo() {
		return schoolMstVo;
	}

	public void setSchoolMstVo(SchoolMstVo schoolMstVo) {
		this.schoolMstVo = schoolMstVo;
	}

	public String getInfraIconPath() {
		return infraIconPath;
	}

	public void setInfraIconPath(String infraIconPath) {
		this.infraIconPath = infraIconPath;
	}

	@Override
	public String toString() {
		return "SchoolInfraDtlVo [id=" + id + ", infraName=" + infraName
				+ ", infraDesc=" + infraDesc + "]";
	}
}
