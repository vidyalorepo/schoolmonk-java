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
@Table(name = "STATES")
@EntityListeners(AuditingEntityListener.class)
public class StatesVo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String stateName;
	
	@Column(name = "country_id")
	private Long countryId;
	
	@Column(name = "country_code")
	private String countryCode;
	
	@Column(name = "fips_code")
	private String fipsCode;
	
	@Column(name = "latitude")
	private String latitude;
	
	@Column(name = "longitude")
	private String longitude;

	public Long getId() {
		return id;
	}

	public String getStateName() {
		return stateName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getFipsCode() {
		return fipsCode;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public void setFipsCode(String fipsCode) {
		this.fipsCode = fipsCode;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	@Override
	public String toString() {
		return "StatesVo [id=" + id + ", stateName=" + stateName + ", countryCode=" + countryCode + ", fipsCode="
				+ fipsCode + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
	

}
