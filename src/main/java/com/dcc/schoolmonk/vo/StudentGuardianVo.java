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
@Table(name = "STUDENT_GUARDIAN_DTL")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_by", "updated_by" }, allowGetters = true)
public class StudentGuardianVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Guardian_Name")
	private String guardianName;
	
	@Column(name = "Guardian_Email")
	private String guardianEmail;
	
	@Column(name = "Guardian_Phone")
	private String guardianPhone;
	
	@Column(name = "Guardian_Qualification")
	private String guardianQualification;
	
	@Column(name = "Guardian_Occupation")
	private String guardianOccupation;
	
	@Column(name = "Guardian_Designation")
	private String guardianDesignation;
	
	@Column(name = "Guardian_Business_Name")
	private String guardianBusinessName;
	
	@Column(name = "Guardian_Annual_Income")
	private String guardianAnnualIncome;
	
	@Column(name = "ID_Proof_Type")
	private String iDProofType;
	
	@Column(name = "Guardian_ID_Proof")
	private String guardianIDProof;
	
	@Column(name = "Guardian_Relation_With_Student")
	private String guardianRelationWithStudent;
	
	//join
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "Student_Id", nullable = false )
    @JsonBackReference
    public StudentMstVo studentMstVo;
	
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

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getGuardianEmail() {
		return guardianEmail;
	}

	public void setGuardianEmail(String guardianEmail) {
		this.guardianEmail = guardianEmail;
	}

	public String getGuardianPhone() {
		return guardianPhone;
	}

	public void setGuardianPhone(String guardianPhone) {
		this.guardianPhone = guardianPhone;
	}

	public String getGuardianQualification() {
		return guardianQualification;
	}

	public void setGuardianQualification(String guardianQualification) {
		this.guardianQualification = guardianQualification;
	}

	public String getGuardianOccupation() {
		return guardianOccupation;
	}

	public void setGuardianOccupation(String guardianOccupation) {
		this.guardianOccupation = guardianOccupation;
	}

	public String getGuardianDesignation() {
		return guardianDesignation;
	}

	public void setGuardianDesignation(String guardianDesignation) {
		this.guardianDesignation = guardianDesignation;
	}

	public String getGuardianBusinessName() {
		return guardianBusinessName;
	}

	public void setGuardianBusinessName(String guardianBusinessName) {
		this.guardianBusinessName = guardianBusinessName;
	}

	public String getGuardianAnnualIncome() {
		return guardianAnnualIncome;
	}

	public void setGuardianAnnualIncome(String guardianAnnualIncome) {
		this.guardianAnnualIncome = guardianAnnualIncome;
	}

	public String getGuardianIDProof() {
		return guardianIDProof;
	}

	public void setGuardianIDProof(String guardianIDProof) {
		this.guardianIDProof = guardianIDProof;
	}

	public String getGuardianRelationWithStudent() {
		return guardianRelationWithStudent;
	}

	public void setGuardianRelationWithStudent(String guardianRelationWithStudent) {
		this.guardianRelationWithStudent = guardianRelationWithStudent;
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


	public StudentMstVo getStudentMstVo() {
		return studentMstVo;
	}

	public void setStudentMstVo(StudentMstVo studentMstVo) {
		this.studentMstVo = studentMstVo;
	}

	public String getiDProofType() {
		return iDProofType;
	}

	public void setiDProofType(String iDProofType) {
		this.iDProofType = iDProofType;
	}

	
	
}
