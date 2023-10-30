package com.dcc.schoolmonk.vo;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AdsOrderVo {
	
	@Id
	private long adId;

	private String zoneName;

	private String page;

	private Integer noOfAd;

	private String widthPx;
	
	private String heightPx;

	private String isavailable;

	private String description;
	
    private Integer price;
    
	private Boolean active;


	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getIsavailable() {
		return isavailable;
	}

	public void setIsavailable(String isavailable) {
		this.isavailable = isavailable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getHeightPx() {
		return heightPx;
	}

	public void setHeightPx(String heightPx) {
		this.heightPx = heightPx;
	}

	public long getAdId() {
		return adId;
	}

	public void setAdId(long adId) {
		this.adId = adId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
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
	
	
}
