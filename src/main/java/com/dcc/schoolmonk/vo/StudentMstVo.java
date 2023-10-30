package com.dcc.schoolmonk.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "STUDENT_MST")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_by", "updated_by" }, allowGetters = true)
public class StudentMstVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Student_Name")
	private String studentName;
	
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
	
	/*@OneToOne
    @JoinColumn(name = "Student_User_Id", nullable = false )
	private UserVo studentUser;*/
	//join
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "Parent_User_Id", nullable = false )
    @JsonBackReference
    public UserVo parentUser;
	
	@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "studentMstVo")
    private List<StudentGuardianVo> studentGuardianVo = new ArrayList<>();
	
	@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "studentMstVoForSibling")
    private List<StudentSiblingVo> studentSiblingVo = new ArrayList<>();
	
	@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "studentMstVoAdd")
    private List<AddressVo> addressVo = new ArrayList<>();
	
	@Transient
	private String studentPhotoPath;
	
	@Transient
	private String guardianPhotoPath;
	
	@Transient
	private String birthCertificatePath;
	
	@Transient
	private String lastMarksheetPath;
	
	@Transient
	private Long schoolId;
	
	@Transient
	List<AttachmentVo> docList = new ArrayList<AttachmentVo>();
	
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
	
	@Transient
	private String admissionForBoard;
	
	@Transient
	private String academicYear;
	
	@Transient
	private String admissionForClass;
	
	@Transient
	private String admissionForStream;
	
	@Transient
	private String wizardFormNo;
	
	@Transient
	private String admissionEndDate;
	
//	@JsonManagedReference
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "studentMstVo")
//    private List<SchoolStudentMappingVo> schoolStudentMappingVo = new ArrayList<>();

	public String getAdmissionForBoard() {
		return admissionForBoard;
	}

	public void setAdmissionForBoard(String admissionForBoard) {
		this.admissionForBoard = admissionForBoard;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getAdmissionForClass() {
		return admissionForClass;
	}

	public void setAdmissionForClass(String admissionForClass) {
		this.admissionForClass = admissionForClass;
	}

	public String getAdmissionForStream() {
		return admissionForStream;
	}

	public void setAdmissionForStream(String admissionForStream) {
		this.admissionForStream = admissionForStream;
	}

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
	
	

	public List<StudentGuardianVo> getStudentGuardianVo() {
		return studentGuardianVo;
	}

	public void setStudentGuardianVo(List<StudentGuardianVo> studentGuardianVo) {
		this.studentGuardianVo = studentGuardianVo;
	}

	public List<StudentSiblingVo> getStudentSiblingVo() {
		return studentSiblingVo;
	}

	public void setStudentSiblingVo(List<StudentSiblingVo> studentSiblingVo) {
		this.studentSiblingVo = studentSiblingVo;
	}

	public List<AddressVo> getAddressVo() {
		return addressVo;
	}

	public void setAddressVo(List<AddressVo> addressVo) {
		this.addressVo = addressVo;
	}

	/*public UserVo getStudentUser() {
		return studentUser;
	}

	public void setStudentUser(UserVo studentUser) {
		this.studentUser = studentUser;
	}*/

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getStudentPhotoPath() {
		return studentPhotoPath;
	}

	public void setStudentPhotoPath(String studentPhotoPath) {
		this.studentPhotoPath = studentPhotoPath;
	}

	public String getGuardianPhotoPath() {
		return guardianPhotoPath;
	}

	public void setGuardianPhotoPath(String guardianPhotoPath) {
		this.guardianPhotoPath = guardianPhotoPath;
	}

	public String getBirthCertificatePath() {
		return birthCertificatePath;
	}

	public void setBirthCertificatePath(String birthCertificatePath) {
		this.birthCertificatePath = birthCertificatePath;
	}

	public String getLastMarksheetPath() {
		return lastMarksheetPath;
	}

	public void setLastMarksheetPath(String lastMarksheetPath) {
		this.lastMarksheetPath = lastMarksheetPath;
	}

	public List<AttachmentVo> getDocList() {
		return docList;
	}

	public void setDocList(List<AttachmentVo> docList) {
		this.docList = docList;
	}

	public UserVo getParentUser() {
		return parentUser;
	}

	public void setParentUser(UserVo parentUser) {
		this.parentUser = parentUser;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getWizardFormNo() {
		return wizardFormNo;
	}

	public void setWizardFormNo(String wizardFormNo) {
		this.wizardFormNo = wizardFormNo;
	}

	public String getAdmissionEndDate() {
		return admissionEndDate;
	}

	public void setAdmissionEndDate(String admissionEndDate) {
		this.admissionEndDate = admissionEndDate;
	}


//	public List<SchoolStudentMappingVo> getSchoolStudentMappingVo() {
//		return schoolStudentMappingVo;
//	}
//
//	public void setSchoolStudentMappingVo(List<SchoolStudentMappingVo> schoolStudentMappingVo) {
//		this.schoolStudentMappingVo = schoolStudentMappingVo;
//	}

	@Override
	public String toString() {
		return "StudentMstVo [id=" + id + ", studentName=" + studentName
				+ ", gender=" + gender + ", religion=" + religion + ", bloodGroup=" + bloodGroup + ", nationallity="
				+ nationallity + ", firstLanguage=" + firstLanguage + ", lastSchoolAttempt=" + lastSchoolAttempt
				+ ", createdOn=" + createdOn + ", createdBy=" + createdBy + ", createdByDetails=" + createdByDetails
				+ ", updatedOn=" + updatedOn + ", updatedBy=" + updatedBy + "]";
	}
}
