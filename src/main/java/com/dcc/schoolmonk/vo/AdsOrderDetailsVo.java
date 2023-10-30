package com.dcc.schoolmonk.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "ADS_ORDER_DETAILS")
@EntityListeners(AuditingEntityListener.class)
public class AdsOrderDetailsVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "zone_id")
	private AdszoneMstVo zoneId;

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ads_customer_Id")
	private UserVo customerId;

	@CreationTimestamp
	@Column(name = "order_date")
	private Date orderDate;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="transaction_id")
	private Transaction transactionId;

	@Column(name = "qty"  ,nullable = false)
	private Integer qty;

	@Column(name = "status")
	private Boolean status=false;

	@Column(name = "is_agree")
	private Boolean isAgree=false;

	@Column(name = "duration", length=255)
	private String duration;

	@Transient
	List<AttachmentVo> attachmentVo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AdszoneMstVo getZoneId() {
		return zoneId;
	}

	public void setZoneId(AdszoneMstVo zoneId) {
		this.zoneId = zoneId;
	}

	public UserVo getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UserVo customerId) {
		this.customerId = customerId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setTransactionId(Transaction transactionId) {
		this.transactionId = transactionId;
	}

	public Transaction getTransactionId() {
		return transactionId;
	}
	
	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Boolean isAgree) {
		this.isAgree = isAgree;
	}

	public List<AttachmentVo> getAttachmentVo() {
		return attachmentVo;
	}

	public void setAttachmentVo(List<AttachmentVo> attachmentVo) {
		this.attachmentVo = attachmentVo;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
}
