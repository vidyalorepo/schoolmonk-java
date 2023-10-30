package com.dcc.schoolmonk.vo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity

@Table(name = "ADS_ZONE_MST")

@EntityListeners(AuditingEntityListener.class)

public class AdszoneMstVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	@Column(name = "zone_name", length = 255 ,unique = true)

	private String zoneName;

	@Column(name = "page", length = 255)

	private String page;

	@Column(name = "no_of_ad")

	private Integer noOfAd;

	@Column(name = "width_px", length = 255)

	private String widthPx;

	@Column(name = "height_px", length = 255)

	private String heightPx;

	@Column(name = "price")

	private Long price;

	@Column(name = "description", length = 255)

	private String description;

	@Column(name = "is_Active")

	private Boolean isActive;

	public Long getId() {

		return id;

	}

	public void setId(Long id) {

		this.id = id;

	}

	public String getZoneName() {

		return zoneName;

	}

	public void setZoneName(String zoneName) {

		this.zoneName = zoneName;

	}

	public String getPage() {

		return page;

	}

	public void setPage(String page) {

		this.page = page;

	}

	public Integer getNoOfAd() {

		return noOfAd;

	}

	public void setNoOfAd(Integer noOfAd) {

		this.noOfAd = noOfAd;

	}

	public String getWidthPx() {

		return widthPx;

	}

	public void setWidthPx(String widthPx) {

		this.widthPx = widthPx;

	}

	public String getHeightPx() {

		return heightPx;

	}

	public void setHeightPx(String heightPx) {

		this.heightPx = heightPx;

	}

	public Long getPrice() {

		return price;

	}

	public void setPrice(Long price) {

		this.price = price;

	}

	public String getDescription() {

		return description;

	}

	public void setDescription(String description) {

		this.description = description;

	}

	public Boolean getIsActive() {

		return isActive;

	}

	public void setIsActive(Boolean isActive) {

		this.isActive = isActive;

	}

	public static long getSerialversionuid() {

		return serialVersionUID;

	}

}