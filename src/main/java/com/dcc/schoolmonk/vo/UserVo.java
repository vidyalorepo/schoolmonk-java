package com.dcc.schoolmonk.vo;

 

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Transient;

 

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

 

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

 

@Entity
@Table(name = "USER_DETAILS")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_by", "updated_by" }, allowGetters = true)
public class UserVo implements Serializable, UserDetails {

 

	private static final long serialVersionUID = 1L;

 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

 

	@Column(name = "first_name")
	private String firstName;

 

	@Column(name = "last_name")
	private String lastName;

 

	@Column(name = "email", unique = true)
	private String email;

 

	// This is mapped to the mobile No
	@Column(name = "phone_country_code", length = 5)
	private String phoneCountryCode;

 

	@Column(name = "phone", unique = true)
	private String phone;

 

	@JsonIgnore
	@Column(name = "password")
	private String password;

 

	// @JsonIgnore
	@Column(name = "isactive")
	private Boolean isActive = false;

 

	@Transient
	private String jwtToken;

 

	@Column(name = "reset_token")
	private String resetToken;

 

	/* Spring Security related fields */

 

	@JsonIgnore
	@Transient
	private List<Role> authorities;

 

	@JsonIgnore
	@Transient
	private boolean accountNonExpired = true;

 

	@JsonIgnore
	@Transient
	private boolean accountNonLocked = true;

 

	@JsonIgnore
	@Transient
	private boolean credentialsNonExpired = true;

 

	@JsonIgnore
	@Transient
	private boolean enabled = true;

 

	@Column(name = "OTP")
	private String otp;

 

	@Column(name = "country_code")
	private String countryCode;

 

	@Column(name = "pin_code")
	private String pincode;

 

	@Column(name = "user_type")
	private String userType;		

 

	/*@Column(name = "date_of_birth")
	private String dateOfBirth;

 

	@Column(name = "father_name")
	private String fatherName;*/

 

	@Column(name = "token")
	private String token;

 

	@Column(name = "School_Id")
	private Long schoolId;

 

	@Transient
	private Long studentId;

	//join
	@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parentUser")
    private List<StudentMstVo> studentMstVo = new ArrayList<>();

//	@JsonManagedReference
//    @OneToOne(mappedBy = "customerId")
//    private AdsOrderDetailsVo adsOrderDetailsVo ;

 

	@Column(name = "otp_expiry_time")
	private Long otpExpiryTime;

	@JsonIgnore
	@Column(name = "subscriber_id")
	private String subscriberId;

 

	public Long getUserId() {
		return userId;
	}

 

	public void setUserId(Long userId) {
		this.userId = userId;
	}

 

	public String getFirstName() {
		return firstName;
	}

 

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

 

	public String getLastName() {
		return lastName;
	}

 

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

 

	public String getEmail() {
		return email;
	}

 

	public void setEmail(String email) {
		this.email = email;
	}

 

	public String getPhone() {
		return phone;
	}

 

	public void setPhone(String phone) {
		this.phone = phone;
	}

 

	public String getJwtToken() {
		return jwtToken;
	}

 

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

 

	public String getPassword() {
		return password;
	}

 

	public void setPassword(String password) {
		this.password = password;
	}

 

	public Boolean getIsActive() {
		return isActive;
	}

 

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

 

	public List<Role> getAuthorities() {
		return authorities;
	}

 

	public void setAuthorities(List<Role> authorities) {
		this.authorities = authorities;
	}

 

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

 

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

 

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

 

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

 

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

 

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

 

	public boolean isEnabled() {
		return enabled;
	}

 

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

 

	public String getResetToken() {
		return resetToken;
	}

 

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

 

	public String getOtp() {
		return otp;
	}

 

	public void setOtp(String otp) {
		this.otp = otp;
	}

 

	public String getCountryCode() {
		return countryCode;
	}

 

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

 

	public String getPincode() {
		return pincode;
	}

 

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

 

	public String getUserType() {
		return userType;
	}

 

	public void setUserType(String userType) {
		this.userType = userType;
	}

 

	public String getPhoneCountryCode() {
		return phoneCountryCode;
	}

 

	public void setPhoneCountryCode(String phoneCountryCode) {
		this.phoneCountryCode = phoneCountryCode;
	}

 

	/*public String getDateOfBirth() {
		return dateOfBirth;
	}

 

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

 

	public String getFatherName() {
		return fatherName;
	}

 

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}*/

 

	public String getToken() {
		return token;
	}

 

	public void setToken(String token) {
		this.token = token;
	}

 

	public Long getSchoolId() {
		return schoolId;
	}

 

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

 

	public Long getStudentId() {
		return studentId;
	}

 

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

 

	public Long getOtpExpiryTime() {
		return otpExpiryTime;
	}

 

	public void setOtpExpiryTime(Long otpExpiryTime) {
		this.otpExpiryTime = otpExpiryTime;
	}

 

	public List<StudentMstVo> getStudentMstVo() {
		return studentMstVo;
	}

 

	public void setStudentMstVo(List<StudentMstVo> studentMstVo) {
		this.studentMstVo = studentMstVo;
	}

 

	public String getSubscriberId() {
		return subscriberId;
	}

 

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

 

	@Override
	public String toString() {
		return "UserVo [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phoneCountryCode=" + phoneCountryCode + ", phone=" + phone + ", password=" + password
				+ ", isActive=" + isActive + ", jwtToken=" + jwtToken + ", resetToken=" + resetToken + ", authorities="
				+ authorities + ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled + ", otp=" + otp
				+ ", countryCode=" + countryCode + ", pincode=" + pincode + ", userType=" + userType 
				+ ", token=" + token + ", schoolId=" + schoolId + "]";
	}

 

	@Override
	public String getUsername() {
		return email;
	}

 

//	public AdsOrderDetailsVo getAdsOrderDetailsVo() {
//		return adsOrderDetailsVo;
//	}
//
//	public void setAdsOrderDetailsVo(AdsOrderDetailsVo adsOrderDetailsVo) {
//		this.adsOrderDetailsVo = adsOrderDetailsVo;
//	}

 

 

	

}

