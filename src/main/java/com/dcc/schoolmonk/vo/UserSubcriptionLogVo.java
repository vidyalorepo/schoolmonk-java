package com.dcc.schoolmonk.vo;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "USER_SUBSCRIPTION_LOG")
@EntityListeners(AuditingEntityListener.class)
public class UserSubcriptionLogVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "subscription_id")
    private SubcriptionMstVo subcriptionMstVo;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserVo userVo;
	
	@Column(name = "Subscription_Start_Date")
	private String subscriptionStartDate;
	
	@Column(name = "Subscription_Expiry_Date")
	private String subscriptionExpiryDate;
	
	@Column(name = "Payment_On")
	private String paymentOn;
	
	@Column(name = "Subscription_Price")
	private String subscriptionPrice;
	
	@Column(name = "Discount_Any")
	private String discountAny;
	
	@Column(name = "Subscription_Validity_Type")
	private String subscriptionValidityType;
	
	@Column(name = "Subscription_Validity")
	private String subscriptionValidity;
	
	@Column(name = "Subscription_Status", length = 15)
	private String subscriptionStatus; //ACTIVE
	
	@Column(name = "Subscription_Reminder_Date")
	private String subscriptionReminderDate; // Remind user if subscription validity end soon 
	
	@Column(name = "Subscription_Emi_Opt")
	private String subscriptionEmiOpt;
	
	@Column(name = "Subscription_Receipt_No")
	private String subscriptionReceiptNo;
	
	@Column(name = "Subscription_Ref_No")
	private String subscriptionRefNo;
	
	@Column(name = "Subscriber_Id")
	private String subscriberId;
	
	@Column(name = "Subscription_Payment_Type")
	private String subscriptionPaymentType;
	
	
	@JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userSubcriptionLogVo")
    private List<SubscriptionEmiVo> subscriptionEmiVo = new ArrayList<>();
	
	@Transient
	private List<SubscriptionFeaturesDtlVo> subscriptionFeaturesDtlVo;

	public Long getId() {
		return id;
	}

	public SubcriptionMstVo getSubcriptionMstVo() {
		return subcriptionMstVo;
	}

	public UserVo getUserVo() {
		return userVo;
	}

	public String getSubscriptionStartDate() {
		return subscriptionStartDate;
	}

	public String getSubscriptionExpiryDate() {
		return subscriptionExpiryDate;
	}

	public String getPaymentOn() {
		return paymentOn;
	}

	public String getDiscountAny() {
		return discountAny;
	}

	public String getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSubcriptionMstVo(SubcriptionMstVo subcriptionMstVo) {
		this.subcriptionMstVo = subcriptionMstVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	public void setSubscriptionStartDate(String subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	public void setSubscriptionExpiryDate(String subscriptionExpiryDate) {
		this.subscriptionExpiryDate = subscriptionExpiryDate;
	}

	public void setPaymentOn(String paymentOn) {
		this.paymentOn = paymentOn;
	}

	public void setSubscriptionStatus(String subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public String getSubscriptionPrice() {
		return subscriptionPrice;
	}

	public String getSubscriptionReminderDate() {
		return subscriptionReminderDate;
	}

	public void setSubscriptionPrice(String subscriptionPrice) {
		this.subscriptionPrice = subscriptionPrice;
	}

	public void setDiscountAny(String discountAny) {
		this.discountAny = discountAny;
	}

	public void setSubscriptionReminderDate(String subscriptionReminderDate) {
		this.subscriptionReminderDate = subscriptionReminderDate;
	}

	public String getSubscriptionValidityType() {
		return subscriptionValidityType;
	}

	public String getSubscriptionValidity() {
		return subscriptionValidity;
	}

	public void setSubscriptionValidityType(String subscriptionValidityType) {
		this.subscriptionValidityType = subscriptionValidityType;
	}

	public void setSubscriptionValidity(String subscriptionValidity) {
		this.subscriptionValidity = subscriptionValidity;
	}

	public String getSubscriptionEmiOpt() {
		return subscriptionEmiOpt;
	}

	public void setSubscriptionEmiOpt(String subscriptionEmiOpt) {
		this.subscriptionEmiOpt = subscriptionEmiOpt;
	}

	public List<SubscriptionEmiVo> getSubscriptionEmiVo() {
		return subscriptionEmiVo;
	}

	public void setSubscriptionEmiVo(List<SubscriptionEmiVo> subscriptionEmiVo) {
		this.subscriptionEmiVo = subscriptionEmiVo;
	}

	public String getSubscriptionReceiptNo() {
		return subscriptionReceiptNo;
	}

	public String getSubscriptionRefNo() {
		return subscriptionRefNo;
	}

	public String getSubscriptionPaymentType() {
		return subscriptionPaymentType;
	}

	public List<SubscriptionFeaturesDtlVo> getSubscriptionFeaturesDtlVo() {
		return subscriptionFeaturesDtlVo;
	}

	public void setSubscriptionReceiptNo(String subscriptionReceiptNo) {
		this.subscriptionReceiptNo = subscriptionReceiptNo;
	}

	public void setSubscriptionRefNo(String subscriptionRefNo) {
		this.subscriptionRefNo = subscriptionRefNo;
	}

	public void setSubscriptionPaymentType(String subscriptionPaymentType) {
		this.subscriptionPaymentType = subscriptionPaymentType;
	}

	public void setSubscriptionFeaturesDtlVo(List<SubscriptionFeaturesDtlVo> subscriptionFeaturesDtlVo) {
		this.subscriptionFeaturesDtlVo = subscriptionFeaturesDtlVo;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	@Override
	public String toString() {
		return "UserSubcriptionLogVo [id=" + id + ", subcriptionMstVo=" + subcriptionMstVo + ", userVo=" + userVo
				+ ", subscriptionStartDate=" + subscriptionStartDate + ", subscriptionExpiryDate="
				+ subscriptionExpiryDate + ", paymentOn=" + paymentOn + ", subscriptionPrice=" + subscriptionPrice
				+ ", discountAny=" + discountAny + ", subscriptionStatus=" + subscriptionStatus
				+ ", subscriptionReminderDate=" + subscriptionReminderDate + "]";
	}

	
	
	
}
