package com.dcc.schoolmonk.vo;

import java.util.ArrayList;
import java.util.Date;
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
@Table(name = "SUBSCRIPTION_MST")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_by", "updated_by" }, allowGetters = true)
public class SubcriptionMstVo {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Subscription_Name")
	private String subscriptionName; // Exp -->> Default/Silver...etc 
	
	@Column(name = "Subscription_Type")
	private String subscriptionType; // Backend side references for subscriptionName
	
	@Column(name = "Subscription_User_Type")
	private String subscriptionUserType; // User Type Subscriptions
	
	@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "subcriptionMstVo")
    private List<SubscriptionAssociatedVo> subscriptionAssociatedVo = new ArrayList<>();
	
//	@JsonManagedReference
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "subcriptionMstVo")
//    private List<SubscriptionFeaturesDtlVo> subscriptionFeaturesDtlVo = new ArrayList<>();
	
	
	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdOn;

	@Column(name = "created_by")
	private Long createdBy;

	@Transient
	private UserVo createdByDetails;

	@Column(name = "updated_on")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedOn;

	@Column(name = "updated_by")
	private Long updatedBy;
	
	
	@Transient
	private Long userId;
	
	@Transient
	private List<SubscriptionFeaturesDtlVo> subscriptionFeaturesDtlVo;

	public Long getId() {
		return id;
	}

	public String getSubscriptionName() {
		return subscriptionName;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

//	public List<SubscriptionFeaturesDtlVo> getSubscriptionFeaturesDtlVo() {
//		return subscriptionFeaturesDtlVo;
//	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public UserVo getCreatedByDetails() {
		return createdByDetails;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

//	public void setSubscriptionFeaturesDtlVo(List<SubscriptionFeaturesDtlVo> subscriptionFeaturesDtlVo) {
//		this.subscriptionFeaturesDtlVo = subscriptionFeaturesDtlVo;
//	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedByDetails(UserVo createdByDetails) {
		this.createdByDetails = createdByDetails;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSubscriptionUserType() {
		return subscriptionUserType;
	}

	public List<SubscriptionAssociatedVo> getSubscriptionAssociatedVo() {
		return subscriptionAssociatedVo;
	}

	public List<SubscriptionFeaturesDtlVo> getSubscriptionFeaturesDtlVo() {
		return subscriptionFeaturesDtlVo;
	}

	public void setSubscriptionUserType(String subscriptionUserType) {
		this.subscriptionUserType = subscriptionUserType;
	}

	public void setSubscriptionAssociatedVo(List<SubscriptionAssociatedVo> subscriptionAssociatedVo) {
		this.subscriptionAssociatedVo = subscriptionAssociatedVo;
	}

	public void setSubscriptionFeaturesDtlVo(List<SubscriptionFeaturesDtlVo> subscriptionFeaturesDtlVo) {
		this.subscriptionFeaturesDtlVo = subscriptionFeaturesDtlVo;
	}
	
	
}
