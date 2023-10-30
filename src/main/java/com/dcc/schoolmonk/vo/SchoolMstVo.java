package com.dcc.schoolmonk.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "SCHOOL_MST")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_by", "updated_by" }, allowGetters = true)
public class SchoolMstVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "school_user_id", nullable = true )
	private UserVo contactUser;

	@Column(name = "School_Name")
	private String schoolName;
	
	@Column(name = "School_ID")
	private String schoolId;		//Registration Number / Business License Number

	@Column(name = "School_Address")
	private String schoolAddress;
	
	@Column(name = "phone_country_code", length = 5)
	private String phoneCountryCode;
	
	@Column(name = "Contact_Phone")
	private String contactPhone;
	
	@Column(name = "Contact_Email")
	private String contactEmail;
	
//	@Column(name = "Contact_Person_first_name")
	@Transient
	private String contactPersonFirstName;
	
//	@Column(name = "Contact_Person_first_name")
	@Transient
	private String contactPersonLastName;
	
	//added later
	@Column(name = "Address_Line_Two")
	private String addressLineTwo;
	
	@Column(name = "Land_Mark")
	private String landMark;
	
	@Column(name = "City")
	private String city;
	
	@Column(name = "State")
	private Long state;			//Id 
	
	@Column(name = "District")
	private Long district;			//Id 
	
	@Column(name = "Postal_Code")
	private Long postalCode;
	
	@Column(name = "Min_Fees")
	private Double minFees;
	
	@Column(name = "Max_Fees")
	private Double maxFees;
	
	@Column(name = "stream", length = 20)
	private String stream;			//science, arts
	
	@Column(name = "Accreditation_By")
	private String accreditationBy;
	
	/*@Column(name = "School_Class")
	private String schoolClass;
	
	@Column(name = "Class_Type", length = 20)
	private String classType; 		//•	Primary/Secondary/Higher Secondary
	@Column(name = "Start_Class", length = 20)
	
	private String startClass;		//Nursery, 1. 2 3
	
	@Column(name = "End_Class", length = 20)
	private String endClass;		//11 12*/
	
	@Column(name = "Message_From_Principal")
	private String messageFromPrincipal;
	
	@Column(name = "About_School")
	private String aboutSchool;
	
	@Column(name = "Establishment_Year")
	private String establishmentYear;
	
	/*@Column(name = "Notices")
	private String notices;
	
	@Column(name = "Rule")
	private String rule;*/
	
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
	
	@Column(name = "School_Open_Time")
	@Temporal(TemporalType.TIMESTAMP)
//	@LastModifiedDate
	private Date schoolOpenTime;
	
	@Column(name = "School_Close_Time")
	@Temporal(TemporalType.TIMESTAMP)
