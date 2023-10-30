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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "SCHOOL_MST_BULK")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_by", "updated_by" }, allowGetters = true)
public class SchoolMstBulkVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Null
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "school_user_id")
	private UserVo contactUser;

	@Column(name = "School_Name")
	private String schoolName;

	@Column(name = "School_ID")
	private String schoolId; // Registration Number / Business License Number

	@Column(name = "School_Principal_Name")
	private String schoolPrincipalName;

	@Column(name = "School_Principal_Qualification")
	private String schoolPrincipalQualification;

	// @Column(name = "Contact_Person_first_name")
	@Transient
	private String contactPersonFirstName;

	// @Column(name = "Contact_Person_first_name")
	@Transient
	private String contactPersonLastName;

	@Column(name = "phone_country_code", length = 5)
	private String phoneCountryCode;

	@Column(name = "Contact_Phone", unique = true)
	// @Column(name = "Contact_Phone")
	private String contactPhone;

	@Column(name = "Contact_Email")
	private String contactEmail;

	@Column(name = "Establishment_Year")
	private String establishmentYear;

	@Column(name = "School_Type", length = 20)
	private String schoolType; // • Co-ed/Girls/Boys

	@Column(name = "School_Board")
	private String schoolBoard;

	@Column(name = "School_Medium")
	private String schoolMedium;

	@Column(name = "Message_From_Principal")
	private String messageFromPrincipal;

	@Column(name = "About_School")
	private String aboutSchool;

	@Column(name = "Featured_School", length = 1)
	private String featuredSchool = "N"; // Y/N

	@Column(name = "custom_admission_form")
	private String customAdmissionForm = "N";

	@Column(name = "School_Address")
	private String schoolAddress;

	// added later
	@Column(name = "Address_Line_Two")
	private String addressLineTwo;

	@Column(name = "Land_Mark")
	private String landMark;

	@Column(name = "City")
	private String city;

	@Column(name = "State")
	private Long state; // Id

	@Column(name = "District")
	private Long district; // Id

	@Column(name = "Postal_Code")
	private Long postalCode;

	@Column(name = "created_on", nullable = false, updatable = false)
	// @Temporal(TemporalType.TIMESTAMP)
	// @CreatedDate

	private Date createdOn;

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
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

	public String getPhoneCountryCode() {
		return phoneCountryCode;
	}

	public void setPhoneCountryCode(String phoneCountryCode) {
		this.phoneCountryCode = phoneCountryCode;
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

	public String getEstablishmentYear() {
		return establishmentYear;
	}

	public void setEstablishmentYear(String establishmentYear) {
		this.establishmentYear = establishmentYear;
	}

	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

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

	public String getFeaturedSchool() {
		return featuredSchool;
	}

	public void setFeaturedSchool(String featuredSchool) {
		this.featuredSchool = featuredSchool;
	}

	public String getCustomAdmissionForm() {
		return customAdmissionForm;
	}

	public void setCustomAdmissionForm(String customAdmissionForm) {
		this.customAdmissionForm = customAdmissionForm;
	}

	public String getSchoolAddress() {
		return schoolAddress;
	}

	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}

	public String getAddressLineTwo() {
		return addressLineTwo;
	}

	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
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

	public UserVo getContactUser() {
		return contactUser;
	}

	public void setContactUser(UserVo contactUser) {
		this.contactUser = contactUser;
	}

	@Override
	public String toString() {
		return "SchoolMstBulkVo [id=" + id + ", schoolName=" + schoolName + ", schoolId=" + schoolId
				+ ", schoolPrincipalName=" + schoolPrincipalName + ", schoolPrincipalQualification="
				+ schoolPrincipalQualification + ", contactPersonFirstName=" + contactPersonFirstName
				+ ", contactPersonLastName=" + contactPersonLastName + ", phoneCountryCode=" + phoneCountryCode
				+ ", contactPhone=" + contactPhone + ", contactEmail=" + contactEmail + ", establishmentYear="
				+ establishmentYear + ", schoolType=" + schoolType + ", schoolBoard=" + schoolBoard + ", schoolMedium="
				+ schoolMedium + ", messageFromPrincipal=" + messageFromPrincipal + ", aboutSchool=" + aboutSchool
				+ ", featuredSchool=" + featuredSchool + ", customAdmissionForm=" + customAdmissionForm
				+ ", schoolAddress=" + schoolAddress + ", addressLineTwo=" + addressLineTwo + ", landMark=" + landMark
				+ ", city=" + city + ", state=" + state + ", district=" + district + ", postalCode=" + postalCode
				+ ", contactUser=" + contactUser + ", createdOn=" + createdOn + "]";
	}

}
