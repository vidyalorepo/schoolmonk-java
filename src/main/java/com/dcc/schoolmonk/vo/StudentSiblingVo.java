package com.dcc.schoolmonk.vo;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "STUDENT_SIBLING_DTL")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_on", "created_by" }, allowGetters = true)
public class StudentSiblingVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Sibling_Name")
	private String siblingName;
	
	@Column(name = "Sibling_School_Name")
	private String siblingSchoolName;
	
	@Column(name = "Sibling_Admission_Id")
	private String siblingAdmissionId;		//If in same School
	
	@Column(name = "Sibling_Class")
	private String siblingClass;
	
	@Column(name = "Sibling_Section")
	private String siblingSection;
	
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "Student_Id", nullable = false )
    @JsonBackReference
    public StudentMstVo studentMstVoForSibling;
	
	@Column(name = "created_on", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdOn;

	@Column(name = "updated_on", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedOn;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSiblingName() {
		return siblingName;
	}

	public void setSiblingName(String siblingName) {
		this.siblingName = siblingName;
	}

	public String getSiblingClass() {
		return siblingClass;
	}

	public void setSiblingClass(String siblingClass) {
		this.siblingClass = siblingClass;
	}

	public String getSiblingSection() {
		return siblingSection;
	}

	public void setSiblingSection(String siblingSection) {
		this.siblingSection = siblingSection;
	}

	public StudentMstVo getStudentMstVoForSibling() {
		return studentMstVoForSibling;
	}

	public void setStudentMstVoForSibling(StudentMstVo studentMstVoForSibling) {
		this.studentMstVoForSibling = studentMstVoForSibling;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getSiblingSchoolName() {
		return siblingSchoolName;
	}

	public void setSiblingSchoolName(String siblingSchoolName) {
		this.siblingSchoolName = siblingSchoolName;
	}

	public String getSiblingAdmissionId() {
		return siblingAdmissionId;
	}

	public void setSiblingAdmissionId(String siblingAdmissionId) {
		this.siblingAdmissionId = siblingAdmissionId;
	}

	@Override
	public String toString() {
		return "StudentSiblingVo [Id=" + id + ", siblingName=" + siblingName + ", siblingSchoolName="
				+ siblingSchoolName + ", siblingAdmissionId=" + siblingAdmissionId + ", siblingClass=" + siblingClass
				+ ", siblingSection=" + siblingSection + ", studentMstVoForSibling=" + studentMstVoForSibling
				+ ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}
}
