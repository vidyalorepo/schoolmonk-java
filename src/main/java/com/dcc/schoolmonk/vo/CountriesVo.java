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
@Table(name = "COUNTRIES")
@EntityListeners(AuditingEntityListener.class)
public class CountriesVo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "numeric_code")
	private String numericCode;
	
	@Column(name = "phonecode")
	private String phonecode;
	
	@Column(name = "capital")
	private String capital;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "currency_name")
	private String currencyName;
	
	@Column(name = "currency_symbol")
	private String currencySymbol;
	
	@Column(name = "native")
	private String _native;
	
	@Column(name = "region")
	private String region;
	
	@Column(name = "subregion")
	private String subregion;

	@Column(name = "latitude")
	private String latitude;
	
	@Column(name = "longitude")
	private String longitude;
	
	@Column(name = "emoji")
	private String emoji;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNumericCode() {
		return numericCode;
	}

	public String getPhonecode() {
		return phonecode;
	}

	public String getCapital() {
		return capital;
	}

	public String getCurrency() {
		return currency;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public String get_native() {
		return _native;
	}

	public String getRegion() {
		return region;
	}

	public String getSubregion() {
		return subregion;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getEmoji() {
		return emoji;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumericCode(String numericCode) {
		this.numericCode = numericCode;
	}

	public void setPhonecode(String phonecode) {
		this.phonecode = phonecode;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public void set_native(String _native) {
		this._native = _native;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setSubregion(String subregion) {
		this.subregion = subregion;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setEmoji(String emoji) {
		this.emoji = emoji;
	}

	@Override
	public String toString() {
		return "CountriesVo [id=" + id + ", name=" + name + ", numericCode=" + numericCode + ", phonecode=" + phonecode
				+ ", capital=" + capital + ", currency=" + currency + ", currencyName=" + currencyName
				+ ", currencySymbol=" + currencySymbol + ", _native=" + _native + ", region=" + region + ", subregion="
				+ subregion + ", latitude=" + latitude + ", longitude=" + longitude + ", emoji=" + emoji + "]";
	}
	
	
	

}
