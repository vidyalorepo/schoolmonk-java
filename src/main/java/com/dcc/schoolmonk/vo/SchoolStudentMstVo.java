/*package com.dcc.schoolmonk.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "SCHOOL_STUDENT_MST")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_by", "updated_by" }, allowGetters = true)
public class SchoolStudentMstVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Student_Generated_Id")
	private String studentGeneratedId;
	
	@Column(name = "Student_Name")
	private String studentName;
	
	@OneToOne
    @JoinColumn(name = "School_Id", nullable = false )
	private SchoolMstVo schoolMstVo;
	
	@Column(name = "Current_Class")
	private String currentClass;
	
	@Column(name = "Current_Section")
	private String currentSection;
	
	@Column(name = "Roll_No")
	private String rollNo;
	
	@Column(name = "Date_of_Birth")
	private String dateOfBirth;
	
	@Column(name = "Gender")
	private String gender;
	
	@Column(name = "Religion")
	private String religion;
	
	@Column(name = "Blood_Group")
	private String bloodGroup;
	
	@Column(name = "Nationallity")
	private String nationallity;
	
	@Column(name = "First_Language")
	private String firstLanguage;
	
	@Column(name = "Last_School_Attempt")
	private String lastSchoolAttempt;
	
	//guardian info
	@Column(name = "Gurdian_Name")
	private String gurdianName;
	
	@Column(name = "Gurdian_Phone_No")
	private String gurdianPhoneNo;
	
	@Column(name = "Gurdian_Email")
	private String gurdianEmail;
	
	@Column(name = "Gurdian_Mailing_Address")
	private String gurdianMailingAddress;
	
	//Marks related info????????????????
	
	@Column(name = "created_on", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdOn;

	@Column(name = "created_by", nullable = false, updatable = false)
	private Long createdBy;

	@Transient
	private UserVo createdByDetails;

	@Column(name = "updated_on", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedOn;

	@Column(name = "updated_by")
	private Long updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public UserVo getCreatedByDetails() {
		return createdByDetails;
	}

	public void setCreatedByDetails(UserVo createdByDetails) {
		this.createdByDetails = createdByDetails;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}  
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getNationallity() {
		return nationallity;
	}

	public void setNationallity(String nationallity) {
		this.nationallity = nationallity;
	}

	public String getFirstLanguage() {
		return firstLanguage;
	}

	public void setFirstLanguage(String firstLanguage) {
		this.firstLanguage = firstLanguage;
	}

	public String getLastSchoolAttempt() {
		return lastSchoolAttempt;
	}

	public void setLastSchoolAttempt(String lastSchoolAttempt) {
		this.lastSchoolAttempt = lastSchoolAttempt;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "StudentMstVo [id=" + id + ", studentName=" + studentName
				+ ", gender=" + gender + ", religion=" + religion + ", bloodGroup=" + bloodGroup + ", nationallity="
				+ nationallity + ", firstLanguage=" + firstLanguage + ", lastSchoolAttempt=" + lastSchoolAttempt
				+ ", createdOn=" + createdOn + ", createdBy=" + createdBy + ", createdByDetails=" + createdByDetails
				+ ", updatedOn=" + updatedOn + ", updatedBy=" + updatedBy + "]";
	}
	

	
}
*/