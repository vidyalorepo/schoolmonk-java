package com.dcc.schoolmonk.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "JOIN_SCHOOL")
@EntityListeners(AuditingEntityListener.class)
public class SchoolAddVo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "School_User_Name")
	private String schoolUserName;
	
	@Column(name = "Designation")
	private String designation;
	
	@Column(name = "School_User_Phone")
	private String phone;
	
	@Column(name = "School_User_Email")
	private String email;
	
	@Column(name = "School_Name")
	private String schoolName;
	
	@Column(name = "School_location")
	private String location;
	
	@Column(name ="is_agree")
	private Boolean isAgree;
	public Long getId() {
		return id;
	}

	public String getSchoolUserName() {
		return schoolUserName;
	}

	public String getDesignation() {
		return designation;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public String getLocation() {
		return location;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSchoolUserName(String schoolUserName) {
		this.schoolUserName = schoolUserName;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public void setLocation(String location) {
		this.location = location;
		
	}
	
	public Boolean getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(Boolean isAgree) {
        this.isAgree = isAgree;
    } 

	@Override
	public String toString() {
		return "SchoolAddVo [id=" + id + ", schoolUserName=" + schoolUserName + ", designation=" + designation
				+ ", phone=" + phone + ", email=" + email + ", schoolName=" + schoolName + ", location=" + location
				+ ", isAgree=" + isAgree +" ]";
	}

		
}