//	@LastModifiedDate
	private Date schoolCloseTime;
	
	@Column(name = "School_Type", length = 20)
	private String schoolType; 		//•	Co-ed/Girls/Boys
	
	@Column(name = "School_Status", length = 20)
	private String schoolStatus = "In-Active";
	
	@Column(name = "school_status_updated_by")
	private Long schoolStatusUpdatedBy;
	
	@Column(name = "school_status_updated_on")
	private Long schoolStatusUpdatedOn;
	
	@Column(name = "Featured_School", length = 1)
	private String featuredSchool = "N";				// Y/N
	
	@Column(name = "School_Principal_Name")
	private String schoolPrincipalName;
	
	@Column(name = "School_Principal_Qualification")
	private String schoolPrincipalQualification;
	
	@Column(name="is_agree")
    private Boolean isAgree;

	@Column(name = "School_Board")
	private String schoolBoard;
	
	@Column(name = "School_Medium")
	private String schoolMedium;
    
	@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "schoolMstVo")
    private List<SchoolAcademicDtlVo> schoolAcademicDtlVo = new ArrayList<>();
	
	@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "schoolMstVo")
    private List<SchoolAdmissionDtlVo> schoolAdmissionDtlVo = new ArrayList<>();
	
	@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "schoolMstVo")
    private List<SchoolTimingDtlVo> schoolTimingDtlVo = new ArrayList<>();
	
	/*@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "schoolMstVo")
    private List<SchoolCourseFeesDtlVo> schoolCourseFeesDtlVo = new ArrayList<>();*/
	
	/*@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "schoolMstVo")
    private List<SchoolStudentEligibilityDtlVo> schoolStudentEligibilityDtlVo = new ArrayList<>();*/
	
	@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "schoolMstVo")
    private List<SchoolInfraDtlVo> schoolInfraDtlVo = new ArrayList<>();	
	
	@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "schoolMstVo")
    private List<SchoolLevelDtlVo> schoolLevelDtlVo = new ArrayList<>();	
	
	@Column(name = "custom_admission_form")
	private String customAdmissionForm = "N";
	
	@Column(name="Initiators_Name")
	private String initiatorsName;
	
	@Column(name="Initiators_email")
    private String initiatorsEmail;
	
	@Column(name="Initiators_Phone_No")
	private String initiatorsPhoneNo;

	@Transient
	List<AttachmentVo> docList = new ArrayList<AttachmentVo>();	
	@Transient
	private String schoolLogoPath;
	@Transient
	private String schoolBannerPath;
	@Transient
	private String schoolBrochurePath;
	@Transient
	private boolean isAdmissionOpen = false;
	@Transient
	private Long schoolUserId;	
	@Transient
	List<Long> idList = new ArrayList<Long>();	
	
	@Transient
	private Integer page;
	@Transient
	private Integer size;
	@Transient
	private String orderByColName;
	@Transient
	private String orderBy;
	@Transient
	private String stateName;
	@Transient
	private String districtName;
	
	@Column(name = "profile_step")
	private String profileStep;
	
	@Transient
	private Set<String> profileStepSet;
	
	@Column(name = "creation_mode")
	private String creationMode;
	
	// FOR SEO
	@Column(name = "School_Name_slug", unique = true)
	private String schoolNameSlug;
	
	@Column(name = "latitude")
	private String latitude;
	
	@Column(name = "longitude")
	private String longitude;
	
	@Column(name = "School_Rating")
	private String schoolRating;
	
	@Column(name = "favorite_users")
	private String favUsers;
	
	@Transient
	private Set<String> favUsersSet;
	
	@Transient
	private Long subscriptionId;
	
	public String getSchoolName() {
		return schoolName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<SchoolAcademicDtlVo> getSchoolAcademicDtlVo() {
		return schoolAcademicDtlVo;
	}

	public void setSchoolAcademicDtlVo(List<SchoolAcademicDtlVo> schoolAcademicDtlVo) {
		this.schoolAcademicDtlVo = schoolAcademicDtlVo;
	}

	public List<SchoolAdmissionDtlVo> getSchoolAdmissionDtlVo() {
		return schoolAdmissionDtlVo;
	}

	public void setSchoolAdmissionDtlVo(List<SchoolAdmissionDtlVo> schoolAdmissionDtlVo) {
		this.schoolAdmissionDtlVo = schoolAdmissionDtlVo;
	}

	public List<SchoolTimingDtlVo> getSchoolTimingDtlVo() {
		return schoolTimingDtlVo;
	}

	public void setSchoolTimingDtlVo(List<SchoolTimingDtlVo> schoolTimingDtlVo) {
		this.schoolTimingDtlVo = schoolTimingDtlVo;
	}

	/*public List<SchoolCourseFeesDtlVo> getSchoolCourseFeesDtlVo() {
		return schoolCourseFeesDtlVo;
	}

	public void setSchoolCourseFeesDtlVo(List<SchoolCourseFeesDtlVo> schoolCourseFeesDtlVo) {
		this.schoolCourseFeesDtlVo = schoolCourseFeesDtlVo;
	}*/

	public List<SchoolInfraDtlVo> getSchoolInfraDtlVo() {
		return schoolInfraDtlVo;
	}

	public void setSchoolInfraDtlVo(List<SchoolInfraDtlVo> schoolInfraDtlVo) {
		this.schoolInfraDtlVo = schoolInfraDtlVo;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolAddress() {
		return schoolAddress;
	}

	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getContactPersonFirstName() {
		return contactPersonFirstName;
	}

	public void setContactPersonFirstName(String contactPersonFirstName) {
		this.contactPersonFirstName = contactPersonFirstName;
	}

	public String getContactPersonLastName() {
		return contactPersonLastName;
	}

	public void setContactPersonLastName(String contactPersonLastName) {
		this.contactPersonLastName = contactPersonLastName;
	}

	/*public String getUploadDoc() {
		return uploadDoc;
	}

	public void setUploadDoc(String uploadDoc) {
		this.uploadDoc = uploadDoc;
	}*/

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

	public UserVo getContactUser() {
		return contactUser;
	}

	public void setContactUser(UserVo contactUser) {
		this.contactUser = contactUser;
	}

	public String getPhoneCountryCode() {
		return phoneCountryCode;
	}

	public void setPhoneCountryCode(String phoneCountryCode) {
		this.phoneCountryCode = phoneCountryCode;
	}

	
	public String getAddressLineTwo() {
		return addressLineTwo;
	}

	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public Long getDistrict() {
		return district;
	}

	public void setDistrict(Long district) {
		this.district = district;
	}

	public Long getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Long postalCode) {
		this.postalCode = postalCode;
	}

	public String getAccreditationBy() {
		return accreditationBy;
	}

	public void setAccreditationBy(String accreditationBy) {
		this.accreditationBy = accreditationBy;
	}

	public String getMessageFromPrincipal() {
		return messageFromPrincipal;
	}

	public void setMessageFromPrincipal(String messageFromPrincipal) {
		this.messageFromPrincipal = messageFromPrincipal;
	}

	public String getAboutSchool() {
		return aboutSchool;
	}

	public void setAboutSchool(String aboutSchool) {
		this.aboutSchool = aboutSchool;
	}

	/*public String getNotices() {
		return notices;
	}

	public void setNotices(String notices) {
		this.notices = notices;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}*/
	
	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public List<AttachmentVo> getDocList() {
		return docList;
	}

	public void setDocList(List<AttachmentVo> docList) {
		this.docList = docList;
	}

	public Date getSchoolOpenTime() {
		return schoolOpenTime;
	}

	public void setSchoolOpenTime(Date schoolOpenTime) {
		this.schoolOpenTime = schoolOpenTime;
	}

	public Date getSchoolCloseTime() {
		return schoolCloseTime;
	}

	public void setSchoolCloseTime(Date schoolCloseTime) {
		this.schoolCloseTime = schoolCloseTime;
	}

	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	public String getSchoolLogoPath() {
		return schoolLogoPath;
	}

	public void setSchoolLogoPath(String schoolLogoPath) {
		this.schoolLogoPath = schoolLogoPath;
	}

	public String getSchoolBannerPath() {
		return schoolBannerPath;
	}

	public void setSchoolBannerPath(String schoolBannerPath) {
		this.schoolBannerPath = schoolBannerPath;
	}

	public String getSchoolBrochurePath() {
		return schoolBrochurePath;
	}

	public void setSchoolBrochurePath(String schoolBrochurePath) {
		this.schoolBrochurePath = schoolBrochurePath;
	}

	public boolean isAdmissionOpen() {
		return isAdmissionOpen;
	}

	public void setAdmissionOpen(boolean isAdmissionOpen) {
		this.isAdmissionOpen = isAdmissionOpen;
	}

	public String getSchoolStatus() {
		return schoolStatus;
	}

	public void setSchoolStatus(String schoolStatus) {
		this.schoolStatus = schoolStatus;
	}

	public Long getSchoolUserId() {
		return schoolUserId;
	}

	public void setSchoolUserId(Long schoolUserId) {
		this.schoolUserId = schoolUserId;
	}

	public String getFeaturedSchool() {
		return featuredSchool;
	}

	public void setFeaturedSchool(String featuredSchool) {
		this.featuredSchool = featuredSchool;
	}

	public String getSchoolPrincipalName() {
		return schoolPrincipalName;
	}

	public void setSchoolPrincipalName(String schoolPrincipalName) {
		this.schoolPrincipalName = schoolPrincipalName;
	}

	public String getSchoolPrincipalQualification() {
		return schoolPrincipalQualification;
	}

	public void setSchoolPrincipalQualification(String schoolPrincipalQualification) {
		this.schoolPrincipalQualification = schoolPrincipalQualification;
	}

	public String getEstablishmentYear() {
		return establishmentYear;
	}

	public void setEstablishmentYear(String establishmentYear) {
		this.establishmentYear = establishmentYear;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public List<SchoolLevelDtlVo> getSchoolLevelDtlVo() {
		return schoolLevelDtlVo;
	}

	public void setSchoolLevelDtlVo(List<SchoolLevelDtlVo> schoolLevelDtlVo) {
		this.schoolLevelDtlVo = schoolLevelDtlVo;
	}

	/*public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}*/

	public Long getSchoolStatusUpdatedBy() {
		return schoolStatusUpdatedBy;
	}

	public void setSchoolStatusUpdatedBy(Long schoolStatusUpdatedBy) {
		this.schoolStatusUpdatedBy = schoolStatusUpdatedBy;
	}

	public Long getSchoolStatusUpdatedOn() {
		return schoolStatusUpdatedOn;
	}

	public void setSchoolStatusUpdatedOn(Long schoolStatusUpdatedOn) {
		this.schoolStatusUpdatedOn = schoolStatusUpdatedOn;
	}

	public String getCustomAdmissionForm() {
		return customAdmissionForm;
	}

	public void setCustomAdmissionForm(String customAdmissionForm) {
		this.customAdmissionForm = customAdmissionForm;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}
	
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getOrderByColName() {
		return orderByColName;
	}

	public void setOrderByColName(String orderByColName) {
		this.orderByColName = orderByColName;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	

	public String getProfileStep() {
		return profileStep;
	}

	public void setProfileStep(String profileStep) {
		this.profileStep = profileStep;
	}

	public Set<String> getProfileStepSet() {
		return profileStepSet;
	}

	public void setProfileStepSet(Set<String> profileStepSet) {
		this.profileStepSet = profileStepSet;
	}

	
	
	//script by SD, all of them are details of school master table
	/*ALTER TABLE `schoolmonkdb`.`SCHOOL_MST` 
	DROP COLUMN `School_Media`,
	DROP COLUMN `Rule`,
	DROP COLUMN `Notices`,
	DROP COLUMN `School_Infrastructure`,
	DROP COLUMN `Upload_Doc`;
	
	ALTER TABLE `schoolmonkdb`.`SCHOOL_MST` 
DROP COLUMN `Admission_Start_Date`,
DROP COLUMN `Admission_End_Date`;

	
	ALTER TABLE `schoolmonkdb`.`SCHOOL_MST` 
		DROP COLUMN `stream`,
		DROP COLUMN `Start_Class`,
		DROP COLUMN `End_Class`,
		DROP COLUMN `Class_Type`,
		DROP COLUMN `School_Class`,
		DROP COLUMN `Medium`,
		DROP COLUMN `Board`;
	*/

//	added by Akash	
	public String getSchoolBoard() {
		return schoolBoard;
	}

	public void setSchoolBoard(String schoolBoard) {
		this.schoolBoard = schoolBoard;
	}

	public String getSchoolMedium() {
		return schoolMedium;
	}

	public void setSchoolMedium(String schoolMedium) {
		this.schoolMedium = schoolMedium;
	}

	public String getCreationMode() {
		return creationMode;
	}

	public void setCreationMode(String creationMode) {
		this.creationMode = creationMode;
	}

	public Double getMinFees() {
		return minFees;
	}

	public void setMinFees(Double minFees) {
		this.minFees = minFees;
	}

	public Double getMaxFees() {
		return maxFees;
	}

	public void setMaxFees(Double maxFees) {
		this.maxFees = maxFees;
	}

	public String getSchoolNameSlug() {
		return schoolNameSlug;
	}

	public void setSchoolNameSlug(String schoolNameSlug) {
		this.schoolNameSlug = schoolNameSlug;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getSchoolRating() {
		return schoolRating;
	}

	public void setSchoolRating(String schoolRating) {
		this.schoolRating = schoolRating;
	}
	
	

	public String getFavUsers() {
		return favUsers;
	}

	public Set<String> getFavUsersSet() {
		return favUsersSet;
	}

	public void setFavUsers(String favUsers) {
		this.favUsers = favUsers;
	}

	public void setFavUsersSet(Set<String> favUsersSet) {
		this.favUsersSet = favUsersSet;
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Boolean getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Boolean isAgree) {
		this.isAgree = isAgree;
	}

	

	public String getInitiatorsName() {
		return initiatorsName;
	}

	public void setInitiatorsName(String initiatorsName) {
		this.initiatorsName = initiatorsName;
	}

	public String getInitiatorsEmail() {
		return initiatorsEmail;
	}

	public void setInitiatorsEmail(String initiatorsEmail) {
		this.initiatorsEmail = initiatorsEmail;
	}

	public String getInitiatorsPhoneNo() {
		return initiatorsPhoneNo;
	}

	public void setInitiatorsPhoneNo(String initiatorsPhoneNo) {
		this.initiatorsPhoneNo = initiatorsPhoneNo;
	}

	@Override
	public String toString() {
		return "SchoolMstVo [id=" + id + ", contactUser=" + contactUser + ", schoolName=" + schoolName + ", schoolId="
				+ schoolId + ", schoolAddress=" + schoolAddress + ", phoneCountryCode=" + phoneCountryCode
				+ ", contactPhone=" + contactPhone + ", contactEmail=" + contactEmail + ", contactPersonFirstName="
				+ contactPersonFirstName + ", contactPersonLastName=" + contactPersonLastName + ", addressLineTwo="
				+ addressLineTwo + ", landMark=" + landMark + ", city=" + city + ", state=" + state + ", district="
				+ district + ", postalCode=" + postalCode + /*", board=" + board + ", medium=" + medium +*/ ", stream="
				+ stream + ", accreditationBy=" + accreditationBy + ", messageFromPrincipal=" + messageFromPrincipal
				+ ", aboutSchool=" + aboutSchool + ", establishmentYear=" + establishmentYear + ", createdOn="
				+ createdOn + ", createdBy=" + createdBy + ", createdByDetails=" + createdByDetails + ", updatedOn="
				+ updatedOn + ", updatedBy=" + updatedBy + ", schoolOpenTime=" + schoolOpenTime + ", schoolCloseTime="
				+ schoolCloseTime + ", schoolType=" + schoolType + ", schoolStatus=" + schoolStatus
				+ ", schoolStatusUpdatedBy=" + schoolStatusUpdatedBy + ", schoolStatusUpdatedOn="
				+ schoolStatusUpdatedOn + ", featuredSchool=" + featuredSchool + ", schoolPrincipalName="
				+ schoolPrincipalName + ", schoolPrincipalQualification=" + schoolPrincipalQualification
				+ ", schoolLevelDtlVo=" + schoolLevelDtlVo + ", customAdmissionForm=" + customAdmissionForm
				+ ", docList=" + docList + ", schoolLogoPath=" + schoolLogoPath + ", schoolBannerPath="
				+ schoolBannerPath + ", schoolBrochurePath=" + schoolBrochurePath + ", isAdmissionOpen="
				+ isAdmissionOpen + ", schoolUserId=" + schoolUserId + ", idList=" + idList + ", page=" + page
				+ ", size=" + size + ", orderByColName=" + orderByColName + ", orderBy=" + orderBy + ", stateName="
				+ stateName + ", districtName=" + districtName + ", profileStep=" + profileStep + ", profileStepSet="
				+ profileStepSet + ", creationMode=" + creationMode + ", schoolBoard=" + schoolBoard + ", schoolMedium="
				+ schoolMedium + "]";
	}
}
