package com.dcc.schoolmonk.vo;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "DISTRICT_MST")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdOn" }, allowGetters = true)
public class DistrictMstVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "District_Code")
	private String districtCode;
	
	@Column(name = "District_Name", unique = true)
	private String districtName;
	
	@Column(name = "State_Name")
	private String stateName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "State_Id", nullable = false)
	private StateMstVo stateMstVo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public StateMstVo getStateMstVo() {
		return stateMstVo;
	}

	public void setStateMstVo(StateMstVo stateMstVo) {
		this.stateMstVo = stateMstVo;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	
}
