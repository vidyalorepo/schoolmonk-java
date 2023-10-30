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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SCHOOL_ACADEMIC_DTL")
@EntityListeners(AuditingEntityListener.class)
public class SchoolAcademicDtlVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "School_Id", nullable = false )
    @JsonBackReference
	private SchoolMstVo schoolMstVo;
	
	@Column(name = "Academic_Year", length = 10)
	private String academicYear;
	
	@Column(name = "Performance_Title", length=200 ,nullable = true)
	private String performanceTitle;
	
	@Column(name = "Academic_Information",length=5000, nullable = false)
	private String academicInformation;

	public SchoolMstVo getSchoolMstVo() {
		return schoolMstVo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSchoolMstVo(SchoolMstVo schoolMstVo) {
		this.schoolMstVo = schoolMstVo;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getPerformanceTitle() {
		return performanceTitle;
	}

	public void setPerformanceTitle(String performanceTitle) {
		this.performanceTitle = performanceTitle;
	}

	public String getAcademicInformation() {
		return academicInformation;
	}

	public void setAcademicInformation(String academicInformation) {
		this.academicInformation = academicInformation;
	}

	@Override
	public String toString() {
		return "SchoolAcademicDtlVo [id=" + id + ", schoolMstVo=" + schoolMstVo + ", academicYear=" + academicYear
				+ ", performanceTitle=" + performanceTitle + ", academicInformation=" + academicInformation + "]";
	}


   
}
