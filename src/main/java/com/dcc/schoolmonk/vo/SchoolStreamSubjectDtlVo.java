package com.dcc.schoolmonk.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SCHOOL_STREAM_SUBJECT_DTL")
@EntityListeners(AuditingEntityListener.class)
public class SchoolStreamSubjectDtlVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "Stream_Id", nullable = false )
    @JsonBackReference
	private SchoolStreamDtlVo schoolStreamDtlVo;
	
	@Column(name = "subject_name", length = 100)
	private String subjectName;			//beng, eng
	
	@Column(name = "subject_type", length = 5)
	private String subjectType;	// 1,2 ,3...
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SchoolStreamDtlVo getSchoolStreamDtlVo() {
		return schoolStreamDtlVo;
	}

	public void setSchoolStreamDtlVo(SchoolStreamDtlVo schoolStreamDtlVo) {
		this.schoolStreamDtlVo = schoolStreamDtlVo;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	@Override
	public String toString() {
		return "SchoolStreamSubjectDtlVo [id=" + id + ", schoolStreamDtlVo=" + schoolStreamDtlVo + ", subjectName="
				+ subjectName + ", subjectType=" + subjectType + "]";
	}
}
