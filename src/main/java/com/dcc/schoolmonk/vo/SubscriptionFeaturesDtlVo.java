 package com.dcc.schoolmonk.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

 @Entity
 @Table(name = "SUB_FEATURES_DTL")
 @EntityListeners(AuditingEntityListener.class)
public class SubscriptionFeaturesDtlVo {
	 
 	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Feature_Name")
	private String featureName;
	
	@Column(name = "Feature_Desc")
	private String featureDesc;
	
	@Column(name = "Feature_Level")
	private String featureLevel;
	
	@Column(name = "Feature_Status")
	private String featureStatus;
	
	@Column(name = "Extra_Pay_Feature_Status")
	private String extraPayfeatureStatus;
	
//	@ManyToOne( fetch = FetchType.EAGER )
//    @JoinColumn(name = "Subscription_Id", nullable = false )
//    @JsonBackReference
//	private SubcriptionMstVo subcriptionMstVo;

	@JsonIgnore
	@Column(name = "Subscription_Id_Str")
	private String subscriptionIdStr;
	
	@Column(name = "Range_Any")
	private String rangeAny;
	
	
	public Long getId() {
		return id;
	}

	public String getFeatureName() {
		return featureName;
	}

	public String getFeatureDesc() {
		return featureDesc;
	}

	public String getFeatureLevel() {
		return featureLevel;
	}

//	public SubcriptionMstVo getSubcriptionMstVo() {
//		return subcriptionMstVo;
//	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public void setFeatureDesc(String featureDesc) {
		this.featureDesc = featureDesc;
	}

	public void setFeatureLevel(String featureLevel) {
		this.featureLevel = featureLevel;
	}

//	public void setSubcriptionMstVo(SubcriptionMstVo subcriptionMstVo) {
//		this.subcriptionMstVo = subcriptionMstVo;
//	}

	public String getFeatureStatus() {
		return featureStatus;
	}

	public String getExtraPayfeatureStatus() {
		return extraPayfeatureStatus;
	}

	public void setFeatureStatus(String featureStatus) {
		this.featureStatus = featureStatus;
	}

	public void setExtraPayfeatureStatus(String extraPayfeatureStatus) {
		this.extraPayfeatureStatus = extraPayfeatureStatus;
	}

	public String getSubscriptionIdStr() {
		return subscriptionIdStr;
	}

	public void setSubscriptionIdStr(String subscriptionIdStr) {
		this.subscriptionIdStr = subscriptionIdStr;
	}

	public String getRangeAny() {
		return rangeAny;
	}

	public void setRangeAny(String rangeAny) {
		this.rangeAny = rangeAny;
	}

	@Override
	public String toString() {
		return "SubscriptionFeaturesDtlVo [id=" + id + ", featureName=" + featureName + ", featureDesc="
				+ featureDesc + ", featureLevel=" + featureLevel + ", featureStatus=" + featureStatus
				+ ", extraPayfeatureStatus=" + extraPayfeatureStatus
				+ "]";
	}
		
		

}
