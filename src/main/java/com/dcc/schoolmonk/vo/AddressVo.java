package com.dcc.schoolmonk.vo;

import java.io.Serializable;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ADDRESS_DTL")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_on", "created_by" }, allowGetters = true)
public class AddressVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;
	
	@Column(name = "Add_Line_One")
	private String addrLineOne;

	@Column(name = "Add_Line_Two")
	private String addrLineTwo;
	
	@Column(name = "Country")
	private String country;
	
	@Column(name = "State")
	private String state;
	
	@Column(name = "City")
	private String city;
	
	@Column(name = "District")
	private String district;
	
	@Column(name = "Pin")
	private Long pin;
	
	@Column(name = "Address_Type")
	private String addressType;
	
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "Student_Id", nullable = false )
    @JsonBackReference
    public StudentMstVo studentMstVoAdd;
	
	@Transient
	private String stateName;
	@Transient
	private String districtName;

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getAddrLineOne() {
		return addrLineOne;
	}

	public void setAddrLineOne(String addrLineOne) {
		this.addrLineOne = addrLineOne;
	}

	public String getAddrLineTwo() {
		return addrLineTwo;
	}

	public void setAddrLineTwo(String addrLineTwo) {
		this.addrLineTwo = addrLineTwo;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Long getPin() {
		return pin;
	}

	public void setPin(Long pin) {
		this.pin = pin;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public StudentMstVo getStudentMstVoAdd() {
		return studentMstVoAdd;
	}

	public void setStudentMstVoAdd(StudentMstVo studentMstVoAdd) {
		this.studentMstVoAdd = studentMstVoAdd;
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

}
